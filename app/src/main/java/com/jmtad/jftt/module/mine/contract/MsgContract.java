package com.jmtad.jftt.module.mine.contract;

import com.jmtad.jftt.base.IBaseContract;
import com.jmtad.jftt.http.bean.node.Message;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 16:48
 **/
public interface MsgContract {
    interface IMsgView extends IBaseContract.IBaseView {
        void showMsgs(List<Message> messages);
    }

    interface IMsgPresenter extends IBaseContract.IBasePresenter {
        void queryMsg();

        void readMsg();
    }
}
