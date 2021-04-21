package com.metmit.simulation.handler;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.metmit.simulation.handler.PageContainer;
import com.metmit.simulation.Click;
import com.metmit.simulation.Swipe;
import com.metmit.simulation.handler.traversor.Collector;
import com.metmit.simulation.handler.traversor.Evaluator;
import com.metmit.simulation.handler.traversor.SuperAppiumDumper;
import com.metmit.simulation.handler.xmodel.LazyValueGetter;
import com.metmit.simulation.handler.xmodel.ValueGetters;
import com.metmit.simulation.handler.xpath.XpathParser;
import com.metmit.simulation.handler.xpath.model.XNode;
import com.metmit.simulation.handler.xpath.model.XNodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import de.robv.android.xposed.XposedHelpers;

public class ViewImage {

    /**
     * 当前视图
     */
    private View originView;

    private Map<String, LazyValueGetter> attributes;

    /**
     * 当前视图的父视图
     */
    private ViewImage parent = null;

    /**
     * 当前视图在父视图中的索引位置
     */
    private int indexOfParent = -1;

    private LazyValueGetter type;
    private LazyValueGetter text;

    private ViewImages allElementsCache = null;

    public ViewImage(Activity activity) {
        this(activity.getWindow().getDecorView());
    }

    public ViewImage(View view) {
        if (view == null) {
            throw new NullPointerException("ViewImage->Constructor originView is Null");
        }
        this.originView = view;
        attributes = ValueGetters.valueGetters(this);
        type = attributes.get(SuperAppium.baseClassName);
        text = attributes.get(SuperAppium.text);
    }

    public String getType() {
        return (String)type.get();
    }

    public String getText() {
        return (String)text.get();
    }

