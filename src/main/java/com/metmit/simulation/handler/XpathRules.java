package com.metmit.simulation.handler;

public class XpathRules {

    public static String textView(String text) {
        return String.format("//android.widget.TextView[@text='%s']", text);
    }

    public static String textViewContains(String text) {
        return String.format("//android.widget.TextView[contains(@text, '%s')]", text);
    }
}
