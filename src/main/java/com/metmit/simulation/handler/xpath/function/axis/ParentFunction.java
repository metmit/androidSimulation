package com.metmit.simulation.handler.xpath.function.axis;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;

import java.util.List;


public class ParentFunction implements AxisFunction {
    @Override
    public ViewImages call(ViewImage e, List<String> args) {
        return new ViewImages(e.parentNode());
    }

    @Override
    public String getName() {
        return "parent";
    }
}
