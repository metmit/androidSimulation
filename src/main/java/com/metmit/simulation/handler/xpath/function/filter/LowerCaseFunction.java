package com.metmit.simulation.handler.xpath.function.filter;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;

public class LowerCaseFunction extends AbstractStringFunction {
    public Object call(ViewImage element, List<SyntaxNode> params) {
        return firstParamToString(element, params).toLowerCase();
    }

    public String getName() {
        return "lower-case";
    }
}
