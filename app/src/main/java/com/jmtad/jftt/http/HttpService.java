package com.jmtad.jftt.http;


import com.jmtad.jftt.http.bean.response.AddReadVolumeResp;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.CollectResp;
import com.jmtad.jftt.http.bean.response.QueryBannerDetailResp;
import com.jmtad.jftt.http.bean.response.QueryBannerListResp;
import com.jmtad.jftt.http.bean.response.QueryCardResp;
import com.jmtad.jftt.http.bean.response.QueryCollectsResp;
import com.jmtad.jftt.http.bean.response.QueryCommonProResp;
import com.jmtad.jftt.http.bean.response.QueryMsgResp;
import com.jmtad.jftt.http.bean.response.QueryOilBalanceResp;
import com.jmtad.jftt.http.bean.response.QueryOilRecordResp;
import com.jmtad.jftt.http.bean.response.QueryPopupResp;
import com.jmtad.jftt.http.bean.response.QueryUserInfoResp;
import com.jmtad.jftt.http.bean.response.SaveAuthResp;
import com.jmtad.jftt.http.bean.response.StarResp;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * service统一接口数据
 */
public interface HttpService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @POST("comm/sendMessage")
    Observable<BaseResponse> sendMessage(@Query("phone") String phone);

    /**
     * 绑定手机号
     *
     * @param phone
     * @param msgCode
     * @param userId
     * @return
     */
    @POST("user/updatePhone")
    Observable<BaseResponse> updatePhone(@Query("phone") String phone, @Query("phoneMsgCode") String msgCode, @Query("userId") String userId);

    /**
     * 保存用户授权信息
     *
     * @param unionid
     * @param nickName
     * @param sex
     * @param headImg
     * @return
     */
    @POST("user/saveUserAuthData")
    Observable<SaveAuthResp> saveAuthData(@Query("unionid") String unionid, @Query("nickname") String nickName, @Query("sex") String sex, @Query("headimgurl") String headImg, @Query("equipment") String equipment);

    /**
     * 更新用户个人资料
     *
     * @param userId
     * @param headImg
     * @param nickName
     * @param sex
     * @param city
     * @param profession
     * @param education
     * @return
     */
    @POST("user/saveUserInfo")
    Observable<BaseResponse> saveUserInfo(@Query("userId") String userId, @Query("headImg") String headImg, @Query("nickName") String nickName,
                                          @Query("sex") String sex, @Query("city") String city, @Query("profession") String profession,
                                          @Query("education") String education, @Query("personalProfile") String personalProfile);

    /**
     * 查询图文详情
     *
     * @param id
     * @return
     */
    @POST("banner/queryBannerById")
    Observable<QueryBannerDetailResp> queryBannerDetail(@Query("id") String id);


    /**
     * 查询最近浏览
     *
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @POST("banner/queryReading")
    Observable<QueryBannerListResp> queryRecentBanner(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize, @Query("userId") String userId);

    /**
     * 查询收藏列表
     *
     * @param userID
     * @param type   0 图文 ，1 游戏
     * @return
     */
    @POST("banner/queryCollections")
    Observable<QueryCollectsResp> queryCollects(@Query("userId") String userID, @Query("type") String type);

    /**
     * 查询图文列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("banner/queryBannerList")
    Observable<QueryBannerListResp> queryBannerList(@Query("userId") String userId, @Query("pageNum") String pageNo, @Query("pageSize") String pageSize, @Query("status") String status);

    /**
     * 查询用户详情
     *
     * @param userId
     * @return
     */
    @POST("user/queryUserInfo")
    Observable<QueryUserInfoResp> queryUserInfo(@Query("userId") String userId);

    /**
     * 查询消息
     *
     * @param userId
     * @return
     */
    @POST("user/queryMyMessage")
    Observable<QueryMsgResp> queryMyMessage(@Query("userId") String userId);

    /**
     * 已读消息
     *
     * @param userId
     * @return
     */
    @POST("user/readMessage")
    Observable<BaseResponse> readMsg(@Query("userId") String userId);

    /**
     * 查询油滴数
     *
     * @param userId
     * @return
     */
    @POST("oil/queryOilBalance")
    Observable<QueryOilBalanceResp> queryOilBalance(@Query("userId") String userId);

    /**
     * 查询交易记录
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @param dateTime
     * @param type     0 全部| 1 收入| 2 支出
     * @return
     */
    @POST("oil/queryOilRecord")
    Observable<QueryOilRecordResp> queryOilRecord(@Query("userId") String userId, @Query("pageNum") String pageNum,
                                                  @Query("pageSize") String pageSize, @Query("dateTime") String dateTime, @Query("type") String type);

    /**
     * 问题反馈
     *
     * @param userId
     * @param title
     * @param des
     * @param imgUrl
     * @param phone
     * @param type   0 功能建议 ；1 性能问题
     * @return
     */
    @POST("sysSetting/saveProblem")
    Observable<BaseResponse> saveProblem(@Query("userId") String userId, @Query("title") String title,
                                         @Query("problemDescri") String des, @Query("imgUrl") String imgUrl, @Query("phone") String phone, @Query("type") String type);

    /**
     * 查询常见问题
     *
     * @param type 0 充值问题 ； 1 账号问题 ； 2 关于我们
     * @return
     */
    @POST("sysSetting/queryCommonPro")
    Observable<QueryCommonProResp> queryCommonPro(@Query("type") String type);

    /**
     * 增加阅读量
     *
     * @param bannerId
     * @return
     */
    @POST("banner/addReadVolume")
    Observable<AddReadVolumeResp> addReadVolume(@Query("bannerId") String bannerId, @Query("userId") String userId);

    /**
     * 点赞\取消点赞
     *
     * @param userId
     * @param bannerId
     * @return
     */
    @POST("banner/collectBanner")
    Observable<StarResp> star(@Query("bannerId") String bannerId, @Query("userId") String userId);

    /**
     * 收藏和取消收藏
     *
     * @param bannerId
     * @param userId
     * @return
     */
    @POST("banner/collectBanner")
    Observable<CollectResp> collectBanner(@Query("bannerId") String bannerId, @Query("userId") String userId);

    @POST("banner/deleteCollections")
    Observable<BaseResponse> deleteCollections(@Query("userId") String userId, @Query("bannerIds") String ids);
    /**
     * 查询绑定的卡
     *
     * @param id
     * @return
     */
    @POST("oil/queryMerchantCard")
    Observable<QueryCardResp> queryCard(@Query("userId") String id);

    /**
     * 绑定卡片
     *
     * @param userId
     * @param cardNo
     * @return
     */
    @POST("oil/saveMerchantCard")
    Observable<BaseResponse> bindCard(@Query("userId") String userId, @Query("cardNo") String cardNo);

    /**
     * 查询用户当天的弹窗列表
     *
     * @param userId
     * @return
     */
    @POST("popup/queryPopupsByUserId")
    Observable<QueryPopupResp> queryPopupsByUserId(@Query("userId") String userId);

    /**
     * 保存用户弹窗记录
     *
     * @param userId
     * @param popupId 弹窗id
     * @return
     */
    @POST("popup/addPopupRecord")
    Observable<BaseResponse> addPopupRecord(@Query("userId") String userId, @Query("popupId") String popupId);

}