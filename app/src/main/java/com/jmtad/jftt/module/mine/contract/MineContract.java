package com.jmtad.jftt.module.mine.contract;

import com.jmtad.jftt.base.IBaseContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 15:38
 **/
public interface MineContract {
    interface IMineView extends IBaseContract.IBaseView {
        void showCards(String cardNo);

        void showNoCard();

        void bindSuccess(String cardNo);

        void showBalance(String balance);
    }

    interface IMinePresenter extends IBaseContract.IBasePresenter {
        void queryCard();

        void bindCard(String cardNo);

        void queryBalance();
    }
}
