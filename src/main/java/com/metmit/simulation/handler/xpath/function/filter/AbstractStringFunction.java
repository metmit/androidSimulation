package com.metmit.simulation.handler.xpath.function.filter;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.exception.EvaluateException;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;

public abstract class AbstractStringFunction implements FilterFunction {
    protected String firstParamToString(ViewImage element, List<SyntaxNode> params) {
        Object string = ((SyntaxNode) params.get(0)).calc(element);
        if (!(string instanceof String)) {
            throw new EvaluateException(getName() + " first parameter is not a string :" + string);
        }
        return string.toString();
    }
}
