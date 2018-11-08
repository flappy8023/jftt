package com.jmtad.jftt.module.mine.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.OilRecord;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryOilBalanceResp;
import com.jmtad.jftt.http.bean.response.QueryOilRecordResp;
import com.jmtad.jftt.module.mine.contract.DealContract;
import com.jmtad.jftt.util.CollectionUtil;

import java.util.List;

import cn.qqtheme.framework.util.LogUtils;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 18:57
 **/
public class DealPresenter extends BasePresenter<DealContract.IDealView> implements DealContract.IDealPresenter {
    @Override
    public void queryDealRecord(int pageNo, int pageSize, String type, String date) {
        HttpApi.getInstance().service.queryOilRecord(getUserId(), String.valueOf(pageNo), String.valueOf(pageSize), date, type).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryOilRecordResp>() {
            @Override
            public void onSuccess(QueryOilRecordResp queryOilRecordResp) {
                if (null != queryOilRecordResp.getData() && TextUtils.equals(BaseResponse.CODE_0, queryOilRecordResp.getCode())) {
                    List<OilRecord> records = queryOilRecordResp.getData().getOilRecords();
                    if (!CollectionUtil.isEmpty(records)) {
                        mView.loadDealRecords(records);
                    } else {
                        LogUtils.debug("no record data");
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtils.error(e);
            }
        });
    }

    @Override
    public void queryOilBalance() {
        HttpApi.getInstance().service.queryOilBalance(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryOilBalanceResp>() {
            @Override
            public void onSuccess(QueryOilBalanceResp queryOilBalanceResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryOilBalanceResp.getCode())) {
                    mView.showBalance(queryOilBalanceResp.getOilNum());
                } else {
                    LogUtils.error("查询油滴数失败," + queryOilBalanceResp.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {
                mView.showBalance("0");
                LogUtils.error(e);
            }
        });
    }

}
