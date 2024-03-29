package com.metmit.simulation.handler.xpath.parser.expression;

import com.metmit.simulation.handler.utils.ListUtils;
import com.metmit.simulation.handler.utils.StringUtils;
import com.metmit.simulation.handler.xpath.exception.NoSuchFunctionException;
import com.metmit.simulation.handler.xpath.exception.XpathSyntaxErrorException;
import com.metmit.simulation.handler.xpath.function.FunctionEnv;
import com.metmit.simulation.handler.xpath.function.filter.FilterFunction;
import com.metmit.simulation.handler.xpath.parser.TokenQueue;
import com.metmit.simulation.handler.xpath.parser.expression.node.FunctionNode;
import com.metmit.simulation.handler.xpath.parser.expression.token.Token;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenAnalysisRegistry;
import com.metmit.simulation.handler.xpath.parser.expression.token.TokenConsumer;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by virjar on 17/6/11. 对于函数,解析函数名字,参数列表,决定参数类型
 *
 * @author virjar
 * @since 0.0.1
 */
public class FunctionParser {

    private static final List<String> paramExcludeTypes = ListUtils.newArrayList(Token.OPERATOR);
    private TokenQueue tokenQueue;

    public FunctionParser(TokenQueue tokenQueue) {
        this.tokenQueue = tokenQueue;
    }

    public FunctionNode parse() throws XpathSyntaxErrorException {
        tokenQueue.consumeWhitespace();
        String functionName = tokenQueue.consumeTo("(");
        String params = tokenQueue.chompBalanced('(', ')');
        FilterFunction filterFunction = FunctionEnv.getFilterFunction(functionName);
        if (filterFunction == null) {
            throw new NoSuchFunctionException(0, "not such function:" + functionName);
        }

        List<SyntaxNode> paramList = new LinkedList<>();

        TokenQueue paramTokenQueue = new TokenQueue(StringUtils.trimToEmpty(params));
        while ((paramTokenQueue.consumeWhitespace() && !paramTokenQueue.consumeWhitespace())
                || !paramTokenQueue.isEmpty()) {

            boolean hint = false;
            for (TokenConsumer tokenConsumer : TokenAnalysisRegistry.consumerIterable()) {
                if (excludeForParam(tokenConsumer.tokenType())) {
                    continue;
                }

                String consume = tokenConsumer.consume(paramTokenQueue);
                if (consume == null) {
                    continue;
                }
                hint = true;
                paramList.add(TokenAnalysisRegistry.findHandler(tokenConsumer.tokenType()).parseToken(consume));
                break;
            }

            if (!hint) {
                throw new XpathSyntaxErrorException(paramTokenQueue.nowPosition(), "can not parse param list: "
                        + paramTokenQueue.getQueue() + "  ,for token " + paramTokenQueue.remainder());
            }

            paramTokenQueue.consumeWhitespace();
            if (!paramTokenQueue.isEmpty()) {
                if (paramTokenQueue.peek() != ',') {
                    throw new XpathSyntaxErrorException(paramTokenQueue.nowPosition(), "can not parse param list: \""
                            + paramTokenQueue.getQueue() + "\"  ,for token " + paramTokenQueue.remainder());
                }
                paramTokenQueue.advance();
            }

        }

        return new FunctionNode(filterFunction, paramList);
    }

    private boolean excludeForParam(String tokenType) {
        return paramExcludeTypes.contains(tokenType);
    }
}
