package com.metmit.simulation.handler.xmodel.view;


import android.content.Context;
import android.view.View;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xmodel.ValueGetter;

import de.robv.android.xposed.XposedHelpers;

public class PackageNameValueGetter implements ValueGetter<String> {

    @Override
    public String get(ViewImage viewImage) {
        View originView = viewImage.getOriginView();
        Context context = (Context) XposedHelpers.getObjectField(originView, "mContext");
        return context.getPackageName();
    }

    @Override
    public boolean support(Class type) {
        return true;
    }

    @Override
    public String attr() {
        return SuperAppium.packageName;
    }
}
