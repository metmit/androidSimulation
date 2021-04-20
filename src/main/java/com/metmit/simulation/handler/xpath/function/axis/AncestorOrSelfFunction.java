package com.metmit.simulation.handler.xpath.function.axis;

import com.metmit.simulation.handler.ViewImage;

import java.util.List;

public class AncestorOrSelfFunction implements AxisFunction {
    public List<ViewImage> call(ViewImage e, List<String> args) {
        List<ViewImage> rs = e.parents();
        rs.add(e);
        return rs;
    }

    public String getName() {
        return "ancestorOrSelf";
    }
}

