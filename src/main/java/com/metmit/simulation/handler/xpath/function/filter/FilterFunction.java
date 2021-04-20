package com.metmit.simulation.handler.xpath.function.filter;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.function.NameAware;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;

public abstract interface FilterFunction extends NameAware {
    public abstract Object call(ViewImage paramViewImage, List<SyntaxNode> paramList);
}