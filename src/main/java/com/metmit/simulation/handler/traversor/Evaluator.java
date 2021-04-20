package com.metmit.simulation.handler.traversor;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.ViewImage;

public abstract class Evaluator {
    protected Evaluator() {
    }

    /**
     * Test if the element meets the evaluator's requirements.
     * 测试元素是否满足评估者的要求。
     * @param root    Root of the matching subtree
     * @param element tested element
     * @return Returns <tt>true</tt> if the requirements are met or
     * <tt>false</tt> otherwise
     */
    public abstract boolean matches(ViewImage root, ViewImage element);

    public boolean onlyOne() {
        return false;
    }

    public static class AllElements extends Evaluator {
        @Override
        public boolean matches(ViewImage root, ViewImage element) {
            return true;
        }
    }

    public static class ByTag extends Evaluator {
        private final String tag;

        public ByTag(String tag) {
            this.tag = tag;
        }

        @Override
        public boolean matches(ViewImage root, ViewImage element) {
            return element.getType().equals(tag);
        }
    }

    public static class ClickAble extends Evaluator {

        @Override
        public boolean matches(ViewImage root, ViewImage element) {
            return element.attribute(SuperAppium.clickable);
        }
    }

    public static class ByHash extends Evaluator {
        private final String hash;

        public ByHash(String hash) {
            this.hash = hash;
        }

        @Override
        public boolean matches(ViewImage root, ViewImage element) {
            return String.valueOf(element.getOriginView().hashCode()).equals(hash);
        }

        @Override
        public boolean onlyOne() {
            return true;
        }
    }

}
