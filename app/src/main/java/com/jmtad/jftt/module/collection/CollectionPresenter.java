package com.jmtad.jftt.module.collection;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryCollectsResp;
import com.jmtad.jftt.util.CollectionUtil;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-26 10:31
 **/
public class CollectionPresenter extends BasePresenter<CollectionContract.ICollectionView> implements CollectionContract.ICollectionPresenter {
    @Override
    public void queryCollections(String type) {
        HttpApi.getInstance().service.queryCollects(getUserId(), Banner.Type.INFO).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryCollectsResp>() {
            @Override
            public void onSuccess(QueryCollectsResp queryCollectsResp) {
                if (TextUtils.equals(queryCollectsResp.getCode(), "0") && !CollectionUtil.isEmpty(queryCollectsResp.getData().getBanners())) {
                    mView.showBanners(queryCollectsResp.getData().getBanners());
                } else {
                    mView.showEmpty();
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void deleteCollections(List<Banner> bannerList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bannerList.size(); i++) {
            Banner banner = bannerList.get(i);
            if (i < bannerList.size() - 1) {
                builder.append(banner.getId()).append(",");
            } else {
                builder.append(banner.getId());
            }
        }
        String ids = builder.toString();
        HttpApi.getInstance().service.deleteCollections(getUserId(), ids).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.deleteSuc(bannerList);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
