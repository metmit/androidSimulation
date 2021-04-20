package com.metmit.simulation.handler.traversor;

import com.metmit.simulation.handler.ViewImage;

public class NodeTraversor {
    private final NodeVisitor visitor;

    /**
     * Create a new traversor.
     * 创建一个新的遍历器
     * @param visitor a class implementing the {@link NodeVisitor} interface, to be called when visiting each node.
     */
    public NodeTraversor(NodeVisitor visitor) {
        this.visitor = visitor;
    }

    /**
     * Start a depth-first traverse of the root and all of its descendants.
     * 开始对根及其所有后代进行深度优先遍历
     * @param root the root node point to traverse.
     */
    public void traverse(ViewImage root) {
        ViewImage node = root;
        int depth = 0;

        while (node != null) {
            if (visitor.head(node, depth)) {
                return;
            }
            if (node.childCount() > 0) {
                node = node.childAt(0);
                depth++;
            } else {
                while (node.nextSibling() == null && depth > 0) {
                    if (visitor.tail(node, depth)) {
                        return;
                    }
                    node = node.parentNode();
                    depth--;
                }
                if (visitor.tail(node, depth)) {
                    return;
                }
                if (node == root)
                    break;
                node = node.nextSibling();
            }
        }
    }
}
