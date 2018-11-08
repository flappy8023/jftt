package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 13:57
 **/
public class QueryBannerListData {
    @SerializedName("list")
    private List<Banner> banners;
    @SerializedName("total")
    private int total;
    @SerializedName("pages")
    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "QueryBannerListData{" +
                "banners=" + banners +
                ", total='" + total + '\'' +
                '}';
    }
}
