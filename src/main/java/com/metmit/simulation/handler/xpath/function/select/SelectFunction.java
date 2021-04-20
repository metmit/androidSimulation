package com.metmit.simulation.handler.xpath.function.select;

import com.metmit.simulation.handler.ViewImages;
import com.metmit.simulation.handler.xpath.function.NameAware;
import com.metmit.simulation.handler.xpath.model.XNodes;
import com.metmit.simulation.handler.xpath.model.XpathNode;

import java.util.List;

public interface SelectFunction extends NameAware {
    XNodes call(XpathNode.ScopeEm scopeEm, ViewImages elements, List<String> args);
}
