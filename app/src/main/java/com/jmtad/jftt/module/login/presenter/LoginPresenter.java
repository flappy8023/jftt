package com.jmtad.jftt.module.login.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.module.login.contract.LoginContract;
import com.jmtad.jftt.util.SharedPreferenceUtil;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-24 15:04
 **/
public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {
    @Override
    public void sendCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mView.showError("手机号不能为空!");
            return;
        }
        if (phone.length() != 11) {
            mView.showError("请输入正确的手机号!");
            return;
        }
        HttpApi.getInstance().service.sendMessage(phone).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse resp) {
                mView.sendCodeSuc();
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void bindPhone(String phone, String msgCode) {

        if (TextUtils.isEmpty(phone)) {
            mView.showError("手机号不能为空!");
            return;
        }
        if (TextUtils.isEmpty(msgCode)) {
            mView.showError("请输入验证码");
            return;
        }
        HttpApi.getInstance().service.updatePhone(phone, msgCode, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse resp) {
                if (TextUtils.equals(BaseResponse.CODE_0, resp.getCode())) {
                    //绑定成功后保存手机号
                    SharedPreferenceUtil.getInstance().savePhone(phone);
                    mView.bindPhoneSuc();
                }
                //验证码错误
                if (TextUtils.equals("5", resp.getCode())) {
                    mView.bindPhoneFail();
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}
