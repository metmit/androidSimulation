package com.metmit.simulation.handler.xpath.function.axis;


import com.metmit.simulation.handler.ViewImage;

import java.util.List;

public class AncestorFunction implements AxisFunction {
    @Override
    public List<ViewImage> call(ViewImage e, List<String> args) {
        return e.parents();
    }

    @Override
    public String getName() {
        return "ancestor";
    }
}
