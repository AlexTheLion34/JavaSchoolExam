package com.tsystems.javaschool.tasks.subsequence;

import java.util.*;

public class Subsequence {

    public static void main(String[] args) {
        Subsequence subsequence = new Subsequence();
        boolean b = subsequence.find(Arrays.asList("A", "B", "C", "D"),
                Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
        System.out.println(b);
    }

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) throws IllegalArgumentException{
        if (x == null || y == null) {
            throw new IllegalArgumentException("Empty list was given");
        }
        Deque arrayDeque = new ArrayDeque();
        for (Object o : x) arrayDeque.addLast(o);
        for (Object o : y) {
            if(o.equals(arrayDeque.peekFirst())) {
                arrayDeque.pop();
            }
        }
        return arrayDeque.isEmpty();
    }
}
