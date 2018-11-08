package com.jmtad.jftt.module.login.contract;

import com.jmtad.jftt.base.IBaseContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-24 15:00
 **/
public interface LoginContract {
    interface ILoginView extends IBaseContract.IBaseView {
        void sendCodeSuc();

        void sendCodeFail();

        void bindPhoneSuc();

        void bindPhoneFail();
    }

    interface ILoginPresenter extends IBaseContract.IBasePresenter {
        void sendCode(String phone);

        void bindPhone(String phone, String msgCode);
    }
}
