package com.jmtad.jftt.event;

import com.jmtad.jftt.http.bean.node.Popup;

/**
 * @description:通知activity展示弹窗的事件
 * @author: luweiming
 * @create: 2018-11-30 11:19
 **/
public class ShowPopupEvent {
    private Popup popup;

    public ShowPopupEvent(Popup p) {
        popup = p;
    }

    public Popup getPopup() {
        return popup;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
}
