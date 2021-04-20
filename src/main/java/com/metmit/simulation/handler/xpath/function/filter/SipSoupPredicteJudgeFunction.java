package com.metmit.simulation.handler.xpath.function.filter;

import android.util.Log;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.xpath.parser.expression.SyntaxNode;

import java.util.List;


public class SipSoupPredicteJudgeFunction implements FilterFunction {
    @Override
    public Object call(ViewImage viewImage, List<SyntaxNode> params) {
        if (viewImage == null) {
            return false;
        }
        Object ret = params.get(0).calc(viewImage);
        if (ret == null) {
            return false;
        }

        if (ret instanceof Number) {
            int i = ((Number) ret).intValue();
            return viewImage.index() + 1 == i;
        }

        if (ret instanceof Boolean) {
            return ret;
        }

        if (ret instanceof CharSequence) {
            String s = ret.toString();
            return Boolean.valueOf(s);
        }

        Log.w(SuperAppium.TAG, "can not recognize predicate expression calc result:" + ret);
        return false;
    }

    @Override
    public String getName() {
        return "sipSoupPredictJudge";
    }
}
