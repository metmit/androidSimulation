package com.metmit.simulation.handler.xpath.parser.expression.operator;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.node.AlgorithmUnit;

/**
 * Created by virjar on 17/6/10.
 */
@OpKey(value = "||", priority = 0)
public class OrUnit extends AlgorithmUnit {
    @Override
    public Object calc(ViewImage element) {
        Object leftValue = left.calc(element);
        Object rightValue = right.calc(element);
        // 左边为true,右边不管是啥,都为真
        if (leftValue != null && leftValue instanceof Boolean && (Boolean) leftValue) {
            return true;
        }

        // 左边不为真,以右边为主
        if (rightValue != null && rightValue instanceof Boolean && (Boolean) rightValue) {
            return true;
        }
        return Boolean.FALSE;
    }
}
