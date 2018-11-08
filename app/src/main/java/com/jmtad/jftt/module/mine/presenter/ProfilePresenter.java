package com.jmtad.jftt.module.mine.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryUserInfoResp;
import com.jmtad.jftt.module.mine.contract.ProfileContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 16:38
 **/
public class ProfilePresenter extends BasePresenter<ProfileContract.IProfileView> implements ProfileContract.IProfilePresenter {
    @Override
    public void queryUserInfo() {
        HttpApi.getInstance().service.queryUserInfo(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryUserInfoResp>() {
            @Override
            public void onSuccess(QueryUserInfoResp queryUserInfoResp) {
                if (TextUtils.equals(queryUserInfoResp.getCode(), BaseResponse.CODE_0)) {
                    mView.showUserInfo(queryUserInfoResp.getUser());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void submitUserInfo(String nickName, String headImg, String city, String sex, String profession, String personalProfile, String edu) {
        HttpApi.getInstance().service.saveUserInfo(getUserId(), headImg, nickName, sex, city, profession, edu, personalProfile).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (TextUtils.equals(BaseResponse.CODE_0, baseResponse.getCode())) {
                    mView.updateUserSucc();
                } else {
                    mView.showError(baseResponse.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
