package com.metmit.simulation;

import android.app.Instrumentation;
import android.view.KeyEvent;

public class SysCtl {

    /**
     * 模拟按返回键
     * @return boolean
     */
    public static boolean sendBack() {

        boolean result = false;
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
            result = true;
        }catch (Exception ignore) {
        }
        if (!result) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // This method can not be called from the main application thread.
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                }).start();
                result = true;
            } catch (Exception ignore) {
            }
        }
        return result;
    }
}
