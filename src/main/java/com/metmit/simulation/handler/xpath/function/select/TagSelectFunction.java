package com.metmit.simulation.handler.xpath.function.select;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;
import com.metmit.simulation.handler.traversor.Collector;
import com.metmit.simulation.handler.traversor.Evaluator;
import com.metmit.simulation.handler.xpath.XpathUtil;
import com.metmit.simulation.handler.xpath.model.XNodes;
import com.metmit.simulation.handler.xpath.model.XpathNode;

import java.util.LinkedList;
import java.util.List;


public class TagSelectFunction implements SelectFunction {
    @Override
    public XNodes call(XpathNode.ScopeEm scopeEm, ViewImages elements, List<String> args) {
        String tagName = args.get(0);
        List<ViewImage> temp = new LinkedList<>();

        if (scopeEm == XpathNode.ScopeEm.RECURSIVE || scopeEm == XpathNode.ScopeEm.CURREC) {// 递归模式
            Evaluator evaluator;
            if ("*".equals(tagName)) {
                evaluator = new Evaluator.AllElements();
            } else {
                evaluator = new Evaluator.ByTag(tagName);
            }
            for (ViewImage element : elements) {
                temp.addAll(Collector.collect(evaluator, element));
            }
            if (scopeEm == XpathNode.ScopeEm.RECURSIVE) {
                //向下递归,不应该包含自身
                temp.removeAll(elements);
            }

            return XpathUtil.transform(temp);
        }

        // 直接子代查找
        if ("*".equals(tagName)) {
            for (ViewImage element : elements) {
                temp.addAll(element.children());
            }
        } else {
            for (ViewImage element : elements) {
                for (ViewImage child : element.children()) {
                    if (child.getType().equals(tagName)) {
                        temp.add(child);
                    }
                }
            }
        }
        return XpathUtil.transform(temp);
    }

    @Override
    public String getName() {
        return "tag";
    }
}

