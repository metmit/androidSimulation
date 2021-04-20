package com.metmit.simulation.handler.xpath.parser.expression.node;

import com.metmit.simulation.handler.ViewImage;

public abstract class BooleanRevertUnit extends WrapperUnit {
    @Override
    public Object calc(ViewImage element) {
        return !((Boolean) wrap().calc(element));
    }

}