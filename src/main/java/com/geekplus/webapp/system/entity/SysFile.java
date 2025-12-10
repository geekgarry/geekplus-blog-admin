package com.geekplus.webapp.system.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * author     : geekplus
 * date       : 11/29/23 04:10
 * description:
 */
public class SysFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String fileOriginalName;

    /**
     *
     */
    private String fileName;

    /**
     *
     */
    private String fileUrl;

    /**
     *
     */
    private String fileType;

    /**
     *
     */
    private String fileSize;

    /**
     *
     */
    private String createBy;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String updateBy;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private String remark;
}
