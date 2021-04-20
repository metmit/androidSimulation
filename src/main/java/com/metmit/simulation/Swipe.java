package com.metmit.simulation;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.metmit.simulation.handler.ViewImage;

import java.lang.ref.WeakReference;

import static android.view.MotionEvent.TOOL_TYPE_FINGER;

/**
 * 滑动事件
 */
public class Swipe {

    /**
     * 默认滑动时间
     */
    private static final long DEFAULT_DURATION = 1000;

    /**
     * 在指定的activity上滑动
     */
    public static void simulateScroll(Activity activity, int startX, int startY, int endX, int endY) {
        simulateScroll(new ViewImage(activity), startX, startY, endX, endY, DEFAULT_DURATION);
    }

    /**
     * 模拟手势滑动指定的View
     */
    public static void simulateScroll(ViewImage view, int startX, int startY, int endX, int endY) {
        simulateScroll(view, startX, startY, endX, endY, DEFAULT_DURATION);
    }

    /**
     * 模拟手势滑动指定的View，滑动时长为 ms
     */
    public static void simulateScroll(ViewImage view, int startX, int startY, int endX, int endY, long duration) {
        dealSimulateScroll(view, startX, startY, endX, endY, duration);
    }

    /**
     * 模拟手势滑动指定的View，滑动时长为 ms
     */
    public static void simulateScroll(ViewImage view, float startX, float startY, float endX, float endY, long duration) {
        dealSimulateScroll(view, startX, startY, endX, endY, duration);
    }

    /**
     * 滑动
     */
    private static void dealSimulateScroll(ViewImage object, float startX, float startY, float endX, float endY, long duration) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = downTime + 50;

        MotionEvent motionEvent = createFingerMotionEvent(downTime, eventTime, MotionEvent.ACTION_DOWN, startX, startY);

        object.getOriginView().dispatchTouchEvent(motionEvent);

        Handler handler = new ViewHandler(object);
        GestureBean bean = new GestureBean(startX, startY, endX, endY, duration);
        Message.obtain(handler, 1, bean).sendToTarget();
    }

    static class ViewHandler extends Handler {
        WeakReference<ViewImage> mView;

        ViewHandler(ViewImage activity) {
            super(Looper.getMainLooper());
            mView = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ViewImage theView = mView.get();
            if (theView == null) {
                return;
            }
            long downTime = SystemClock.uptimeMillis();
            GestureBean bean = (GestureBean) msg.obj;
            long count = bean.count;
            if (count >= bean.totalCount) {
                theView.getOriginView().dispatchTouchEvent(
                        createFingerMotionEvent(downTime, downTime, MotionEvent.ACTION_UP, bean.endX, bean.endY)
                );
            } else {
                theView.getOriginView().dispatchTouchEvent(
                        createFingerMotionEvent(downTime, downTime, MotionEvent.ACTION_MOVE, bean.startX + bean.ratioX * count, bean.startY + bean.ratioY * count)
                );
                bean.count++;
                Message message = new Message();
                message.obj = bean;
                sendMessageDelayed(message, bean.speed);
            }
        }
    }

    static class GestureBean {
        float startX;
        float startY;
        float endX;
        float endY;
        /**
         * 每个周期 x 移动的位置
         */
        float ratioX;
        /**
         * 每个周期 y 移动的位置
         */
        float ratioY;
        /**
         * 总共周期
         */
        long totalCount;
        /**
         * 当前周期
         */
        long count = 0;
        /**
         * 每周期 x 毫秒
         * 比如同样滑动 500像素：
         *      设置总时长为 100 毫秒，需要滑动10次，滑动速度较快
         *      设置总时长为 200 毫秒，需要滑动20次，滑动速度较慢
         */
        int speed = 10;

        GestureBean(float startX, float startY, float endX, float endY, long duration) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            totalCount = duration / speed;
            ratioX = (endX - startX) / totalCount;
            ratioY = (endY - startY) / totalCount;
        }
    }

    private static MotionEvent createFingerMotionEvent(long downTime, long eventTime, int action, float x, float y) {
        return MotionEvent.obtain(downTime, eventTime, action, x, y, 0);
    }

    private static MotionEvent createFingerMotionEvent(long downTime, long eventTime, int action, float x, float y, int metaState) {
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = 0;
        // pointerProperties.id = view.getId();  // TODO 似乎应该是 0、1、2 ，崩端应该是因为这个
        pointerProperties.toolType = TOOL_TYPE_FINGER;
        MotionEvent.PointerProperties[] pointerPropertiesArray = new MotionEvent.PointerProperties[]{pointerProperties};

        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = x;
        pointerCoords.y = y;
        MotionEvent.PointerCoords[] pointerCoordsArray = new MotionEvent.PointerCoords[]{pointerCoords};
        // @param deviceId The id for the device that this event came from.  An id of zero indicates that the event didn't come from a physical device;
        return MotionEvent.obtain(downTime, eventTime, action, 1, pointerPropertiesArray, pointerCoordsArray,
                0, 0, 0, 0, 8, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
    }
}
