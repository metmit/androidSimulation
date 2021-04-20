package com.metmit.simulation.handler.xpath.function.axis;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;

import java.util.List;

public class FollowingSiblingOneFunction implements AxisFunction {
    @Override
    public ViewImages call(ViewImage e, List<String> args) {
        ViewImages rs = new ViewImages();
        if (e.nextSibling() != null) {
            rs.add(e.nextSibling());
        }
        return rs;
    }

    @Override
    public String getName() {
        return "following-sibling-one";
    }
}
