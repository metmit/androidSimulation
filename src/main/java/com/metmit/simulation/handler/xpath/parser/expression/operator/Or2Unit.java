package com.metmit.simulation.handler.xpath.parser.expression.operator;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.node.WrapperUnit;

/**
 * Created by virjar on 17/6/10.
 */
@OpKey(value = "or", priority = 0)
public class Or2Unit extends WrapperUnit {
    @Override
    protected String targetName() {
        return "||";
    }

    @Override
    public Object calc(ViewImage element) {
        return wrap().calc(element);
    }

}
