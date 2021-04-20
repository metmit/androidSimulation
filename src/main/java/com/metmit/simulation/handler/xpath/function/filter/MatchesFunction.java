package com.metmit.simulation.handler.xpath.function.filter;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.exception.EvaluateException;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;


public class MatchesFunction extends AbstractStringFunction {
    @Override
    public Object call(ViewImage element, List<SyntaxNode> params) {
        //Preconditions.checkArgument(params.size() >= 2, getName() + " must has 2 parameters");
        Object calc = params.get(1).calc(element);
        if (!(calc instanceof String)) {
            throw new EvaluateException(getName() + " second parameter is not a string:" + calc);
        }
        return firstParamToString(element, params).matches(calc.toString());
    }

    @Override
    public String getName() {
        return "matches";
    }
}

