package com.metmit.simulation.handler.xpath.parser.expression;

import com.metmit.simulation.handler.ViewImage;

public abstract interface SyntaxNode {
    public abstract Object calc(ViewImage paramViewImage);
}