    public Collection<String> attributeKeys() {
        return attributes.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T> T attribute(String key) {
        LazyValueGetter valueGetter = attributes.get(key);
        if (valueGetter == null) {
            return null;
        }
        return (T) valueGetter.get();
    }

    /**
     * 获取根视图
     */
    public View getOriginView() {
        return this.originView;
    }

    /**
     * 根视图的 x,y 坐标
     */
    private int[] location = null;

    /**
     * 获取根视图在整个屏幕上的坐标位置
     */
    public int[] locationOnScreen() {
        if (location != null) {
            return location;
        }
        location = new int[2];
        originView.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取根视图的横坐标位置
     */
    public int x() {
        return locationOnScreen()[0];
    }

    /**
     * 获取根视图的纵坐标位置
     */
    public int y() {
        return locationOnScreen()[1];
    }

    /**
     * 获取当前节点的宽度
     */
    public int getWidth() {
        return getOriginView().getWidth();
    }

    /**
     * 获取当前节点的高度
     */
    public int getHeight() {
        return getOriginView().getHeight();
    }

    /**
     * 子元素的个数
     */
    private Integer theChildCount = null;

    /**
     * 返回直接子元素的个数，不包含子元素内部包含的元素个数
     */
    public int childCount() {
        if (theChildCount != null) {
            return theChildCount;
        }
        if (!(originView instanceof ViewGroup)) {
            return 0;
        }
        ViewGroup viewGroup = (ViewGroup) originView;
        // getChildCount（）方法返回的是直接子元素的个数，不包含子元素内部包含的元素个数
        theChildCount = viewGroup.getChildCount();
        return theChildCount;
    }

    /**
     * 当前视图下的所有子视图实例
     */
    private ViewImage[] children;

    /**
     * 获取当前视图下，指定位置的子视图实例
     */
    public ViewImage childAt(int index) {
        if (childCount() < 0) {
            throw new IllegalStateException("can not parse child node for none ViewGroup object!!");
        }
        if (children == null) {
            children = new ViewImage[childCount()];
        }
        ViewImage viewImage = children[index];
        if (viewImage != null) {
            return viewImage;
        }

        ViewGroup viewGroup = (ViewGroup) originView;
        // 返回根视图下、指定位置的子元素视图或者null：
        viewImage = new ViewImage(viewGroup.getChildAt(index));
        viewImage.parent = this;
        viewImage.indexOfParent = index;
        children[index] = viewImage;
        return viewImage;
    }

    /**
     * 当前视图在父视图的索引位置
     */
    public Integer index() {
        return indexOfParent;
    }

    /**
     * 获取当前视图的父、父、父视图 列表
     */
    public List<ViewImage> parents() {
        List<ViewImage> ret = new ArrayList<>();
        ViewImage parent = this.parent;
        while (parent != null) {
            ret.add(parent);
            parent = parent.parent;
        }
        return ret;
    }

    /**
     * 获取当前视图下的所有直接子视图
     */
    public List<ViewImage> children() {
        if (childCount() <= 0) {
            return new ArrayList<>();
        }
        List<ViewImage> ret = new ArrayList<>(childCount());
        for (int i = 0; i < childCount(); i++) {
            ret.add(childAt(i));
        }
        return ret;
    }

    /**
     * 获取全部的子节点，包括父类，子类
     * TODO 还没有仔细跟踪
     */
    public ViewImages getAllElements() {
        if (allElementsCache == null) {
            allElementsCache = Collector.collect(new Evaluator.AllElements(), this);
        }
        return allElementsCache;
    }

    /**
     * 获取父节点
     */
    public ViewImage parentNode() {
        return parent;
    }

    /**
     * 递归获取指定层次的父节点
     */
    public ViewImage parentNode(int n) {
        if (n == 1) {
            return parentNode();
        }
        return parentNode().parentNode(n - 1);
    }

    /**
     * 获取后一个的兄弟节点 对象
     */
    public ViewImage nextSibling() {
        if (parent == null) {
            return null;
        }

        int nextSiblingIndex = indexOfParent + 1;
        if (parent.childCount() > nextSiblingIndex) {
            return parent.childAt(nextSiblingIndex);
        }
        return null;
    }

    /**
     * 获取前一个的兄弟节点 对象
     */
    public ViewImage previousSibling() {
        if (parent == null) { // root
            return null;
        }
        int nextSiblingIndex = indexOfParent - 1;
        if (nextSiblingIndex < 0) {
            return null;
        }
        return parent.childAt(nextSiblingIndex);
    }

    /**
     * 获取全部的相邻的View(兄弟节点)
     */
    public ViewImages siblings() {
        if (parent == null) {
            return new ViewImages();
        }
        int parentChildren = parent.childCount();
        ViewImages viewImages = new ViewImages(parentChildren - 1);
        for (int i = 0; i < parentChildren; i++) {
            ViewImage viewImage = parent.childAt(i);
            if (viewImage == this) {
                continue;
            }
            viewImages.add(viewImage);
        }
        return viewImages;
    }

    /**
     * @return 打印当前view的全部属性
     */
    public String attributes() {
        JSONObject jsonObject = new JSONObject();
        for (String key : attributeKeys()) {
            try {
                jsonObject.put(key, (Object) attribute(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    /**
     * 递归获取父节点，直到获取到 根节点
     */
    public ViewImage rootViewImage() {
        ViewImage parentViewImage = parentNode();
        if (parentViewImage == null) {
            return this;
        }
        return parentViewImage.rootViewImage();
    }

    /**
     * 查找第一个匹配项
     */
    public ViewImage xPath(String xpath) {

        ViewImages viewImages = xPaths(xpath);
        if (viewImages.size() >= 1) {
            return viewImages.get(0);
        }

        List<PopupWindow> pupWindows = PageContainer.getTopPupWindows();
        for (PopupWindow pupWindow : pupWindows) {
            View mDecorView = (View) XposedHelpers.getObjectField(pupWindow, "mDecorView");
            ViewImages DialogWindowXpath = new ViewImage(mDecorView).xPaths(xpath);
            if (DialogWindowXpath.size() >= 1) {
                return DialogWindowXpath.get(0);
            }
        }

        List<Window> dialogWindows = PageContainer.getTopDialogWindows();
        for (Window dialogWindow : dialogWindows) {
            ViewImages DialogWindowXpath = new ViewImage(dialogWindow.getDecorView()).xPaths(xpath);
            if (DialogWindowXpath.size() >= 1) {
                return DialogWindowXpath.get(0);
            }
        }

        return null;
    }

    /**
     * 查找全部匹配项
     */
    public ViewImages xPaths(String xpath) {
        return XpathParser.compileNoError(xpath).evaluateToElement(new XNodes(XNode.e(this)));
    }

    /**
     * 获取节点的内容
     */
    public String xpath2String(String xpath) {
        return XpathParser.compileNoError(xpath).evaluateToSingleString(new XNodes(XNode.e(this)));
    }

    public boolean click() {
        return Click.click(this);
    }

    public boolean click(String xpath) {
        ViewImage viewImage = xPath(xpath);
        if (viewImage == null) return false;
        return viewImage.click();
    }

    public boolean setText(CharSequence str) {
        if (originView instanceof TextView) {
            TextView originView = (TextView) this.originView;
            originView.setText(str);
            return true;
            // return originView.getText().equals(str);
        }
        return false;
    }

    /**
     * 向上滑动
     * @param height 滑动高度，如果为负数，则向下滑动
     */
    public void swipeUp(int height, long duration) {
        swipe(-height, 0, duration);
    }

    /**
     * 向下滑动
     * @param height 滑动高度，如果为负数，则向上滑动
     */
    public void swipeDown(int height, long duration) {
        swipe(height, 0, duration);
    }

    /**
     * 向左滑动
     *
     * @param width 滑动宽度，如果为负数，则向右滑动
     */
    public void swipeLeft(int width, long duration) {
        swipe(0, -width, duration);
    }

    /**
     * 向右滑动
     *
     * @param width 滑动宽度，如果为负数，则向左滑动
     */
    public void swipeRight(int width, long duration) {
        swipe(0, width, duration);
    }

    /**
     * 滑动指定的高度、宽度
     * @param height 滑动高度，正数向下滑，负数向上滑
     * @param width 滑动宽度，正数向右滑，负数向左滑
     */
    public void swipe(int height, int width, long duration) {
        int[] locations = new int[2];
        originView.getLocationOnScreen(locations);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        int fromX, toX, fromY, toY;

        if (width == 0) { // 在偏中间的位置随机取点
            fromX = (int) (locations[0] + viewWidth * (ThreadLocalRandom.current().nextDouble(0.4) - 0.2));
        } else if (width > 0) { // 在偏侧边的位置随机取点
            fromX = (int) (locations[0] + viewWidth * ThreadLocalRandom.current().nextDouble(0.1));
        } else { // 在偏另一侧的位置随机取点
            fromX = (int) (locations[0] + viewWidth * (ThreadLocalRandom.current().nextDouble(0.1) + 0.9));
        }
        if (fromX < 2) {
            fromX = 2;
        }
        toX = fromX + width + (int) (viewWidth * (ThreadLocalRandom.current().nextDouble(0.005)));
        if (toX < 2) {
            toX = 2;
        }

        if (height == 0) {  // 在偏中间的位置随机取点
            fromY = (int) (locations[1] + viewHeight * (ThreadLocalRandom.current().nextDouble(0.05) - 0.025 + 0.5));
        } else if (height > 0) {  // 在偏侧边的位置随机取点
            fromY = (int) (locations[1] + viewHeight * ThreadLocalRandom.current().nextDouble(0.1));
        } else {  // 在偏另一侧的位置随机取点
            fromY = (int) (locations[1] + viewHeight * (ThreadLocalRandom.current().nextDouble(0.1) + 0.9));
        }
        if (fromY < 2) {
            fromY = 2;
        }
        toY = fromY + height + (int) (viewHeight * (ThreadLocalRandom.current().nextDouble(0.008)));
        if (toY < 2) {
            toY = 2;
        }
        Swipe.simulateScroll(this, fromX, fromY, toX, toY, duration);
    }

    /**
     * 判断当前View是否是WebView
     */
    public WebView findWebViewIfExist() {
        ViewImages webViews = Collector.collect(new Evaluator() {

            @Override
            public boolean matches(ViewImage root, ViewImage element) {
                return element.getOriginView() instanceof WebView;
            }

            @Override
            public boolean onlyOne() {
                return true;
            }
        }, this);
        if (webViews.size() == 0) {
            return null;
        }
        return (WebView) webViews.get(0).getOriginView();
    }

    /**
     * 打印当前view包括子view的全部属性
     */
    @Override
    public String toString() {
        return SuperAppiumDumper.dumpToJson(this);
    }
}
