package com.geekplus.common.page;

import com.geekplus.common.util.string.StringUtils;

/**
 * author     : geekplus
 * description: 分页查询的数据传输对象
 */
public class PageData {
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列排序字段*/
    private String orderBy;

    //排序方向
    private String isAsc="asc";

    /** 分页参数合理化 */
    private Boolean reasonable = true;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderBy))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderBy) + " " + isAsc;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }
}
