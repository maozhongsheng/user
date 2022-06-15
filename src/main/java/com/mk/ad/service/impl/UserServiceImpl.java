package com.mk.ad.service.impl;

import com.github.pagehelper.PageHelper;
import com.mk.ad.entity.SysDept;
import com.mk.ad.entity.SysRole;
import com.mk.ad.entity.SysUser;
import com.mk.ad.entity.SysUserRole;
import com.mk.ad.mapper.*;
import com.mk.ad.mapper.mymapper.MyAdminSspAccountMapper;
import com.mk.ad.service.*;
import com.mk.ad.utils.*;
import com.mk.ad.vo.req.*;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.constants.Constant;
import com.mk.ad.exception.BusinessException;
import com.mk.ad.exception.code.BaseResponseCode;
import com.mk.ad.vo.resp.LoginRespVO;
import com.mk.ad.vo.resp.UserInfoRespVO;
import com.mk.ad.vo.resp.UserOwnRoleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserserviceImpl
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private MyAdminSspAccountMapper mySysAccountMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenSettings tokenSettings;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private CityMapper cityMapper;

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        //通过用户名查询用户信息
        //如果查询存在用户
        //就比较它密码是否一样
        SysUser userInfoByName = sysUserMapper.getUserInfoByName(vo.getUsername());
        if(userInfoByName==null){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        if(userInfoByName.getStatus()==2){
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK_TIP);
        }
        if(!PasswordUtils.matches(userInfoByName.getSalt(),vo.getPassword(),userInfoByName.getPassword())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        String id = userInfoByName.getId();
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setLast_time(new Date());
        SysRole role = sysRoleMapper.getUserInfoByName(vo.getUsername());
        String status = sysUserMapper.getStatus(id);
        if(status != null){
            if(!"2".equals(status)){
                throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR_SSP);
            }
        }
        String dspstatus = sysUserMapper.getDSPStatus(id);
        if(dspstatus != null){
            if(!"2".equals(dspstatus)){
                throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR_SSP);
            }
        }

        if(!userInfoByName.getSysType().equals(0)){
            if(!vo.getSysType().equals(userInfoByName.getSysType().toString())){
                throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR_SYSTEM);
            }
        }
        LoginRespVO loginRespVO=new LoginRespVO();
        loginRespVO.setPhone(userInfoByName.getPhone());
        loginRespVO.setUsername(userInfoByName.getUsername());
        loginRespVO.setNickName(userInfoByName.getNickName());
        loginRespVO.setRealName(userInfoByName.getRealName());
        loginRespVO.setId(userInfoByName.getId());
        loginRespVO.setEmail(userInfoByName.getEmail());
        loginRespVO.setIs_up(userInfoByName.getIsUp());
       // loginRespVO.setAddress(userInfoByName.getAddress());
        String address = "";
        String xiangqing = "";
        String endid ="";
        String cid ="";
        String sid = "";
        if(null != userInfoByName.getAddress()){
        String[] sourceStrArray = userInfoByName.getAddress().split(",");
        for (int i = 0; i < sourceStrArray.length; i++) {
            if(sourceStrArray.length - i == 1){
                xiangqing = sourceStrArray[i];

            }
            else if (sourceStrArray.length - i == 3){
                String s = sourceStrArray[i].toString();
                cid  = cityMapper.selectid(s).toString();


            }
            else if (sourceStrArray.length - i == 4){
                String s = sourceStrArray[i].toString();
                sid  = cityMapper.selectid(s).toString();
            }
            else  if (sourceStrArray.length - i == 2){
                String s = sourceStrArray[i].toString();
                String t = sourceStrArray[i-1].toString();
                Integer c = cityMapper.selectid(t);
                Map pream = new HashMap();
                pream.put("s",s);
                pream.put("c",c);
                endid =cityMapper.selectendid(pream).toString();
            }
            address = sid + "," + cid + "," + endid + "," +xiangqing;
        }
        loginRespVO.setAddress(address);
        }
