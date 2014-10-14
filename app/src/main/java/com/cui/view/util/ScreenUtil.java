package com.cui.view.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕工具类，需初始化后使用
 * @author cui
 *
 */
public class ScreenUtil {

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    /**
     * 将dip转换为像素单位
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将像素转换为dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Activity context) {
        if (SCREEN_WIDTH == 0 && null != context) {
            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            SCREEN_WIDTH = dm.widthPixels;
        }
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(Activity context) {
        if (SCREEN_HEIGHT == 0 && null != context) {
            DisplayMetrics dm = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(dm);
            SCREEN_HEIGHT = dm.heightPixels;
        }
        return SCREEN_HEIGHT;
    }

    public static void initScreenData(Activity context) {
        getScreenWidth(context);
        getScreenHeight(context);
    }
}
