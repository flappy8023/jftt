package com.jmtad.jftt.module.mine.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.LinkagePicker;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-23 18:45
 **/
public class JobProvider extends LinkagePicker.DataProvider {
    @NonNull
    @Override
    public List<String> provideFirstData() {
        List<String> category = new ArrayList<>();
        category.add("通用岗位");
        category.add("IT互联网");
        category.add("文化传媒");
        category.add("金融");
        category.add("教育培训");

        category.add("医疗生物");
        category.add("政府组织");
        category.add("工业制造");
        category.add("餐饮出行");
        category.add("服务业");
        category.add("其他");

        return category;
    }

    @NonNull
    @Override
    public List<String> provideSecondData(int firstIndex) {
        List<String> data = new ArrayList<>();
        switch (firstIndex) {
            case 0:
                //通用岗位
                data.add("销售");
                data.add("市场");
                data.add("人力资源");
                data.add("行政");
                data.add("公关");
                data.add("客服");
                data.add("采购");
                data.add("技工");
                data.add("公司职员");
                data.add("职业经理人");
                data.add("私营企业主");
                data.add("中层管理者");
                data.add("自由职业者");
                break;
            //IT互联网
            case 1:
                data.add("开发工程师");
                data.add("测试工程师");
                data.add("设计师");
                data.add("运营师");
                data.add("产品经理");
                data.add("风控安全");
                data.add("个体/网店");
                break;
            //文化传媒
            case 2:
                data.add("编辑策划");
                data.add("记者");
                data.add("艺人");
                data.add("经纪人");
                data.add("媒体工作者");
                break;
            //金融
            case 3:
                data.add("咨询");
                data.add("投行");
                data.add("保险");
                data.add("金融分析");
                data.add("财务");
                data.add("风险管理");
                data.add("风险投资人");
                break;
            //教育培训
            case 4:
                data.add("学生");
                data.add("留学生");
                data.add("大学生");
                data.add("研究生");
                data.add("博士");
                data.add("科研人员");
                data.add("教师");
                break;
            //医疗生物
            case 5:
                data.add("医生");
                data.add("护士");
                data.add("宠物医生");
                data.add("医学研究");
                break;
            //政府组织
            case 6:
                data.add("公务员");
                data.add("事业单位");
                data.add("军人");
                data.add("律师");
                data.add("警察");
                data.add("国企工作者");
                data.add("运动员");
                break;
            //工业制造
            case 7:
                data.add("技术研发");
                data.add("技工");
                data.add("质检");
                data.add("建筑工人");
                data.add("装修工人");
                data.add("建筑设计师");
                break;
            //餐饮出行
            case 8:
                data.add("厨师");
                data.add("服务员");
                data.add("收银");
                data.add("导购");
                data.add("保安");
                data.add("乘务人员");
                data.add("驾驶员");
                data.add("航空人员");
                data.add("空城");
                break;
            //服务业
            case 9:
                data.add("导游");
                data.add("快递员(含外卖)");
                data.add("美容美发");
                data.add("家政服务");
                data.add("婚庆摄影");
                data.add("运动健身");
                break;
            case 10:
                data.add("其他");
                break;

        }
        return data;
    }

    @Nullable
    @Override
    public List<String> provideThirdData(int firstIndex, int secondIndex) {
        return null;
    }

    @Override
    public boolean isOnlyTwo() {
        return true;
    }
}
