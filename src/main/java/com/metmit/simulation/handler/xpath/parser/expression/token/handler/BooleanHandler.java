package com.metmit.simulation.handler.xpath.parser.expression.token.handler;

import com.metmit.simulation.handler.utils.ListUtils;
import com.metmit.simulation.handler.xpath.exception.XpathSyntaxErrorException;
import com.metmit.simulation.handler.xpath.function.FunctionEnv;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;
import com.metmit.simulation.handler.xpath.parser.expression.node.FunctionNode;
import com.metmit.simulation.handler.xpath.parser.expression.token.Token;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenHandler;


public class BooleanHandler implements TokenHandler {
    @Override
    public SyntaxNode parseToken(final String tokenStr) throws XpathSyntaxErrorException {
        return new FunctionNode(FunctionEnv.getFilterFunction(Boolean.parseBoolean(tokenStr) ? "true" : "false"),
                ListUtils.<SyntaxNode>newLinkedList());
    }

    @Override
    public String typeName() {
        return Token.BOOLEAN;
    }
}
