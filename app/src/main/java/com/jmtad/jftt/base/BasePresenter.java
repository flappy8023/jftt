package com.jmtad.jftt.base;

import android.support.annotation.NonNull;

import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-09-30 10:03
 **/
public class BasePresenter<T extends IBaseContract.IBaseView> implements IBaseContract.IBasePresenter {
    protected T mView;

    @Override
    public void attachView(IBaseContract.IBaseView view) {
        mView = (T) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    /**
     * 如果persenter和view保持生命周期一致，调用此方法在view结束时候释放资源
     * 此方法也处理了主线程子线程的切换
     *
     * @param lifecycle
     * @param <R>
     * @return
     */
    public <R> ObservableTransformer<R, R> onCompose(final LifecycleTransformer<R> lifecycle) {
        return new ObservableTransformer<R, R>() {
            @Override
            public ObservableSource<R> apply(@NonNull Observable<R> upstream) {
                if (lifecycle == null) {
                    return upstream.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {

                        }
                    }).subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                }
                return upstream.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(lifecycle);
            }
        };

    }

    /**
     * 获取userID
     *
     * @return
     */
    public String getUserId() {
        return SharedPreferenceUtil.getInstance().getUserId();
    }
}
