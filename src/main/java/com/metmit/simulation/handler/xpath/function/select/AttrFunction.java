package com.metmit.simulation.handler.xpath.function.select;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.utils.StringUtils;
import com.metmit.simulation.handler.xpath.model.XNode;

import java.util.List;


public class AttrFunction extends AttrBaseFunction {

    @Override
    public void handle(boolean allAttr, String attrKey, ViewImage element, List<XNode> ret) {
        if (allAttr) {
            ret.add(XNode.t(element.attributes()));
        } else {
            Object value = element.attribute(attrKey);
            if (value == null) {
                return;
            }
            String str = value.toString();

            if (StringUtils.isNotBlank(str)) {
                ret.add(XNode.t(str));
            }
        }
    }

    @Override
    public String getName() {
        return "@";
    }
}
