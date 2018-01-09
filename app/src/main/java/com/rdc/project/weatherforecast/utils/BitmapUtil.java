package com.rdc.project.weatherforecast.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BitmapUtil {

    public static void setImageViewMathParent(Activity context, ImageView image, Bitmap bitmap) {
        //获得屏幕密度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay()
                .getMetrics(displayMetrics);
        //获得屏幕宽度和图片宽度的比例
        float scale = (float)displayMetrics.widthPixels
                / (float) bitmap.getWidth();
        //获得ImageView的参数类
        ViewGroup.LayoutParams vgl=image.getLayoutParams();
        //设置ImageView的宽度为屏幕的宽度
        vgl.width=displayMetrics.widthPixels;
        //设置ImageView的高度
        vgl.height=(int) (bitmap.getHeight() * scale);
        //设置图片充满ImageView控件
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        //等比例缩放
        image.setAdjustViewBounds(true);
        image.setLayoutParams(vgl);
        image.setImageBitmap(bitmap);

        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }

}
