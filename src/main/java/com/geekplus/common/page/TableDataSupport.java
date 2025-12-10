package com.geekplus.common.page;

import com.geekplus.common.util.http.ServletUtil;

/**
 * author     : geekplus
 * description: 统一分页查询返回的数据对象，封装返回分页对象
 */
public class TableDataSupport {

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderBy";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    private static final String REASONABLE = "reasonable";

    /**
     * 封装分页对象
     */
    public static PageData getPageDomain()
    {
        PageData pageDomain = new PageData();
        pageDomain.setPageNum(ServletUtil.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtil.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderBy(ServletUtil.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtil.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtil.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageData buildPageRequest()
    {
        return getPageDomain();
    }
}
