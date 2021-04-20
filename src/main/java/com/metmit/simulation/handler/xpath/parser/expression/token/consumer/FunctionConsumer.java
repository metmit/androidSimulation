package com.metmit.simulation.handler.xpath.parser.expression.token.consumer;

import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenConsumer;

public class FunctionConsumer implements TokenConsumer {
    public String consume(TokenQueue tokenQueue) {
        if (tokenQueue.matchesFunction()) {
            return tokenQueue.consumeFunction();
        }
        return null;
    }

    public int order() {
        return 60;
    }

    public String tokenType() {
        return "FUNCTION";
    }
}
