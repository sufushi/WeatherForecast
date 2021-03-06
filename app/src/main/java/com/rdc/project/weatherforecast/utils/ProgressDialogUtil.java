package com.rdc.project.weatherforecast.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import static com.rdc.project.weatherforecast.constant.Constant.GRAVITY_BOTTOM;
import static com.rdc.project.weatherforecast.constant.Constant.GRAVITY_CENTER;
import static com.rdc.project.weatherforecast.constant.Constant.GRAVITY_TOP;

public class ProgressDialogUtil {
    private static ProgressDialog sProgressDialog;
    private static boolean isShow = false;

    public static void showProgressDialog(Context context, CharSequence msg) {
        sProgressDialog = new ProgressDialog(context);
        sProgressDialog.setMessage(msg);
        sProgressDialog.setCancelable(true);
        sProgressDialog.show();
        isShow = true;
    }

    public static void setMsg(CharSequence msg) {
        if (sProgressDialog != null) {
            sProgressDialog.setMessage(msg);
        }
    }

    public static void setGravity(int gravity) {
        Window window = sProgressDialog.getWindow();
        if (window != null) {
            switch (gravity) {
                case GRAVITY_TOP:
                    window.setGravity(Gravity.TOP);
                    break;
                case GRAVITY_CENTER:
                    window.setGravity(Gravity.CENTER);
                    break;
                case GRAVITY_BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.y = 160;
                    window.setAttributes(layoutParams);
                    break;
                default:
                    window.setGravity(Gravity.CENTER);
                    break;
            }
        }

    }

    public static void dismiss() {
        if (sProgressDialog != null && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
            isShow = false;
        }
    }

    public static boolean isShow() {
        return isShow;
    }
}