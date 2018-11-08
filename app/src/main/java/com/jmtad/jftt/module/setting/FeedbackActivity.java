package com.jmtad.jftt.module.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.StatusBarUtil;

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
    @BindView(R.id.et_feedback_title)
    EditText etTitle;
    @BindView(R.id.et_feedback_content)
    EditText etContent;
    @BindView(R.id.et_feedback_contact)
    EditText etContact;
    @BindView(R.id.bt_feedback_submit)
    Button btSubmit;
    @BindView(R.id.tl_feedback_tab)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.feedback_title);
        ivBack.setImageResource(R.drawable.back_black);
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
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String contact = etContact.getText().toString();
        //常规校验
        if (TextUtils.isEmpty(title)) {
            showMsg("请填写标题");
            etTitle.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            showMsg("请输入内容");
            etContent.requestFocus();
            return;
        }
        if (contact.length() != 11) {
            showMsg("请输入正确手机号");
            etContact.requestFocus();
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FeedbackActivity.this);
                    dialog.setIcon(R.drawable.success);
                    dialog.setTitle("反馈成功");
                    dialog.setMessage("您的反馈已经成功记录");
                    dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
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