//        if(role != null){
//            if (null != getRoleByUserId(userInfoByName.getId()).get(0)){
//                loginRespVO.setRolename(getRoleByUserId(userInfoByName.getId()).get(0));//权限名称
//            }
//        }
        Map<String, Object> claims=new HashMap<>();
        claims.put(Constant.ROLES_INFOS_KEY,getRoleByUserId(userInfoByName.getId()));
        claims.put(Constant.PERMISSIONS_INFOS_KEY,getPermissionByUserId(userInfoByName.getId()));
        claims.put(Constant.JWT_USER_NAME,userInfoByName.getUsername());
        String accessToken= JwtTokenUtil.getAccessToken(userInfoByName.getId(),claims);
        log.info(accessToken);
        String refreshToken;
        if(vo.getType().equals("1")){
            refreshToken=JwtTokenUtil.getRefreshToken(userInfoByName.getId(),claims);
        }else {
            refreshToken=JwtTokenUtil.getRefreshAppToken(userInfoByName.getId(),claims);
        }

        if(null!= userInfoByName.getRole_jurisdiction() && null!=role.getRole_power()){
            loginRespVO.setJurisdiction(userInfoByName.getRole_jurisdiction()+","+role.getRole_power());
        }else if(null!=userInfoByName.getRole_jurisdiction()){
            loginRespVO.setJurisdiction(userInfoByName.getRole_jurisdiction());
        }else if(null!=role && null!=role.getRole_power()){
            loginRespVO.setJurisdiction(role.getRole_power());
        }
        String token = JWTHelper.getToken("UserInfo", userInfoByName.getId());
        loginRespVO.setAccessToken(token);
        loginRespVO.setRefreshToken(refreshToken);
        loginRespVO.setPassword(userInfoByName.getPwd());
        loginRespVO.setRolename(userInfoByName.getRoleName());
        Map company_profile = sysUserMapper.selectCompanyProfile(userInfoByName.getId());
        if(null!=company_profile){
            loginRespVO.setCompany_profile(company_profile.get("name").toString());
            if(null != company_profile.get("id")){
                loginRespVO.setDsp_id(company_profile.get("id").toString());
            }else{
                loginRespVO.setDsp_id(userInfoByName.getId());
            }

        }else{
            loginRespVO.setCompany_profile("北京天卓");
        }

        Integer i = sysUserMapper.getDspType(id);
        if(null == i){
            loginRespVO.setDspType(0);
        }else{
            loginRespVO.setDspType(i);
        }
        //SecurityUtils.getSubject().getSession().setTimeout(300);//session过期时间2小时
        return loginRespVO;
    }
    /**
     * 用过用户id查询拥有的角色信息
     * @Author:      yjn
     * @UpdateUser:
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    private List<String> getRoleByUserId(String userId){
//        List<String> list=new ArrayList<>();
//        if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
//            list.add("admin");
//        }else {
//            list.add("dev");
//        }
        return roleService.getNamesByUserId(userId);
    }

    private List<String> getPermissionByUserId(String userId){
//        List<String> list=new ArrayList<>();
//        if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
//            list.add("sys:user:add");
//            list.add("sys:user:update");
//            list.add("sys:user:delete");
//            list.add("sys:user:list");
//        }else {
////            list.add("sys:user:list");
//            list.add("sys:user:add");
//        }
        return permissionService.getPermissionByUserId(userId);
    }


    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SysUser> list=sysUserMapper.selectAll(vo);
        for (SysUser sysUser:list){
            SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysUser.getRoleId());
            Date createTime = sysUser.getCreateTime();
            String format = sf.format(createTime);
            sysUser.setCreateT(format);
            if(sysDept!=null){
                sysUser.setDeptName(sysDept.getName());
            }
            if(sysRole!=null){
                sysUser.setRoleName(sysRole.getName());
            }
        }
        return PageUtil.getPageVO(list);
    }
    @Override
    public void addUser(UserAddReqVO vo) {
        List email = sysUserMapper.selectemail();
        if(email.contains(vo.getEmail())){
            throw new BusinessException(BaseResponseCode.EMAIL_ERROR);
        }
        SysUser sysUser=new SysUser();
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(vo,sysUser);
        String s = UUID.randomUUID().toString();
        sysUser.setId(s);
        sysUser.setCreateTime(new Date());
        String salt=PasswordUtils.getSalt();
        String ecdPwd=PasswordUtils.encode(vo.getPassword(),salt);
        sysUser.setSalt(salt);
        sysUser.setPassword(ecdPwd);
        sysUser.setEmail(vo.getEmail());
        sysUser.setPhone(vo.getPhone());
        sysUser.setRoleId(vo.getRoleId());
        sysUser.setSysType(1);
        sysUser.setStatus(1);
        List role = vo.getRole();
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i<role.size() ; i++){
            if(i<role.size()-1){
                sb.append(role.get(i) + ",");
            }else {
                sb.append(role.get(i));
            }
        }
        String bu = sb.toString();
        sysUser.setRole_jurisdiction(bu);
        sysUserRole.setRoleId(vo.getRoleId());
        sysUserRole.setUserId(s);
        sysUserRole.setId(UUID.randomUUID().toString());
        sysUserRole.setCreateTime(new Date());
        int i = sysUserMapper.insertSelective(sysUser);
        sysUserRoleMapper.insertSelective(sysUserRole);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        UserOwnRoleRespVO respVO=new UserOwnRoleRespVO();
        respVO.setOwnRoles(userRoleService.getRoleIdsByUserId(userId));
        respVO.setAllRole(roleService.selectAllBy());
        return respVO;
    }

    @Override
    public void setUserOwnRole(UserOwnRoleReqVO vo) {
        userRoleService.addUserRoleInfo(vo);
        /**
         * 标记用户 要主动去刷新
         */
        redisService.set(Constant.JWT_REFRESH_KEY+vo.getUserId(),vo.getUserId(),tokenSettings.getAccessTokenExpireTime().toMillis(),TimeUnit.MILLISECONDS);
        /**
         * 清楚用户授权数据缓存
         */
        redisService.delete(Constant.IDENTIFY_CACHE_KEY+vo.getUserId());
    }

