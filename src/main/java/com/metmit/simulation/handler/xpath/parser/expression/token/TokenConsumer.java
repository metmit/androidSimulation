package com.metmit.simulation.handler.xpath.parser.expression.token;

import com.metmit.simulation.handler.xpath.parser.TokenQueue;

public abstract interface TokenConsumer {
    public abstract String consume(TokenQueue paramTokenQueue);

    public abstract int order();

    public abstract String tokenType();
}


/* Location:              /Users/alienhe/.gradle/caches/modules-2/files-2.1/com.virjar/ratel-extersion/1.0.4/c3247d9a6d2e125b04726c3b311ee721ef979ad2/ratel-extersion-1.0.4.jar!/com/virjar/ratel/api/extension/superappium/xpath/parser/expression/token/TokenConsumer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */