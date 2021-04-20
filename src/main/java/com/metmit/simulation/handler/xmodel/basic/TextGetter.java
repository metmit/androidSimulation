package com.metmit.simulation.handler.xmodel.basic;


import android.widget.TextView;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class TextGetter implements ValueGetter<String> {
    @Override
    public String get(ViewImage viewImage) {
        TextView textView = (TextView) viewImage.getOriginView();
        CharSequence text = textView.getText();
        if (text == null) {
            return null;
        }
        return text.toString();
    }

    @Override
    public boolean support(Class type) {
        return TextView.class.isAssignableFrom(type);
    }

    @Override
    public String attr() {
        return SuperAppium.text;
    }
}
