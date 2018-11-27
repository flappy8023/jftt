package com.jmtad.jftt.module.collection;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.bean.node.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-26 10:31
 **/
public class CollectionPresenter extends BasePresenter<CollectionContract.ICollectionView> implements CollectionContract.ICollectionPresenter {
    @Override
    public void queryCollections(String type) {
        List<Banner> banners = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Banner banner = new Banner();
            banner.setTitle("标题" + i);
            banner.setSummary("f 分水水水水水水水水水水水水水水水水烦烦烦烦烦烦烦烦烦方法");
            banner.setImgUrl("http://t2.hddhhn.com/uploads/tu/201610/198/scx30045vxd.jpg");
            banners.add(banner);
        }
        mView.showBanners(banners);
    }

    @Override
    public void deleteCollections(List<Banner> bannerList) {

    }
}
