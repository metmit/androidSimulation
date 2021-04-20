package com.metmit.simulation.handler.xpath.parser.expression.token.handler;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.utils.NumberUtils;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;
import com.metmit.simulation.handler.xpath.parser.expression.token.Token;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenHandler;


public class NumberHandler implements TokenHandler {
    @Override
    public SyntaxNode parseToken(final String tokenStr) {
        return new SyntaxNode() {
            @Override
            public Object calc(ViewImage element) {
                if (tokenStr.contains(".")) {
                    return NumberUtils.toDouble(tokenStr);
                } else {
                    return NumberUtils.toInt(tokenStr);
                }
            }
        };
    }

    @Override
    public String typeName() {
        return Token.NUMBER;
    }
}
