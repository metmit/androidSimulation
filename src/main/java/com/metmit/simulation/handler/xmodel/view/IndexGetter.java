package com.metmit.simulation.handler.xmodel.view;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class IndexGetter implements ValueGetter<Integer> {
    @Override
    public Integer get(ViewImage viewImage) {
        return viewImage.index();
    }

    @Override
    public boolean support(Class type) {
        return true;
    }

    @Override
    public String attr() {
        return SuperAppium.index;
    }
}
