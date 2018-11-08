package com.jmtad.jftt.http;

import io.reactivex.observers.ResourceObserver;

/**
 * @description:方便以后统一处理
 * @author: flappy8023
 * @create: 2018-10-16 15:04
 **/
public abstract class RxCallBack<T> extends ResourceObserver<T> {
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable e);
}
