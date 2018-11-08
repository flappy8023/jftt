package com.jmtad.jftt.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmtad.jftt.util.MyToast;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-08 09:32
 **/
public abstract class BaseMvpFragment<T extends IBaseContract.IBasePresenter> extends BaseFragment implements IBaseContract.IBaseView {
    protected T presenter;
    protected Activity activity;
    private View rootView;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void showError(String msg) {
        MyToast.showShortToast(getActivity(), msg);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            activity = getActivity();
            rootView = inflater.inflate(attachLayoutRes(), null);

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected abstract void initView(View view);

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * fragmentpagerAdapter中 页面移出缓存后不会调用ondestroy，所以在这个生命周期释放rxjava资源
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY_VIEW);
    }


    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化 Presenter对象
     */
    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        unbinder.unbind();
    }

}
