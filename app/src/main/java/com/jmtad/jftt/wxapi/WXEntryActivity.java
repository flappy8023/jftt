package com.jmtad.jftt.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jmtad.jftt.util.wechat.WechatUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WechatUtil.getInstance().getApi().handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // 成功
                int type = baseResp.getType();


                if (type == ConstantsAPI.COMMAND_SENDAUTH) {
                    // 授权
                    String code = ((SendAuth.Resp) baseResp).code;
                    WechatUtil.getInstance().checkAccessToken(code);
                    break;
                }

                if (type == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
                    // 分享
//                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                    break;
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                // 用户取消
//                WechatHelper.getInstance().sendUserConfirmMessage(WECHAT_CHECK_FAILURE);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                // 用户拒绝
//                WechatHelper.getInstance().sendUserConfirmMessage(WECHAT_CHECK_FAILURE);
                break;
            default:
//                WechatHelper.getInstance().sendUserConfirmMessage(WECHAT_CHECK_FAILURE);
        }

        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        WechatUtil.getInstance().getApi().handleIntent(intent, this);

        finish();
    }
}

