package com.jmtad.jftt.module.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.dialog.CommonDialog;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.CheckUpdateResp;
import com.jmtad.jftt.module.login.ui.LoginActivity;
import com.jmtad.jftt.util.ApkUtil;
import com.jmtad.jftt.util.CacheUtil;
import com.jmtad.jftt.util.CheckUpdateUtil;
import com.jmtad.jftt.util.JsonParse;
import com.jmtad.jftt.util.StatusBarUtil;
import com.jmtad.jftt.util.ThreadPoolUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivTitleLeft;

    @BindView(R.id.tv_total_cache)
    TextView totalCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected void initView() {
        ivTitleLeft.setImageResource(R.drawable.back_black);
        tvTitle.setText(getString(R.string.setting_title));
        showCache();
    }

    /**
     * 显示当前缓存
     */
    private void showCache() {
        String cache = "";
        try {
            cache = CacheUtil.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        totalCache.setText(cache);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initPresenter() {

    }


    /**
     * 清除缓存
     */
    @OnClick(R.id.ll_setting_clear_cache)
    public void clearCache() {
        new CommonDialog(SettingActivity.this, R.style.BaseDialog, getString(R.string.makesure_clear_cache)).setPositiveButton(getString(R.string.sure)).setTitle("提示").setListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                ThreadPoolUtil.execute(() -> {
                    CacheUtil.clearAllCache(SettingActivity.this);
                    runOnUiThread(() -> {
                        showMsg(getString(R.string.clear_done));
                        dialog.dismiss();
                        showCache();
                    });
                });
            }
        }).show();
    }

    /**
     * 检查更新
     */
    @OnClick(R.id.ll_setting_check_update)
    public void checkUpdate() {
        DownloadBuilder builder = CheckUpdateUtil.checkVersion(new RequestVersionListener() {
            @Nullable
            @Override
            public UIData onRequestVersionSuccess(String result) {
                CheckUpdateResp resp = JsonParse.json2Object(result, CheckUpdateResp.class);
                if (null != resp && null != resp.getData() && TextUtils.equals(BaseResponse.CODE_0, resp.getCode())) {
                    //如果返回版本号大于本地版本号,弹出更新对话框
                    if (ApkUtil.getVersionCode(SettingActivity.this) < resp.getData().getIdenNumber()) {
                        UIData data = UIData.create();
                        data.setContent(resp.getData().getExplainText());
                        data.setDownloadUrl(resp.getData().getDownloadUrl());
                        data.setTitle(resp.getData().getTitle());
                        return data;
                    } else {
                        showMsg("已经是最新版本");
                    }
                } else {
                    showError("请稍后再试");

                }

                return null;
            }

            @Override
            public void onRequestVersionFailure(String message) {

            }
        }, true);
        builder.setShowDownloadingDialog(false);
        builder.executeMission(this);

    }

    /**
     * 跳转反馈页面
     */
    @OnClick(R.id.ll_setting_feedback)
    public void feedback() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    /**
     * 跳转关于我们页面
     */
    @OnClick(R.id.ll_setting_aboutUs)
    public void aboutUs() {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    /**
     * 切换账号
     */
    @OnClick(R.id.bt_switch_account)
    public void swtichAccount() {
        Intent intent = new Intent(this, LoginActivity.class);
        //清除栈内其他activity,跳转到登录页面
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
