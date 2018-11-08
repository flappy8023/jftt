package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:用户结构
 * @author: flappy8023
 * @create: 2018-10-25 09:43
 **/
public class User {
    @SerializedName("userId")
    private String userId;
    @SerializedName("unionid")
    private String unionId;
    @SerializedName("openId")
    private String openId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("status")
    private String status;
    @SerializedName("headImgUrl")
    private String headImgUrl;
    @SerializedName("nickName")
    private String nickName;
    //1:男性 2:女性
    @SerializedName("sex")
    private String sex;
    @SerializedName("city")
    private String city;
    @SerializedName("profession")
    private String profession;
    @SerializedName("education")
    private String education;
    @SerializedName("personalProfile")
    private String personalProfile;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", unionId='" + unionId + '\'' +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", profession='" + profession + '\'' +
                ", education='" + education + '\'' +
                ", personalProfile='" + personalProfile + '\'' +
                ", equipment='" + equipment + '\'' +
                '}';
    }

    /**
     * 设备
     */
    @SerializedName("equipment")
    private String equipment;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

}
