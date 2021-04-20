package com.metmit.simulation.handler.xpath.function.filter;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;

public class FalseFunction implements FilterFunction {
    public Object call(ViewImage element, List<SyntaxNode> params) {
        return Boolean.valueOf(false);
    }

    public String getName() {
        return "false";
    }
}

