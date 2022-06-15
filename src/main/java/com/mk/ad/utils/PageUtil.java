package com.mk.ad.utils;

import com.github.pagehelper.Page;
import com.mk.ad.vo.resp.PageVO;

import java.util.List;

/**
 * @ClassName: PageUtil
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public class PageUtil {
    private PageUtil(){}
    public static <T> PageVO<T> getPageVO(List<T> list){
        PageVO<T> result=new PageVO<>();
        if(list instanceof Page){
            Page<T> page= (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setCurPageSize(page.getPageSize());
            result.setPageSize(page.size());
            result.setList(page.getResult());
        }
        return result;
    }
}
