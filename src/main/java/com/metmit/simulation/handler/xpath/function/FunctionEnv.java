package com.metmit.simulation.handler.xpath.function;

import android.util.Log;

import com.metmit.simulation.handler.SuperAppium;
import com.metmit.simulation.handler.xpath.function.axis.AncestorFunction;
import com.metmit.simulation.handler.xpath.function.axis.AncestorOrSelfFunction;
import com.metmit.simulation.handler.xpath.function.axis.AxisFunction;
import com.metmit.simulation.handler.xpath.function.axis.ChildFunction;
import com.metmit.simulation.handler.xpath.function.axis.DescendantFunction;
import com.metmit.simulation.handler.xpath.function.axis.DescendantOrSelfFunction;
import com.metmit.simulation.handler.xpath.function.axis.FollowingSiblingFunction;
import com.metmit.simulation.handler.xpath.function.axis.FollowingSiblingOneFunction;
import com.metmit.simulation.handler.xpath.function.axis.ParentFunction;
import com.metmit.simulation.handler.xpath.function.axis.PrecedingSiblingFunction;
import com.metmit.simulation.handler.xpath.function.axis.SiblingFunction;
import com.metmit.simulation.handler.xpath.function.filter.AbsFunction;
import com.metmit.simulation.handler.xpath.function.filter.BooleanFunction;
import com.metmit.simulation.handler.xpath.function.filter.ConcatFunction;
import com.metmit.simulation.handler.xpath.function.filter.ContainsFunction;
import com.metmit.simulation.handler.xpath.function.filter.FalseFunction;
import com.metmit.simulation.handler.xpath.function.filter.FilterFunction;
import com.metmit.simulation.handler.xpath.function.filter.FirstFunction;
import com.metmit.simulation.handler.xpath.function.filter.LastFunction;
import com.metmit.simulation.handler.xpath.function.filter.LowerCaseFunction;
import com.metmit.simulation.handler.xpath.function.filter.MatchesFunction;
import com.metmit.simulation.handler.xpath.function.filter.NameFunction;
import com.metmit.simulation.handler.xpath.function.filter.NotFunction;
import com.metmit.simulation.handler.xpath.function.filter.NullToDefaultFunction;
import com.metmit.simulation.handler.xpath.function.filter.PositionFunction;
import com.metmit.simulation.handler.xpath.function.filter.PredicateFunction;
import com.metmit.simulation.handler.xpath.function.filter.RootFunction;
import com.metmit.simulation.handler.xpath.function.filter.SipSoupPredicteJudgeFunction;
import com.metmit.simulation.handler.xpath.function.filter.StringFunction;
import com.metmit.simulation.handler.xpath.function.filter.StringLengthFunction;
import com.metmit.simulation.handler.xpath.function.filter.SubstringFunction;
import com.metmit.simulation.handler.xpath.function.filter.ToDoubleFunction;
import com.metmit.simulation.handler.xpath.function.filter.ToIntFunction;
import com.metmit.simulation.handler.xpath.function.filter.TrueFunction;
import com.metmit.simulation.handler.xpath.function.filter.TryExeptionFunction;
import com.metmit.simulation.handler.xpath.function.filter.UpperCaseFunction;
import com.metmit.simulation.handler.xpath.function.select.AttrFunction;
import com.metmit.simulation.handler.xpath.function.select.SelectFunction;
import com.metmit.simulation.handler.xpath.function.select.SelfFunction;
import com.metmit.simulation.handler.xpath.function.select.TagSelectFunction;
import com.metmit.simulation.handler.xpath.function.select.TextFunction;

import java.util.HashMap;
import java.util.Map;

public class FunctionEnv {
    private static Map<String, SelectFunction> selectFunctions = new HashMap<>();
    private static Map<String, FilterFunction> filterFunctions = new HashMap<>();
    private static Map<String, AxisFunction> axisFunctions = new HashMap<>();

    static {
        registerAllSelectFunctions();
        registerAllFilterFunctions();
        registerAllAxisFunctions();

    }

    public static SelectFunction getSelectFunction(String functionName) {
        return selectFunctions.get(functionName);
    }

    public static FilterFunction getFilterFunction(String functionName) {
        return filterFunctions.get(functionName);
    }

    public static AxisFunction getAxisFunction(String functionName) {
        return axisFunctions.get(functionName);
    }

    public synchronized static void registerSelectFunction(SelectFunction selectFunction) {
        if (selectFunctions.containsKey(selectFunction.getName())) {
            Log.w(SuperAppium.TAG, "register a duplicate  select function " + selectFunction.getName());
        }
        selectFunctions.put(selectFunction.getName(), selectFunction);
    }

    public synchronized static void registerFilterFunction(FilterFunction filterFunction) {
        if (filterFunctions.containsKey(filterFunction.getName())) {
            Log.w(SuperAppium.TAG, "register a duplicate  filter function " + filterFunction.getName());
        }
        filterFunctions.put(filterFunction.getName(), filterFunction);
    }

    public synchronized static void registerAxisFunciton(AxisFunction axisFunction) {
        if (axisFunctions.containsKey(axisFunction.getName())) {
            Log.w(SuperAppium.TAG, "register a duplicate  axis function " + axisFunction.getName());
        }
        axisFunctions.put(axisFunction.getName(), axisFunction);
    }

    private static void registerAllSelectFunctions() {
        registerSelectFunction(new AttrFunction());
        registerSelectFunction(new SelfFunction());
        registerSelectFunction(new TagSelectFunction());
        registerSelectFunction(new TextFunction());
    }

    private static void registerAllFilterFunctions() {
        registerFilterFunction(new AbsFunction());
        registerFilterFunction(new BooleanFunction());
        registerFilterFunction(new ConcatFunction());
        registerFilterFunction(new ContainsFunction());
        registerFilterFunction(new FalseFunction());
        registerFilterFunction(new FirstFunction());
        registerFilterFunction(new LastFunction());
        registerFilterFunction(new LowerCaseFunction());
        registerFilterFunction(new MatchesFunction());
        registerFilterFunction(new NameFunction());
        registerFilterFunction(new NotFunction());
        registerFilterFunction(new NullToDefaultFunction());
        registerFilterFunction(new PositionFunction());
        registerFilterFunction(new PredicateFunction());
        registerFilterFunction(new RootFunction());
        registerFilterFunction(new StringFunction());
        registerFilterFunction(new StringLengthFunction());
        registerFilterFunction(new SubstringFunction());
        registerFilterFunction(new com.metmit.simulation.handler.xpath.function.filter.TextFunction());
        registerFilterFunction(new ToDoubleFunction());
        registerFilterFunction(new ToIntFunction());
        registerFilterFunction(new TrueFunction());
        registerFilterFunction(new TryExeptionFunction());
        registerFilterFunction(new UpperCaseFunction());
        registerFilterFunction(new SipSoupPredicteJudgeFunction());
    }

    private static void registerAllAxisFunctions() {
        registerAxisFunciton(new AncestorFunction());
        registerAxisFunciton(new AncestorOrSelfFunction());
        registerAxisFunciton(new ChildFunction());
        registerAxisFunciton(new DescendantFunction());
        registerAxisFunciton(new DescendantOrSelfFunction());
        registerAxisFunciton(new FollowingSiblingFunction());
        registerAxisFunciton(new FollowingSiblingOneFunction());
        registerAxisFunciton(new ParentFunction());
        registerAxisFunciton(new PrecedingSiblingFunction());
        registerAxisFunciton(new com.metmit.simulation.handler.xpath.function.axis.SelfFunction());
        registerAxisFunciton(new SiblingFunction());
    }


}
