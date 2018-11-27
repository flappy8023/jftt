package com.jmtad.jftt.util;

import android.app.Dialog;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.R;
import com.jmtad.jftt.http.HttpApi;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-24 11:03
 **/
public class CheckUpdateUtil {
    public static DownloadBuilder getDownloadBuilder(RequestVersionListener listener) {
        HttpParams params = new HttpParams();
        params.put("type", "0");
        return AllenVersionChecker.getInstance().requestVersion().setRequestUrl(HttpApi.getInstance().BASE_URL + "/sysSetting/queryVersion").setRequestMethod(HttpRequestMethod.POST).setRequestParams(params).request(listener);
    }

    public static CustomVersionDialogListener createVersionDialog(boolean canCancel) {
        return (context, versionBundle) -> {
            Dialog baseDialog = new Dialog(context, R.style.BaseDialog);
            baseDialog.setContentView(R.layout.custom_dialog_two_layout);
            TextView textView = baseDialog.findViewById(R.id.tv_msg);
            textView.setText(versionBundle.getContent());
            baseDialog.setCanceledOnTouchOutside(canCancel);
            baseDialog.setCancelable(canCancel);
            return baseDialog;
        };
    }

    public static DownloadBuilder checkVersion(RequestVersionListener listener, boolean canCancel) {
        return getDownloadBuilder(listener).setCustomVersionDialogListener(createVersionDialog(canCancel));
    }

}
