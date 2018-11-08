package com.jmtad.jftt.module.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.FaqAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.CommonPro;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryCommonProResp;
import com.jmtad.jftt.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FAQDetailActivity extends BaseActivity {
    //    public static final String KEY_CONTENT = "content";
//    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE = "type";
    @BindView(R.id.rv_faq_detail)
    RecyclerView recyclerView;

    @BindView(R.id.iv_toolbar_left_button)
    ImageView back;

    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    private FaqAdapter adapter;
    private List<CommonPro> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        back.setImageResource(R.drawable.back_black);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FaqAdapter(this, details);
        recyclerView.setAdapter(adapter);
        if (null != getIntent()) {
            String type = getIntent().getStringExtra(KEY_TYPE);
            if (TextUtils.equals(type, FAQActivity.FAQ_TYPE_ACCOUNT)) {
                tvTitle.setText(R.string.faq_account_question);
                getFaqs(FAQActivity.FAQ_TYPE_ACCOUNT);
            } else {
                tvTitle.setText(R.string.faq_charge_question);
                getFaqs(FAQActivity.FAQ_TYPE_CHARGE);
            }
        }
    }

    /**
     * 根据不同问题类型请求
     *
     * @param type
     */
    private void getFaqs(String type) {
        HttpApi.getInstance().service.queryCommonPro(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<QueryCommonProResp>() {
            @Override
            public void onSuccess(QueryCommonProResp queryCommonProResp) {
                if (TextUtils.equals(BaseResponse.CODE_0, queryCommonProResp.getCode())) {
                    details.addAll(queryCommonProResp.getCommonPros());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtils.error("query faq failed, " + e);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_faq_detail;
    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }
}
