package com.jmtad.jftt.module.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.FeedbackAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.dialog.CommonDialog;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 反馈页面
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.bt_feedback_submit)
    Button btSubmit;
    @BindView(R.id.tl_feedback_tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_feedback)
    ViewPager viewPager;
    FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.feedback_title);
        ivBack.setImageResource(R.drawable.back_black);
        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FeedBackFragment());
        fragments.add(new FeedBackFragment());
        feedbackAdapter = new FeedbackAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(feedbackAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    slidrInterface.lock();
                } else {
                    slidrInterface.unlock();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.bt_feedback_submit)
    public void submit() {
        FeedBackFragment fragment = (FeedBackFragment) feedbackAdapter.getItem(viewPager.getCurrentItem());
        String title = fragment.getTitle();
        String content = fragment.getContent();
        String contact = fragment.getContact();
        //进行校验
        if (!fragment.check(title, content, contact)) {
            return;
        }
        //0 功能建议 ；1 性能问题
        String type = "";
        if (tabLayout.getSelectedTabPosition() == 0) {
            type = "0";
        } else {
            type = "1";
        }
        HttpApi.getInstance().service.saveProblem(SharedPreferenceUtil.getInstance().getUserId(), title, contact, "", contact, type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (TextUtils.equals(BaseResponse.CODE_0, baseResponse.getCode())) {
                    new CommonDialog(FeedbackActivity.this, R.style.BaseDialog, "您的反馈已经成功记录").setTitle("反馈成功").setPositiveButton("好的").setListener(new CommonDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.dismiss();
                            //反馈成功后清除填写的内容
                            fragment.reset();
                        }
                    }).show();
                } else {
                    showMsg("反馈失败,请稍后再试");
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtils.error(e);
            }
        });
    }
}
