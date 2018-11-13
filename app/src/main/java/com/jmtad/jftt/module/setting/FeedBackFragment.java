package com.jmtad.jftt.module.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseMvpFragment;

import butterknife.BindView;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-13 10:33
 **/
public class FeedBackFragment extends BaseMvpFragment {
    @BindView(R.id.et_feedback_title)
    EditText etTitle;
    @BindView(R.id.et_feedback_content)
    EditText etContent;
    @BindView(R.id.et_feedback_contact)
    EditText etContact;

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_feedback;
    }

    @Override
    protected void initPresenter() {

    }

    public String getTitle() {
        return etTitle.getText().toString();
    }

    public String getContent() {
        return etContent.getText().toString();
    }

    public String getContact() {
        return etContact.getText().toString();
    }

    public boolean check(String title, String content, String contact) {
        //常规校验
        if (TextUtils.isEmpty(title)) {
            showError("请填写标题");
            etTitle.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(content)) {
            showError("请输入内容");
            etContent.requestFocus();
            return false;
        }
        if (contact.length() != 11) {
            showError("请输入正确手机号");
            etContact.requestFocus();
            return false;
        }
        return true;
    }

    public void reset() {
        etTitle.setText("");
//        etContact.setText("");
        etContent.setText("");
    }
}
