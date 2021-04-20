package com.metmit.simulation.handler.xpath.parser.expression.operator;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.XpathUtil;
import com.metmit.simulation.handler.xpath.parser.expression.node.AlgorithmUnit;


/**
 * Created by virjar on 17/6/10.
 *
 * @author virjar
 * @since 0.0.1 大于> 运算
 */
@OpKey(value = ">", priority = 10)
public class GreaterThanUnit extends AlgorithmUnit {
    @Override
    public Object calc(ViewImage element) {
        Object leftValue = left.calc(element);
        Object rightValue = right.calc(element);
        if (leftValue == null || rightValue == null) {
            return XpathUtil.toPlainString(leftValue).compareTo(XpathUtil.toPlainString(rightValue)) > 0;
        }
        // 左右都不为空,开始计算
        // step one think as number
        if (leftValue instanceof Number && rightValue instanceof Number) {
            return ((Number) leftValue).doubleValue() > ((Number) rightValue).doubleValue();
        }

        return XpathUtil.toPlainString(leftValue).compareTo(XpathUtil.toPlainString(rightValue)) > 0;
    }

}
