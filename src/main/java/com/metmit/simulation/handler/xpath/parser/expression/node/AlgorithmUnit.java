package com.metmit.simulation.handler.xpath.parser.expression.node;

import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

public abstract class AlgorithmUnit implements SyntaxNode {
    protected SyntaxNode left = null;
    protected SyntaxNode right = null;

    public void setLeft(SyntaxNode left) {
        this.left = left;
    }

    public void setRight(SyntaxNode right) {
        this.right = right;
    }
}
