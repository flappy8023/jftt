package com.jmtad.jftt.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-09-30 09:58
 **/
public interface IBaseContract {
    interface IBasePresenter<T extends IBaseView> {
        void attachView(T view);

        void detachView();
    }

    interface IBaseView {
        void showError(String msg);

        void showLoading();

        void hideLoading();

        /**
         * 配合RxLifecycle使用
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();
    }
}
