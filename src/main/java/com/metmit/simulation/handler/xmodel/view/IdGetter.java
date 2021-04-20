package com.metmit.simulation.handler.xmodel.view;


import android.content.res.Resources;
import android.view.View;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

public class IdGetter implements ValueGetter<String> {
    @Override
    public String get(ViewImage viewImage) {
        View originView = viewImage.getOriginView();
        int id = originView.getId();
        if (id <= 0) {
            return null;
        }
        try {
            return originView.getResources().getResourceName(id);
        } catch (Resources.NotFoundException e) {
            //这里可能报错
            return null;
        }
    }

    @Override
    public boolean support(Class type) {
        return true;
    }

    @Override
    public String attr() {
        return SuperAppium.id;
    }
}
