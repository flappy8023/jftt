package com.jmtad.jftt.module.mine.contract;

import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.User;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 16:27
 **/
public interface ProfileContract {
    interface IProfileView extends IBaseContract.IBaseView {
        void showUserInfo(User user);

        void updateUserSucc();
    }

    interface IProfilePresenter extends IBaseContract.IBasePresenter {
        void queryUserInfo();

        void submitUserInfo(String nickName, String headImg, String city, String sex, String profession, String personalProfile, String edu);
    }
}
