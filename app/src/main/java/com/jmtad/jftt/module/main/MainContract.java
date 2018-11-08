package com.jmtad.jftt.module.main;


import android.view.View;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.Banner;

import java.util.List;


/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-09 10:00
 **/
public interface MainContract {
    interface IMainView extends IBaseContract.IBaseView {
        void loadBannerList(List<Banner> banners, int total, int pages);

        void noBanners();

        void starSucc(View view, long stars);

        void unStarSucc(View view, long stars);
    }

    interface IMainPresenter extends IBaseContract.IBasePresenter {
        void queryBannerList(int pageNo, int pageSize, String status);

        DownloadBuilder checkUpdate(RequestVersionListener listener, boolean canCancel);

        void starOrUnStar(Banner banner, View view);
    }
}
