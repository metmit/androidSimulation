package com.metmit.simulation.handler.xpath.parser.expression.token.handler;

import com.metmit.simulation.handler.xpath.exception.XpathSyntaxErrorException;
import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.FunctionParser;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;
import com.metmit.simulation.handler.xpath.parser.expression.token.Token;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenHandler;

public class FunctionHandler implements TokenHandler {
    @Override
    public SyntaxNode parseToken(String tokenStr) throws XpathSyntaxErrorException {
        return new FunctionParser(new TokenQueue(tokenStr)).parse();
    }

    @Override
    public String typeName() {
        return Token.FUNCTION;
    }
}
