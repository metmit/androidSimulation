package com.metmit.simulation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.metmit.simulation.handler.PageContainer;

import java.util.HashMap;

/**
 * 页面跳转
 */
public class Jump {

    /**
     * 从当前activity跳转到指定activity
     *
     * @param activityClassName 类全称
     * @param extras            参数
     */
    public static void jumpActivity(String activityClassName, HashMap<String, String> extras) throws ClassNotFoundException {

        Class<?> clazz = Class.forName(activityClassName, false, PageContainer.getClassLoader());

        Activity activity = PageContainer.getTopActivity();

        Intent intent = new Intent(activity, clazz);

        if (extras != null) {
            for (String key : extras.keySet()) {
                intent.putExtra(key, extras.get(key));
            }
        }

        activity.startActivity(intent);
    }

    /**
     * 跳转到指定包名的指定activity
     *
     * @param packageName       包名
     * @param activityClassName 类全称
     * @param extras            参数
     */
    public static void jumpActivity(String packageName, String activityClassName, HashMap<String, String> extras) {

        Intent intent = new Intent();

        intent.setClassName(packageName, activityClassName);

        if (extras != null) {
            for (String key : extras.keySet()) {
                intent.putExtra(key, extras.get(key));
            }
        }

        PageContainer.getTopActivity().startActivity(intent);
    }

    /**
     * 根据scheme跳转
     */
    public static void jumpActivityByUrl(String url, HashMap<String, String> extras) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // Intent intent = new Intent();
        // intent.setData(Uri.parse(url));

        if (extras != null) {
            for (String key : extras.keySet()) {
                intent.putExtra(key, extras.get(key));
            }
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PageContainer.getTopActivity().startActivity(intent);
    }
}
