package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-27 13:33
 **/
public class SaveAuthRespData {
    @SerializedName("userId")
    private String userId;
    @SerializedName("unionid")
    private String unionid;
    @SerializedName("openId")
    private String openId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("status")
    private String status;
    @SerializedName("registerTime")
    private String registerTime;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("lastLoginTime")
    private String lastLoginTime;
    @SerializedName("equipment")
    private String equipment;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
