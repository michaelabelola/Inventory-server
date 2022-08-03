package com.buyern.inventory.Helpers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h3>convert a list from one type to another</h3>
 * @param <F> The element type of the old List Object
 * @param <T> The element type of the new List you want to convert to
 */
public class ListMapper<F, T> {
    /**
     * <h3>the main list converter method</h3>
     * @param fromList the list you are converting from
     * @param function the function or method to converter. takes in <F> and produces <T>
     * @return A new List
     */
    public List<T> map(List<F> fromList, Function<F, T> function) {
        if (fromList == null || fromList.isEmpty()) return null;
        return fromList.stream().map(function).collect(Collectors.toList());
    }
}
