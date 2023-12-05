package com.nami.tools;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static int sum(Integer... n) {
        return sum(Arrays.asList(n));
    }

    public static int sum(List<Integer> n) {
        return n.stream().mapToInt(a -> a).sum();
    }

}
