package com.metmit.simulation.handler.xpath.parser.expression.operator;

import com.metmit.simulation.handler.xpath.parser.expression.node.BooleanRevertUnit;


/**
 * Created by virjar on 17/6/10.
 */
@OpKey(value = "!~", priority = 10)
public class NotMatchUnit extends BooleanRevertUnit {
    @Override
    protected String targetName() {
        return "~=";
    }
}
