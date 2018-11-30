package com.jmtad.jftt.base;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jmtad.jftt.event.ShowPopupEvent;
import com.jmtad.jftt.http.bean.node.Popup;
import com.jmtad.jftt.manager.PopupManager;
import com.jmtad.jftt.receiver.PopupReceiver;
import com.jmtad.jftt.service.PopupService;
import com.jmtad.jftt.util.MyToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description:activity基类
 * @author: luweiming
 * @create: 2018-09-30 17:55
 **/
public abstract class BaseActivity<T extends IBaseContract.IBasePresenter> extends RxAppCompatActivity implements IBaseContract.IBaseView {
    protected T presenter;
    protected Unbinder unbinder;
    protected SlidrInterface slidrInterface;
    private PopupReceiver popupReceiver;
    /**
     * 当前activity是否处于顶部
     */
    private boolean isTop = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //每个页面都需要支持弹出活动弹框
        EventBus.getDefault().register(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
        //当该页面不可见时取消注册活动广播
//        unregisterReceiver(popupReceiver);
//        EventBus.getDefault().unregister(this);
    }

    /**
     * 注册接收活动弹窗广播
     */
    private void registBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PopupService.TIMER_ACTION);
        popupReceiver = new PopupReceiver();
        registerReceiver(popupReceiver, filter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showPopup(ShowPopupEvent event) {
        if (isTop) {
            Popup act = event.getPopup();
            PopupManager.getInstance().showPopup(this, act);
            //弹窗展示后立马上报
            PopupManager.getInstance().saveRecord(act.getId());
        }
    }


}
