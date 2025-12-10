package com.geekplus.webapp.system.vo;

import java.io.Serializable;

/**
 * author     : geekplus
 * email      :
 * date       : 9/21/25 7:24 PM
 * description: //TODO
 */
public class SysDeptVO {

    //部门ID和名称
    private Long deptId;
    private String deptName;
    /**
     * 部门表 负责人
     */
    private String leader;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
