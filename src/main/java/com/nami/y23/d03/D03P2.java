package com.nami.y23.d03;

import com.nami.frame.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class D03P2 implements Part {

    private static final List<Character> DIGITS = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final int[][] DIRECTIONS = new int[][]{
            {0, 1},
            {0, -1},
            {1, 0},
            {1, 1},
            {1, -1},
            {-1, 0},
            {-1, 1},
            {-1, -1}
    };

    private static final char GEAR = '*';

    @Override
    public long solve(String input) throws IOException {
        List<String> lines = List.of(input.split("\n"));

        char[][] matrix = new char[lines.size()][];
        for (int y = 0; y < matrix.length; y++)
            matrix[y] = lines.get(y).toCharArray();

        int height = matrix.length, width = matrix[0].length;

        TreeMap<Integer, List<Integer>> numbers = new TreeMap<>();
        for (int y = 0; y < matrix.length; y++) {
            char[] row = matrix[y];

            int n = 0;
            List<Integer> gearIds = new ArrayList<>();
            for (int x = 0; x < row.length; x++) {
                char c = row[x];

                if (DIGITS.contains(c)) {
                    n = n * 10 + Character.getNumericValue(c);

                    for (int[] dir : DIRECTIONS) {
                        int xc = x + dir[0];
                        int yc = y + dir[1];

                        if (xc < 0 || xc > width - 1 || yc < 0 || yc > height - 1)
                            continue;

                        if (matrix[yc][xc] == GEAR) {
                            int gearId = xc + yc * width;
                            if (!gearIds.contains(gearId))
                                gearIds.add(gearId);
                        }
                    }
                } else if (n > 0) {
                    for (Integer id : gearIds) {
                        if (!numbers.containsKey(id))
                            numbers.put(id, new ArrayList<>());

                        numbers.get(id).add(n);
                    }

                    n = 0;
                    gearIds.clear();
                }
            }

            for (Integer id : gearIds) {
                if (!numbers.containsKey(id))
                    numbers.put(id, new ArrayList<>());

                numbers.get(id).add(n);
            }

        }

        long sum = 0;
        for (List<Integer> list : numbers.values()) {
            if (list.size() != 2)
                continue;

            sum += list.get(0) * list.get(1);
        }

        return sum;
    }

}
