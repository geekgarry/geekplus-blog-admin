package com.geekplus.common.core.controller;

import com.geekplus.common.constant.HttpStatus;
import com.geekplus.common.domain.LoginUser;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageData;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.page.TableDataSupport;
import com.geekplus.common.util.http.ServletUtil;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.webapp.common.service.SysUserTokenService;
import com.geekplus.webapp.system.entity.SysMenu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author     : geekplus
 * description: 基础控制类方法，包括分页查询数据，构建递归树形结构
 */
public class BaseController {

    public static void startPage(){
        PageData pageDomain = TableDataSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            //String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize).setReasonable(reasonable);
        }else {
            PageHelper.startPage(1, 0);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected PageDataInfo getDataTable(List<?> list)
    {
        PageDataInfo rspData = new PageDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Result toResult(int rows)
    {
        return rows > 0 ? Result.success() : Result.error();
    }

    /**
     *递归方法，加载菜单为折叠形态
     */
    public List<SysMenu> getParentMenuList(List<SysMenu> list){
        List<SysMenu> menuList=new ArrayList<>();
        list.stream().forEach(menu ->{
            //SysMenu menu = lt.next();
            if (menu.getParentId() == 0) {
                //log.info("==========>数据"+menuList);
                menu.setChildren(getChild(list, menu.getMenuId()));
                menuList.add(menu);
            }
        });
        return menuList;
    }
    /**
     *递归方法
     */
    public List<SysMenu> getChild(List<SysMenu> list, Long menuId){
        List<SysMenu> childList=new ArrayList<>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();){
            SysMenu menu = iterator.next();
            if (menu.getParentId().equals(menuId)){
                //log.info("==========>数据"+menu);
                menu.setChildren(getChild(list, menu.getMenuId()));
                childList.add(menu);
                //log.info("==========>数据"+childList);
            }
        }
        return childList;
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUser getLoginUser()
    {
        Subject subject = SecurityUtils.getSubject();
        return (LoginUser) subject.getPrincipal();
    }

    /**
     * 获取登录用户名
     */
    public String getUsername()
    {
        return getLoginUser().getUsername();
    }
}
