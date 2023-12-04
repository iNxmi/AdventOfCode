package com.nami.y23.d1;

import com.nami.Input;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class PartTwo {

    private static final List<String> DIGITS = Arrays.asList(
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    );

    private static final HashMap<String, Integer> MAPPING = new HashMap<>();

    public PartTwo() throws IOException, URISyntaxException {
        MAPPING.put(0 + "", 0);
        MAPPING.put(1 + "", 1);
        MAPPING.put(2 + "", 2);
        MAPPING.put(3 + "", 3);
        MAPPING.put(4 + "", 4);
        MAPPING.put(5 + "", 5);
        MAPPING.put(6 + "", 6);
        MAPPING.put(7 + "", 7);
        MAPPING.put(8 + "", 8);
        MAPPING.put(9 + "", 9);

        MAPPING.put("zero", 0);
        MAPPING.put("one", 1);
        MAPPING.put("two", 2);
        MAPPING.put("three", 3);
        MAPPING.put("four", 4);
        MAPPING.put("five", 5);
        MAPPING.put("six", 6);
        MAPPING.put("seven", 7);
        MAPPING.put("eight", 8);
        MAPPING.put("nine", 9);

        List<String> lines = Input.load(23, 1);

        List<Integer> numbers = new ArrayList<>();
        for (String line : lines) {
            TreeMap<Integer, String> map = new TreeMap<>();
            DIGITS.forEach(d -> {
                int index = line.indexOf(d);
                if (index != -1)
                    map.put(index, d);

                int lastIndex = line.lastIndexOf(d);
                if (lastIndex != -1)
                    map.put(lastIndex, d);
            });

            int n0 = MAPPING.get(map.firstEntry().getValue());
            if (map.size() == 1) {
                numbers.add(n0 * 11);
            } else {
                int n1 = MAPPING.get(map.lastEntry().getValue());
                numbers.add(n0 * 10 + n1);
            }
        }

        int sum = numbers.stream().mapToInt(a -> a).sum();
        System.out.println(sum);
    }

    public static void main(String[] args) {
        try {
            new PartTwo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
