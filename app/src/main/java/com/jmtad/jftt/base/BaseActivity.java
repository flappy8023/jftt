package com.jmtad.jftt.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jmtad.jftt.util.MyToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description:activity基类
 * @author: flappy8023
 * @create: 2018-09-30 17:55
 **/
public abstract class BaseActivity<T extends IBaseContract.IBasePresenter> extends RxAppCompatActivity implements IBaseContract.IBaseView {
    protected T presenter;
    protected Unbinder unbinder;
    protected SlidrInterface slidrInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
        if (null != presenter) {
            presenter.attachView(this);
        }
        setContentView(getLayoutId());
        //初始化butterknife
        unbinder = ButterKnife.bind(this);
        initView();
        //全局实现滑动返回功能
        slidrInterface = Slidr.attach(this);
    }

    protected abstract void initView();

    /**
     * 设置contentview,每个子类需要实现
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 提示错误信息,默认弹出toast
     *
     * @param msg
     */
    @Override
    public void showError(String msg) {
        MyToast.showLongToast(this, msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void showMsg(String msg) {
        MyToast.showShortToast(this, msg);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindToLifecycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (null != presenter) {
            presenter.detachView();
        }
    }
}
