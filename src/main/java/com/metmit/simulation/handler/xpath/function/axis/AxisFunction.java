package com.metmit.simulation.handler.xpath.function.axis;


import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.function.NameAware;

import java.util.List;

public abstract interface AxisFunction extends NameAware {
    public abstract List<ViewImage> call(ViewImage paramViewImage, List<String> paramList);
}
