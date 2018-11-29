package com.jmtad.jftt.event;

import com.jmtad.jftt.http.bean.node.Popup;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-29 14:44
 **/
public class PopupEvent {
    private Popup act;

    public PopupEvent(Popup act) {
        this.act = act;
    }

    public Popup getAct() {
        return act;
    }
}
