package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.Message;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 10:46
 **/
public class QueryMsgResp extends BaseResponse {
    @SerializedName("data")
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "QueryMsgResp{" +
                "messages=" + messages +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
