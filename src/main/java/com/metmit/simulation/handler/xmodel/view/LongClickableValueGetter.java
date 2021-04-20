package com.metmit.simulation.handler.xmodel.view;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class LongClickableValueGetter implements ValueGetter<Boolean> {

    @Override
    public Boolean get(ViewImage viewImage) {
        return viewImage.getOriginView().isLongClickable();
    }

    @Override
    public boolean support(Class type) {
        return true;
    }

    @Override
    public String attr() {
        return SuperAppium.longClickable;
    }
}
