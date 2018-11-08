package com.jmtad.jftt.module.mine.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryCardResp;
import com.jmtad.jftt.http.bean.response.QueryOilBalanceResp;
import com.jmtad.jftt.module.mine.contract.MineContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 15:37
 **/
public class MinePresenter extends BasePresenter<MineContract.IMineView> implements MineContract.IMinePresenter {
    @Override
    public void queryCard() {
        HttpApi.getInstance().service.queryCard(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryCardResp>() {
            @Override
            public void onSuccess(QueryCardResp queryCardResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryCardResp.getCode())) {
                    if (!TextUtils.isEmpty(queryCardResp.getCardNo())) {
                        mView.showCards(queryCardResp.getCardNo());
                    } else {
                        mView.showNoCard();
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void bindCard(String cardNo) {
        HttpApi.getInstance().service.bindCard(getUserId(), cardNo).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (TextUtils.equals(BaseResponse.CODE_0, baseResponse.getCode())) {
                    mView.bindSuccess(cardNo);
                } else {
                    mView.showError(baseResponse.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void queryBalance() {
        HttpApi.getInstance().service.queryOilBalance(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryOilBalanceResp>() {
            @Override
            public void onSuccess(QueryOilBalanceResp queryOilBalanceResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryOilBalanceResp.getCode())) {
                    mView.showBalance(queryOilBalanceResp.getOilNum());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
