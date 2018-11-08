package com.jmtad.jftt.module.home;

import com.jmtad.jftt.base.IBaseContract;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 17:21
 **/
public interface HomeContract {
    interface IHomeView extends IBaseContract.IBaseView {
        void addViewsSucc(long views);

        void unStarSucc();

        void starSucc();
    }

    interface IHomePresenter extends IBaseContract.IBasePresenter {
        void addViews(String bannerId);

        void starOrUnStar(String bannerId);
    }
}
