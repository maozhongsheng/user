package com.mk.ad.controller;

import com.mk.ad.entity.SysRole;
import com.mk.ad.service.RoleService;
import com.mk.ad.utils.DataResult;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.vo.req.AddRoleReqVO;
import com.mk.ad.vo.req.RolePageReqVO;
import com.mk.ad.vo.req.RoleUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RoleController
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-角色管理",description = "角色管理相关接口")
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色数据接口")
    //@RequiresPermissions("sys:role:list")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo){
        DataResult result =DataResult.success();
        result.setData(roleService.pageInfo(vo));
        return result;
    }

    @PostMapping("/role/addRole")
    @ApiOperation(value = "新增角色接口")
    //@RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid AddRoleReqVO vo){
        DataResult result =DataResult.success();
        result.setData(roleService.addRole(vo));
        return result;
    }


    @PostMapping("/role/status")
    @ApiOperation(value = "修改状态")
     //@RequiresPermissions("sys:role:add")
    public DataResult roleStatus(@RequestBody  RoleUpdateReqVO vo){
        Map params = new HashMap();
        String id = vo.getId();
        String status = vo.getStatus().toString();
        params.put("id",id);
        params.put("status",status);
        DataResult result =DataResult.success();
        result.setData(roleService.updateStatus(params));
        return result;
    }

//    @GetMapping("/role/{id}")
//    @ApiOperation(value = "获取角色详情接口")
//   // @RequiresPermissions("sys:role:detail")
//    public DataResult<SysRole> detailInfo(@PathVariable("id") String id){
//        DataResult result=DataResult.success();
//        result.setData(roleService.detailInfo(id));
//        return result;
//    }
    @GetMapping("/role/{id}")
    @ApiOperation(value = "获取角色详情接口")
     //@RequiresPermissions("sys:role:detail")
    public DataResult detailInfo(@PathVariable("id") String id){
        DataResult result=DataResult.success();
        result.setData(roleService.detailInfo(id));
        return result;
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "更新角色信息接口")
    //@RequiresPermissions("sys:role:update")
    public DataResult updateRole(@RequestBody @Valid RoleUpdateReqVO vo){
        DataResult result=DataResult.success();
        roleService.updateRole(vo);
        return result;
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    //@RequiresPermissions("sys:role:delete")
    public DataResult deletedRole(@PathVariable("id") String id){
        roleService.deletedRole(id);
        DataResult result=DataResult.success();
        return result;
    }
}
