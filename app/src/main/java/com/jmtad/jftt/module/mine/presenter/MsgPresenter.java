package com.jmtad.jftt.module.mine.presenter;

import android.text.TextUtils;

import com.jmtad.jftt.base.BasePresenter;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryMsgResp;
import com.jmtad.jftt.module.mine.contract.MsgContract;

import cn.qqtheme.framework.util.LogUtils;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 16:51
 **/
public class MsgPresenter extends BasePresenter<MsgContract.IMsgView> implements MsgContract.IMsgPresenter {
    @Override
    public void queryMsg() {
        HttpApi.getInstance().service.queryMyMessage(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<QueryMsgResp>() {
            @Override
            public void onSuccess(QueryMsgResp queryMsgResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryMsgResp.getCode())) {
                    mView.showMsgs(queryMsgResp.getMessages());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void readMsg() {
        HttpApi.getInstance().service.readMsg(getUserId()).compose(onCompose(mView.bindToLife())).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (TextUtils.equals(BaseResponse.CODE_0, baseResponse.getCode())) {
                    LogUtils.debug("all message readed");
                } else {
                    LogUtils.error("read message error , " + baseResponse);
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtils.error(e);
            }
        });
    }

}
