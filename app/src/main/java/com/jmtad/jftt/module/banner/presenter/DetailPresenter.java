package com.jmtad.jftt.module.banner.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.AddReadVolumeResp;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryBannerDetailResp;
import com.jmtad.jftt.http.bean.response.StarResp;
import com.jmtad.jftt.module.banner.contract.DetailContract;
import com.jmtad.jftt.util.LogUtil;

import cn.qqtheme.framework.util.LogUtils;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-07 15:29
 **/
public class DetailPresenter extends BasePresenter<DetailContract.IDetailView> implements DetailContract.IDetailPresenter {
    @Override
    public void starOrUnStar(String bannerId) {
        HttpApi.getInstance().service.star(bannerId, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<StarResp>() {
            @Override
            public void onSuccess(StarResp starResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, starResp.getCode())) {
                    if (TextUtils.equals(StarResp.TYPE_STAR, starResp.getOprType())) {
                        mView.starSuc();
                    } else {
                        mView.unStarSuc();
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

    @Override
    public void addViews(String bannerId) {
        HttpApi.getInstance().service.addReadVolume(bannerId, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<AddReadVolumeResp>() {
            @Override
            public void onSuccess(AddReadVolumeResp addReadVolumeResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, addReadVolumeResp.getCode())) {
                    mView.addViewsSuc(addReadVolumeResp.getViews());
                } else {
                    LogUtils.error(addReadVolumeResp.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    @Override
    public void queryBannerByID(String id) {
        HttpApi.getInstance().service.queryBannerDetail(id).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryBannerDetailResp>() {
            @Override
            public void onSuccess(QueryBannerDetailResp queryBannerDetailResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryBannerDetailResp.getCode())) {
                    mView.loadBanner(queryBannerDetailResp.getBanner());
                } else {
                    mView.showError("");
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtil.debug(e.getLocalizedMessage());
            }
        });
    }
}
