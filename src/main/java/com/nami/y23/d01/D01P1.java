package com.nami.y23.d01;

import com.nami.frame.Part;
import com.nami.tools.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D01P1 implements Part {

    private static final List<Character> DIGITS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    @Override
    public long solve(String input) {
        List<Integer> numbers = new ArrayList<>();

        List<String> lines = List.of(input.split("\n"));
        for (String line : lines) {
            char[] chars = line.toCharArray();

            List<Integer> digits = new ArrayList<>();
            for (char c : chars)
                if (DIGITS.contains(c))
                    digits.add(Character.getNumericValue(c));

            int n0 = digits.get(0);
            if (digits.size() == 1) {
                numbers.add(n0 * 11);
            } else {
                numbers.add(n0 * 10 + digits.get(digits.size() - 1));
            }
        }

        return Utils.sum(numbers);
    }

}
