package com.jmtad.jftt.module.home;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.AddReadVolumeResp;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.StarResp;

import cn.qqtheme.framework.util.LogUtils;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 17:31
 **/
public class HomePresenter extends BasePresenter<HomeContract.IHomeView> implements HomeContract.IHomePresenter {
    @Override
    public void addViews(String bannerId) {
        HttpApi.getInstance().service.addReadVolume(bannerId, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<AddReadVolumeResp>() {
            @Override
            public void onSuccess(AddReadVolumeResp addReadVolumeResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, addReadVolumeResp.getCode())) {
                    mView.addViewsSucc(addReadVolumeResp.getViews());
                } else {
                    LogUtils.error(addReadVolumeResp.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 点赞或者取消点赞
     *
     * @param bannerId
     */
    @Override
    public void starOrUnStar(String bannerId) {
        HttpApi.getInstance().service.star(bannerId, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<StarResp>() {
            @Override
            public void onSuccess(StarResp starResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, starResp.getCode())) {
                    if (TextUtils.equals(StarResp.TYPE_STAR, starResp.getOprType())) {
                        mView.starSucc();
                    } else {
                        mView.unStarSucc();
                    }
                } else {
                    mView.showError(starResp.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
