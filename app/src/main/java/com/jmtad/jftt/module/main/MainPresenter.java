package com.jmtad.jftt.module.main;

import android.text.TextUtils;
import android.view.View;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryBannerListResp;
import com.jmtad.jftt.http.bean.response.QueryCollectsResp;
import com.jmtad.jftt.http.bean.response.StarResp;
import com.jmtad.jftt.util.CheckUpdateUtil;
import com.jmtad.jftt.util.CollectionUtil;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-08 16:54
 **/
public class MainPresenter extends BasePresenter<MainContract.IMainView> implements MainContract.IMainPresenter {
    @Override
    public void queryBannerList(int pageNo, int pageSize, String status) {
        HttpApi.getInstance().service.queryBannerList(getUserId(), pageNo + "", pageSize + "", status).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryBannerListResp>() {
            @Override
            public void onSuccess(QueryBannerListResp queryBannerListResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryBannerListResp.getCode())) {
                    List<Banner> banners = queryBannerListResp.getData().getBanners();
                    int total = queryBannerListResp.getData().getTotal();
                    int pages = queryBannerListResp.getData().getPages();
                    if (CollectionUtil.isEmpty(banners)) {
                        mView.noBanners();
                    } else {
                        mView.loadBannerList(banners, total, pages);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                mView.showError("系统错误");
            }
        });
    }

    @Override
    public DownloadBuilder checkUpdate(RequestVersionListener listener, boolean canCael) {
        return CheckUpdateUtil.checkVersion(listener, canCael);
    }

    /**
     * 点赞或者取消点赞
     */
    @Override
    public void starOrUnStar(Banner banner, View tvStar) {
        HttpApi.getInstance().service.star(banner.getId(), getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<StarResp>() {
            @Override
            public void onSuccess(StarResp starResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, starResp.getCode())) {
                    if (TextUtils.equals(StarResp.TYPE_STAR, starResp.getOprType())) {
                        banner.setStars(banner.getStars() + 1);
                        banner.setStarStatus(Banner.STATUS_STARED);
                        mView.starSucc(tvStar, banner.getStars());
                    } else {
                        banner.setStarStatus(Banner.STATUS_UNSTARED);
                        banner.setStars(banner.getStars() - 1);
                        mView.unStarSucc(tvStar, banner.getStars());
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
    public void queryRecentList(int pageNo, int pageSize) {
        HttpApi.getInstance().service.queryRecentBanner(pageNo, pageSize, getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryBannerListResp>() {
            @Override
            public void onSuccess(QueryBannerListResp queryBannerListResp) {
                if (TextUtils.equals(queryBannerListResp.getCode(), BaseResponse.CODE_0)) {
                    if (null != queryBannerListResp.getData() && !CollectionUtil.isEmpty(queryBannerListResp.getData().getBanners())) {
                        mView.loadRecent(queryBannerListResp.getData().getBanners());
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void queryCollects(String type) {
        HttpApi.getInstance().service.queryCollects(getUserId(), type).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryCollectsResp>() {
            @Override
            public void onSuccess(QueryCollectsResp queryBannerListResp) {
                if (TextUtils.equals(queryBannerListResp.getCode(), BaseResponse.CODE_0)) {
                    if (null != queryBannerListResp.getData() && !CollectionUtil.isEmpty(queryBannerListResp.getData().getBanners())) {
                        mView.loadCollects(queryBannerListResp.getData().getBanners());
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
