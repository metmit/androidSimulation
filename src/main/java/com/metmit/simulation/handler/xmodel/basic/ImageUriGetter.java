package com.metmit.simulation.handler.xmodel.basic;

import android.widget.ImageView;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

import de.robv.android.xposed.XposedHelpers;


public class ImageUriGetter implements ValueGetter {
    @Override
    public Object get(ViewImage viewImage) {
        return XposedHelpers.getObjectField(viewImage.getOriginView(), "mUri");
    }

    @Override
    public String attr() {
        return SuperAppium.mUri;
    }

    @Override
    public boolean support(Class type) {
        return ImageView.class.isAssignableFrom(type);
    }
}
