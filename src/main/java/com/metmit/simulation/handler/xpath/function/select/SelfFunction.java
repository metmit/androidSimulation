package com.metmit.simulation.handler.xpath.function.select;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;
import com.metmit.simulation.handler.xpath.model.XNode;
import com.metmit.simulation.handler.xpath.model.XNodes;
import com.metmit.simulation.handler.xpath.model.XpathNode;

import java.util.List;


public class SelfFunction implements SelectFunction {
    @Override
    public XNodes call(XpathNode.ScopeEm scopeEm, ViewImages elements, List<String> args) {
        XNodes xNodes = new XNodes();
        for (ViewImage viewImage : elements) {
            xNodes.add(XNode.e(viewImage));
        }
        return xNodes;
    }

    @Override
    public String getName() {
        return "self";
    }
}
