package com.jmtad.jftt.module.banner.contract;

import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.Banner;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-07 15:26
 **/
public interface DetailContract {
    interface IDetailView extends IBaseContract.IBaseView {
        void starSuc();

        void unStarSuc();

        void addViewsSuc(long views);

        void loadBanner(Banner banner);
    }

    interface IDetailPresenter extends IBaseContract.IBasePresenter {
        void starOrUnStar(String bannerId);

        void addViews(String bannerId);

        void queryBannerByID(String id);
    }
}
