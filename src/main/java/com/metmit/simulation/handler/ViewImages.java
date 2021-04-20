package com.metmit.simulation.handler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 节点集合
 */
public class ViewImages extends ArrayList<ViewImage> {
    public ViewImages(ViewImage... parentNodes) {
        addAll(Arrays.asList(parentNodes));
    }

    public ViewImages() {
    }

    public ViewImages(List<ViewImage> tempList) {
        super(tempList);
    }

    public ViewImages(int initialCapacity) {
        super(initialCapacity);
    }
}
