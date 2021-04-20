package com.metmit.simulation.handler.xpath.parser.expression.token.consumer;

import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenConsumer;

public class DigitConsumer implements TokenConsumer {
    public String consume(TokenQueue tokenQueue) {
        if (tokenQueue.matchesDigit()) {
            return tokenQueue.consumeDigit();
        }
        return null;
    }

    public int order() {
        return 50;
    }

    public String tokenType() {
        return "NUMBER";
    }
}