//    @Override
//    public String refreshToken(String refreshToken) {
//        //它是否过期
//        //它是否被加如了黑名||redisService.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken)
//        if(!JwtTokenUtil.validateToken(refreshToken)){
//            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
//        }
//        String userId=JwtTokenUtil.getUserId(refreshToken);
//        String username=JwtTokenUtil.getUserName(refreshToken);
//        log.info("userId={}",userId);
//        Map<String,Object> claims=new HashMap<>();
//        claims.put(Constant.ROLES_INFOS_KEY,getRoleByUserId(userId));
//        claims.put(Constant.PERMISSIONS_INFOS_KEY,getPermissionByUserId(userId));
//        claims.put(Constant.JWT_USER_NAME,username);
//        String newAccessToken= JwtTokenUtil.getAccessToken(userId,claims);
//        return newAccessToken;
//    }
    @Override
    public String refreshToken(String refreshToken) {
        //根据token获取userid
        //重新生成时间为2小时的新token
        String userId = JWTHelper.vd(refreshToken);
        log.info("userId={}",userId);
        String token = JWTHelper.getToken("user login", userId);
        return token;
    }
    @Override
    public void updateUserInfo(UserUpdateReqVO vo, String operationId) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(operationId);
        sysUser.setPhone(vo.getPhone());
        sysUser.setEmail(vo.getEmail());
        sysUser.setUsername(vo.getUsername());
        if(StringUtils.isEmpty(vo.getPassword())){
            sysUser.setPassword(null);
        }else {
            String salt= PasswordUtils.getSalt();
            String endPwd=PasswordUtils.encode(vo.getPassword(),salt);
            sysUser.setSalt(salt);
            sysUser.setPassword(endPwd);
        }
        List role = vo.getRole();
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i<role.size() ; i++){
            if(i<role.size()-1){
                sb.append(role.get(i) + ",");
            }else {
                sb.append(role.get(i));
            }
        }
        String bu = sb.toString();
        sysUser.setRole_jurisdiction(bu);
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
//        if(vo.getStatus()==2){
//            redisService.set(Constant.ACCOUNT_LOCK_KEY+vo.getId(),vo.getId());
//        }else {
//            redisService.delete(Constant.ACCOUNT_LOCK_KEY+vo.getId());
//        }
    }
    @Override
    public void deletedUsers(List<String> list, String operationId) {
        SysUser sysUser=new SysUser();
        sysUser.setUpdateId(operationId);
        sysUser.setUpdateTime(new Date());
        int i = sysUserMapper.deletedUsers(sysUser, list);
        if(i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        for (String userId:
                list) {
            redisService.set(Constant.DELETED_USER_KEY+userId,userId,tokenSettings.getRefreshTokenExpireAppTime().toMillis(),TimeUnit.MILLISECONDS);
            /**
             * 清楚用户授权数据缓存
             */
            redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
        }
    }

    @Override
    public List<SysUser> selectUserInfoByDeptIds(List<String> deptIds) {
        return sysUserMapper.selectUserInfoByDeptIds(deptIds);
    }

    @Override
    public SysUser detailInfo(String userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void userUpdateDetailInfo(UserUpdateDetailInfoReqVO vo, String userId) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setId(userId);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public void userUpdatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken) {
        String userId=JwtTokenUtil.getUserId(accessToken);
        //校验旧密码
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if(sysUser==null){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        if(!PasswordUtils.matches(sysUser.getSalt(),vo.getOldPwd(),sysUser.getPassword())){
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        //保存新密码
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(),sysUser.getSalt()));
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        /**
         * 把token 加入黑名单 禁止再访问我们的系统资源
         */
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST+accessToken,userId,JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        /**
         * 把 refreshToken 加入黑名单 禁止再拿来刷新token
         */
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken,userId,JwtTokenUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);
        /**
         * 清楚用户授权数据缓存
         */
        redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken)||StringUtils.isEmpty(refreshToken)){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            subject.logout();
        }
        String userId=JwtTokenUtil.getUserId(accessToken);
        /**
         * 把accessToken 加入黑名单
         */
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST+accessToken,userId,JwtTokenUtil.getRemainingTime(accessToken),TimeUnit.MILLISECONDS);

        /**
         * 把refreshToken 加入黑名单
         */
        redisService.set(Constant.JWT_REFRESH_IDENTIFICATION+refreshToken,userId,JwtTokenUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);
    }

    @Override
    public void userUpdatePwdssp(UserUpdateInfoReqVO vo, String accessToken, String refresgToken) {
        String userId = JWTHelper.vd(accessToken);
        //校验旧密码
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if(sysUser==null){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        //保存新密码
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(),sysUser.getSalt()));
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

