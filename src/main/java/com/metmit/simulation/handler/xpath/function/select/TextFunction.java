package com.metmit.simulation.handler.xpath.function.select;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;
import com.metmit.simulation.handler.xpath.model.XNode;
import com.metmit.simulation.handler.xpath.model.XNodes;
import com.metmit.simulation.handler.xpath.model.XpathNode;

import java.util.List;

public class TextFunction implements SelectFunction {
    /**
     * 只获取节点自身的子文本
     */
    @Override
    public XNodes call(XpathNode.ScopeEm scopeEm, ViewImages elements, List<String> args) {
        XNodes res = new XNodes();
        if (elements != null && elements.size() > 0) {
            for (ViewImage e : elements) {
                res.add(XNode.t(e.getText()));
            }
        }
        return res;
    }

    @Override
    public String getName() {
        return "text";
    }
}

