package com.metmit.simulation.handler.xmodel.basic;

import android.widget.TextView;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class HintGetter implements ValueGetter {
    @Override
    public Object get(ViewImage viewImage) {
        TextView textView = (TextView) viewImage.getOriginView();
        CharSequence hint = textView.getHint();
        if (hint == null) {
            return null;
        }
        return hint.toString();
    }

    @Override
    public String attr() {
        return SuperAppium.hint;
    }

    @Override
    public boolean support(Class type) {
        return TextView.class.isAssignableFrom(type);
    }
}