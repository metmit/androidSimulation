package com.metmit.simulation.handler.traversor;

import com.metmit.simulation.handler.ViewImage;

public interface NodeVisitor {
    /**
     * Callback for when a node is first visited.
     * 第一次访问节点时的回调
     * @param node  the node being visited.
     * @param depth the depth of the node, relative to the root node. E.g., the root node has depth 0, and a child node
     *              of that will have depth 1.
     */
    boolean head(ViewImage node, int depth);

    /**
     * Callback for when a node is last visited, after all of its descendants have been visited.
     * 当节点的所有后代都被访问后，最后一次访问节点时的回调
     * @param node  the node being visited.
     * @param depth the depth of the node, relative to the root node. E.g., the root node has depth 0, and a child node
     *              of that will have depth 1.
     */
    boolean tail(ViewImage node, int depth);
}
