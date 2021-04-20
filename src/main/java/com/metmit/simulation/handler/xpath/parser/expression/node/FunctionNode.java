package com.metmit.simulation.handler.xpath.parser.expression.node;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.function.filter.FilterFunction;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;

public class FunctionNode implements SyntaxNode {
    private FilterFunction filterFunction;
    private List<SyntaxNode> filterFunctionParams;


    public FunctionNode(FilterFunction filterFunction, List<SyntaxNode> filterFunctionParams) {
        this.filterFunction = filterFunction;
        this.filterFunctionParams = filterFunctionParams;
    }


    @Override
    public Object calc(ViewImage viewImage) {
        return filterFunction.call(viewImage, filterFunctionParams);
    }
}
