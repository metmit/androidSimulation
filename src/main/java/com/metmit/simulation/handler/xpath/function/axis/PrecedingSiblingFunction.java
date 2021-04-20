package com.metmit.simulation.handler.xpath.function.axis;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;

import java.util.LinkedList;
import java.util.List;


public class PrecedingSiblingFunction implements AxisFunction {
    @Override
    public ViewImages call(ViewImage e, List<String> args) {
        ViewImage tmp = e.previousSibling();
        LinkedList<ViewImage> tempList = new LinkedList<>();
        while (tmp != null) {
            tempList.addFirst(tmp);
            tmp = tmp.previousSibling();
        }
        return new ViewImages(tempList);
    }

    @Override
    public String getName() {
        return "preceding-sibling";
    }
}
