package com.jmtad.jftt.module.banner.contract;

import com.jmtad.jftt.base.IBaseContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-07 15:26
 **/
public interface DetailContract {
    interface IDetailView extends IBaseContract.IBaseView {
        void starSuc();

        void unStarSuc();

        void addViewsSuc(long views);
    }

    interface IDetailPresenter extends IBaseContract.IBasePresenter {
        void starOrUnStar(String bannerId);

        void addViews(String bannerId);
    }
}
