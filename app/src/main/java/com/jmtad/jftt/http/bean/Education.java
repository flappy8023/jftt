package com.jmtad.jftt.http.bean;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-22 17:29
 **/
public enum Education {
    XIAOXUE("1", "小学"), CHUZHONG("2", "初中"), GAOZHONG("3", "高中"), ZHONGZHUAN("4", "中专"), ZHIXIAO("5", "职校"), ZHONGJI("6", "中技"), ZHUANKE("7", "专科"),
    BENKE("8", "本科"), SHUOSHI("9", "硕士"), BOSHI("10", "博士"), OTHER("99", "其他");
    private String type;
    private String name;

    Education(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
