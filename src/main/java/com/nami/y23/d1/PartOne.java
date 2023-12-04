package com.nami.y23.d1;

import com.nami.Input;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartOne {

    private static final List<Character> DIGITS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    public PartOne() throws IOException, URISyntaxException {
        List<Integer> numbers = new ArrayList<>();

        List<String> lines = Input.load(23,1);
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

        int sum = numbers.stream().mapToInt(a -> a).sum();
        System.out.println(sum);
    }


    @SuppressWarnings("InstantiationOfUtilityClass")
    public static void main(String[] args) {
        try {
            new PartOne();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
