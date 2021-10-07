package com.imooc.service.impl.center;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.imooc.utils.PagedGridResult;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.service.impl.center
 * @date 2021/10/7 15:22
 */
public class BasicService {

    public PagedGridResult setPagedGrid(List<?> list,Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
