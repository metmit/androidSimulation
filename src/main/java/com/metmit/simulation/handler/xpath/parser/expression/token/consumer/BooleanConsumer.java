package com.metmit.simulation.handler.xpath.parser.expression.token.consumer;

import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenConsumer;

public class BooleanConsumer implements TokenConsumer {
    public String consume(TokenQueue tokenQueue) {
        if (tokenQueue.matchesBoolean()) {
            return tokenQueue.consumeWord();
        }
        return null;
    }

    public int order() {
        return 70;
    }

    public String tokenType() {
        return "BOOLEAN";
    }
}
