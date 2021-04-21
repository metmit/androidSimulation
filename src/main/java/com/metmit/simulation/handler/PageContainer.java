package com.metmit.simulation.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import de.robv.android.xposed.XposedHelpers;


public class PageContainer {
    @SuppressLint("StaticFieldLeak")
    public static Activity topActivity = null;

    @SuppressLint("StaticFieldLeak")
    public static Context mContext = null;

    public static ClassLoader mClassLoader = null;

    private static final Set<WeakReference<Window>> dialogWindowsSets = new CopyOnWriteArraySet<>();

    private static final Set<WeakReference<PopupWindow>> popupWindowSets = new CopyOnWriteArraySet<>();

    private static final Map<String, Object> topFragmentMaps = new ConcurrentHashMap<>();

    public static void setTopActivity(Activity activity) {
        topActivity = activity;
    }

    public static Activity getTopActivity() {
        return topActivity;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setClassLoader(ClassLoader classLoader) {
        mClassLoader = classLoader;
    }

    public static ClassLoader getClassLoader() {
        return mClassLoader;
    }

    public static void addTopFragment(String className, Object object) {
        topFragmentMaps.put(className, object);
    }

    public static void addDialogWindow(Window mWindow) {
        dialogWindowsSets.add(new WeakReference<>(mWindow));
    }

    public static void addPopupWindow(PopupWindow mWindow) {
        popupWindowSets.add(new WeakReference<>(mWindow));
    }

    public static List<Object> getTopFragment() {
        List<Object> ret = new ArrayList<>();
        for (String fragmentClassName : topFragmentMaps.keySet()) {
            Object topFragment = topFragmentMaps.get(fragmentClassName);
            if (topFragment == null) {
                continue;
            }
            boolean isVisible = (boolean) XposedHelpers.callMethod(topFragment, "isVisible");
            if (!isVisible) {
                topFragmentMaps.remove(fragmentClassName);
            } else {
                ret.add(topFragment);
            }
        }
        return ret;
    }

    public static Window getTopDialogWindow() {
        List<Window> windows = getTopDialogWindows();
        if (windows.size() >= 1) return windows.get(0);
        return null;
    }

    public static List<Window> getTopDialogWindows() {

        List<Window> ret = new ArrayList<>();

        for (WeakReference<Window> windowWeakReference : dialogWindowsSets) {
            Window window = windowWeakReference.get();
            if (window == null) {
                dialogWindowsSets.remove(windowWeakReference);
                continue;
            }
            if (window.getDecorView().getVisibility() != View.VISIBLE) {
                continue;
            }
            if (!window.getDecorView().hasWindowFocus()) {
                continue;
            }
            ret.add(window);
        }
        return ret;
    }

    public static PopupWindow getTopPupWindow() {
        List<PopupWindow> windows = getTopPupWindows();
        if (windows.size() >= 1) return windows.get(0);
        return null;
    }

    public static List<PopupWindow> getTopPupWindows() {
        List<PopupWindow> ret = new ArrayList<>();
        for (WeakReference<PopupWindow> popupWindowWeakReference : popupWindowSets) {
            PopupWindow popupWindow = popupWindowWeakReference.get();
            if (popupWindow == null) {
                popupWindowSets.remove(popupWindowWeakReference);
                continue;
            }
            View mDecorView = (View) XposedHelpers.getObjectField(popupWindow, "mDecorView");
            if (mDecorView == null) {
                continue;
            }
            if (mDecorView.getVisibility() != View.VISIBLE) {
                continue;
            }
            ret.add(popupWindow);
        }
        return ret;
    }
}
