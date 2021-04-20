package com.metmit.simulation.handler.xpath.parser.expression.token.consumer;

import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenConsumer;

public class ExpressionConsumer implements TokenConsumer {
    public String consume(TokenQueue tokenQueue) {
        if (tokenQueue.matches("(")) {
            return tokenQueue.chompBalanced('(', ')');
        }
        return null;
    }

    public int order() {
        return 0;
    }

    public String tokenType() {
        return "EXPRESSION";
    }
}

