package com.metmit.simulation.handler.traversor;

import com.metmit.simulation.handler.ViewImage;
import com.metmit.simulation.handler.ViewImages;

import java.util.List;

public class Collector {

    private Collector() {
    }

    /**
     * Build a list of elements, by visiting root and every descendant of root, and testing it against the evaluator.
     * 通过访问根和根的每个后代，构建一个元素列表，并针对求值器测试它。
     * @param eval Evaluator to test elements against
     * @param root root of tree to descend
     * @return list of matches; empty if none
     */
    public static ViewImages collect(Evaluator eval, ViewImage root) {
        ViewImages elements = new ViewImages();
        new NodeTraversor(new Accumulator(root, elements, eval)).traverse(root);
        return elements;
    }

    private static class Accumulator implements NodeVisitor {
        private final ViewImage root;
        private final List<ViewImage> elements;
        private final Evaluator eval;

        Accumulator(ViewImage root, List<ViewImage> elements, Evaluator eval) {
            this.root = root;
            this.elements = elements;
            this.eval = eval;
        }

        public boolean head(ViewImage node, int depth) {
            if (eval.matches(root, node)) {
                elements.add(node);
                return eval.onlyOne();
            }

            return false;
        }

        public boolean tail(ViewImage node, int depth) {
            // void
            return false;
        }
    }
}

