package com.syh.framework.model;

import java.util.List;

/**
 * Created by shenyonghe on 2020-01-13.
 */
public class SoConfig {
    private String arm_type; // v8 v7
    private String type; // 业务类型 pano_xixun
    private List<String> libs; // so列表

    public String getArm_type() {
        return arm_type;
    }

    public void setArm_type(String arm_type) {
        this.arm_type = arm_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getLibs() {
        return libs;
    }

    public void setLibs(List<String> libs) {
        this.libs = libs;
    }
}
