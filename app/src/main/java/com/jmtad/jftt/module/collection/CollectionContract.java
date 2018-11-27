package com.jmtad.jftt.module.collection;

import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.Banner;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-26 10:24
 **/
public interface CollectionContract {
    interface ICollectionPresenter extends IBaseContract.IBasePresenter {
        void queryCollections(String type);

        void deleteCollections(List<Banner> bannerList);
    }

    interface ICollectionView extends IBaseContract.IBaseView {
        void showEmpty();

        void showBanners(List<Banner> banners);

        void deleteSuc();
    }
}
