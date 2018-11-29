package com.jmtad.jftt.http;

import android.text.TextUtils;

import com.jmtad.jftt.http.bean.response.BaseResponse;

import io.reactivex.observers.ResourceObserver;

/**
 * @description:方便以后统一处理
 * @author: luweiming
 * @create: 2018-10-16 15:04
 **/
public abstract class RxCallBack<T> extends ResourceObserver<T> {
    @Override
    public void onNext(T t) {
        if (t instanceof BaseResponse) {
            if (TextUtils.equals((((BaseResponse) t).getCode()), BaseResponse.CODE_0)) {
                onSuccess(t);
            }
        } else {
            onSuccess(t);
        }
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
