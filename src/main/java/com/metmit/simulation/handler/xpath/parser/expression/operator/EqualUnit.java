package com.metmit.simulation.handler.xpath.parser.expression.operator;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.node.AlgorithmUnit;


/**
 * Created by virjar on 17/6/10.
 */
@OpKey(value = "=", priority = 10)
public class EqualUnit extends AlgorithmUnit {
    @Override
    public Object calc(ViewImage element) {
        Object leftValue = left.calc(element);
        Object rightValue = right.calc(element);
        if (leftValue == null && rightValue == null) {
            return Boolean.TRUE;
        }
        if (leftValue == null || rightValue == null) {
            return Boolean.FALSE;
        }

        return leftValue.equals(rightValue);
    }
}