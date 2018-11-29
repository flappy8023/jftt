package com.jmtad.jftt.http;

import com.jmtad.jftt.http.bean.node.Popup;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-29 14:25
 **/
public interface MockService {
    @GET("queryActs")
    Observable<List<Popup>> queryActs();
}