//        /**
//         * 把token 加入黑名单 禁止再访问我们的系统资源
//         */
//        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST+accessToken,userId,JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
//        /**
//         * 把 refreshToken 加入黑名单 禁止再拿来刷新token
//         */
//        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refresgToken,userId,JwtTokenUtil.getRemainingTime(refresgToken),TimeUnit.MILLISECONDS);
//        /**
//         * 清楚用户授权数据缓存
//         */
//        redisService.delete(Constant.IDENTIFY_CACHE_KEY+userId);
    }

    @Override
    public void userUpdateDetailInfossp(UserUpdateInfoReqVO vo, String userId) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setId(userId);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if(i!=1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public UserInfoRespVO getusersByid(UserPageReqVO vo) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(vo.getUserId());
        UserInfoRespVO user = new UserInfoRespVO();
        user.setId(sysUser.getId());
        user.setUsername(sysUser.getUsername());
        user.setEmail(sysUser.getEmail());
        user.setPhone(sysUser.getPhone());
        if(null!=sysUser.getRoleId()){
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysUser.getRoleId());
            user.setRolename(sysRole.getName());
            user.setRoleid(sysRole.getId());
        }
        user.setJurisdictionId(sysUser.getRole_jurisdiction());
        return user;
    }
    @Override
    public Integer updateStatus(Map params) {
        Integer resutl =  sysUserMapper.updateStatus(params);

        return resutl;
    }


}
