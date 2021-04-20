package com.metmit.simulation;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.metmit.simulation.handler.ViewImage;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// import de.robv.android.xposed.XposedHelpers;

import static android.view.MotionEvent.TOOL_TYPE_FINGER;

/**
 * 点击事件
 */
public class Click {

    /**
     * 点击
     */
    public static boolean click(ViewImage viewImage) {

        // 如果上层有 view，能点击吗？
        if (!clickByPoint(viewImage)) { // 根据坐标点击
            //开始尝试对ListItem进行点击，ListItem需要List事件分发(onItemClick)才会生效
            ViewImage parentViewImage = viewImage.parentNode();
            if (parentViewImage != null) {
                View parentOriginView = parentViewImage.getOriginView();
                if (parentOriginView instanceof AdapterView) {
                    if (!viewImage.getOriginView().performClick()) {
                        return clickAdapterView((AdapterView) parentOriginView, viewImage.getOriginView());
                    }
                }
            }
        }

        if (viewImage.getOriginView().isClickable()) {
            if (viewImage.getOriginView().performClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据坐标点击元素
     */
    public static boolean click(ViewImage viewImage, final float x, final float y) {

        if (!dispatchInputEvent(viewImage, createFingerMotionEvent(MotionEvent.ACTION_DOWN, x, y))) {
            return false;
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dispatchInputEvent(viewImage, createFingerMotionEvent(MotionEvent.ACTION_UP, x, y));
            }
        }, ThreadLocalRandom.current().nextInt(25) + 10);
        return true;
    }

    /**
     * 根据节点的坐标及宽高计算出可点击的坐标数，然后按坐标点击
     */
    public static boolean clickByPoint(ViewImage viewImage) {

        float[] floats = measureClickPoint(viewImage);

        // 获取根节点
        View rootView = viewImage.rootViewImage().getOriginView();
        int[] location = new int[2];
        rootView.getLocationOnScreen(location); // 一个控件在其整个屏幕上的坐标位置

        final float locationOnRootViewX = floats[0] - location[0];
        final float locationOnRootViewY = floats[1] - location[1];

        // 点击到屏幕外面了
        if (locationOnRootViewX < 0 || locationOnRootViewY < 0
                || locationOnRootViewX > rootView.getWidth() || locationOnRootViewY > rootView.getHeight()) {
            return false;
        }

        return click(viewImage, floats[0], floats[1]);
    }


    /**
     * 尝试对ListItem 进行点击
     */
    private static boolean clickAdapterView(AdapterView parent, View mView) {

        final int position = parent.getPositionForView(mView);
        final long itemId = parent.getAdapter() != null
                ? parent.getAdapter().getItemId(position)
                : 0;
        if (position != AdapterView.INVALID_POSITION) {
            if (parent.performItemClick(mView, position, itemId)) {
                return true;
            }
            AdapterView.OnItemClickListener onItemClickListener = parent.getOnItemClickListener();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(parent, mView, position, itemId);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 分发事件
     */
    public static boolean dispatchInputEvent(ViewImage viewImage, InputEvent inputEvent) {
        // TODO 反射

        // View rootView = viewImage.rootViewImage().getOriginView();
        // final Object mViewRootImpl = XposedHelpers.callMethod(rootView, "getViewRootImpl");
        // if (mViewRootImpl == null) {
        //     return false;
        // }
        // XposedHelpers.callMethod(mViewRootImpl, "dispatchInputEvent", inputEvent);
        return true;
    }

    /**
     * 根据节点 坐标 及 宽高，计算出可以点击的坐标
     */
    private static float[] measureClickPoint(ViewImage viewImage) {
        int[] locations = new int[2];
        viewImage.getOriginView().getLocationOnScreen(locations); // 一个控件在其整个屏幕上的坐标位置
        float x = locations[0];//+ ((float) originView.getWidth() / 4) + random.nextInt(originView.getWidth() / 4);
        float y = locations[1];//+ ((float) originView.getHeight() / 4) + random.nextInt(originView.getHeight() / 4);

        if (viewImage.getWidth() > 5) {
            x += ((float) viewImage.getWidth() / 4) + new Random().nextInt(viewImage.getWidth() / 4);
        }
        if (viewImage.getHeight() > 5) {
            y += ((float) viewImage.getHeight() / 4) + new Random().nextInt(viewImage.getHeight() / 4);
        }

        float[] ret = new float[2];
        ret[0] = x;
        ret[1] = y;
        return ret;
    }

    private static MotionEvent createFingerMotionEvent(long downTime, long eventTime, int action, float x, float y) {
        return MotionEvent.obtain(downTime, eventTime, action, x, y, 0);
    }

    /**
     * 创建动作事件
     */
    private static MotionEvent createFingerMotionEvent(int action, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = downTime + 50;
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = 0;
        // pointerProperties.id = view.getId();  // TODO 似乎应该是 0、1、2 ，崩端应该是因为这个
        pointerProperties.toolType = TOOL_TYPE_FINGER; // 设置滑动类型防止部分App 对滑动类型进行检测，影响点击
        MotionEvent.PointerProperties[] pointerPropertiesArray = new MotionEvent.PointerProperties[]{pointerProperties};
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = x;
        pointerCoords.y = y;
        MotionEvent.PointerCoords[] pointerCoordsArray = new MotionEvent.PointerCoords[]{pointerCoords};
        return MotionEvent.obtain(downTime, eventTime, action, 1, pointerPropertiesArray, pointerCoordsArray,
                0, 0, 0, 0,
                8, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
    }
}
