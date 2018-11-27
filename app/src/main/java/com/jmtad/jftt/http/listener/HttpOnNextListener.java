package com.jmtad.jftt.http.listener;

/**
 * 成功回调处理
 */
public interface HttpOnNextListener<T> {
    /**
     * 成功后回调方法
     *
     * @param t
     */
    void onNext(T t);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     *
     * @param e
     */
    void onError(Throwable e);

    /**
     * 取消回調
     */
    void onCancel();
}
