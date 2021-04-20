package com.metmit.simulation.handler.xmodel.view;


import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class ClassNameGetter implements ValueGetter<String> {

    @Override
    public String get(ViewImage viewImage) {
        return viewImage.getOriginView().getClass().getName();
    }

    @Override
    public boolean support(Class type) {
        return true;
    }

    @Override
    public String attr() {
        return SuperAppium.className;
    }
}
