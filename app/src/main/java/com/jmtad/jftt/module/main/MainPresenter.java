package com.jmtad.jftt.module.main;

import android.text.TextUtils;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryBannerListResp;
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
}
