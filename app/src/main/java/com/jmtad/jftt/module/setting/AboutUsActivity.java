package com.jmtad.jftt.module.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryCommonProResp;
import com.jmtad.jftt.util.CollectionUtil;
import com.jmtad.jftt.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 关于我们页面
 */
public class AboutUsActivity extends BaseActivity {
    private static final String TYPE = "2";
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;

    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivLeft;

    @BindView(R.id.tv_about_us_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.setting_about_us);
        ivLeft.setImageResource(R.drawable.back_black);
        HttpApi.getInstance().service.queryCommonPro(TYPE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<QueryCommonProResp>() {
            @Override
            public void onSuccess(QueryCommonProResp queryCommonProResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryCommonProResp.getCode())) {
                    if (!CollectionUtil.isEmpty(queryCommonProResp.getCommonPros()) && !TextUtils.isEmpty(queryCommonProResp.getCommonPros().get(0).getDescription())) {
                        tvContent.setText(queryCommonProResp.getCommonPros().get(0).getDescription());
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initPresenter() {

    }
}
