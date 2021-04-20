package com.metmit.simulation.handler.xpath.parser.expression.token.handler;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.XpathParser;
import com.metmit.simulation.handler.xpath.exception.XpathSyntaxErrorException;
import com.metmit.simulation.handler.xpath.model.XNode;
import com.metmit.simulation.handler.xpath.model.XNodes;
import com.metmit.simulation.handler.xpath.model.XpathEvaluator;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;
import com.metmit.simulation.handler.xpath.parser.expression.token.Token;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenHandler;

public class XpathHandler implements TokenHandler {
    @Override
    public SyntaxNode parseToken(String tokenStr) throws XpathSyntaxErrorException {
        final XpathEvaluator xpathEvaluator = new XpathParser(tokenStr).parse();
        return new SyntaxNode() {
            @Override
            public Object calc(ViewImage element) {
                return xpathEvaluator.evaluate(new XNodes(XNode.e(element)));
            }
        };
    }

    @Override
    public String typeName() {
        return Token.XPATH;
    }
}
