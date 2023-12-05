package com.nami.y23.d03;

import com.nami.frame.Part;
import com.nami.tools.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class D03P1 implements Part {

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
    private static final char MARKUP = '@';

    @Override
    public long solve(String input) throws IOException {
        List<String> lines = List.of(input.replaceAll("[^0-9.\n]", String.valueOf(MARKUP)).split("\n"));

        char[][] matrix = new char[lines.size()][];
        for (int y = 0; y < matrix.length; y++)
            matrix[y] = lines.get(y).toCharArray();

        int height = matrix.length, width = matrix[0].length;

        List<Integer> numbers = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            char[] row = matrix[y];

            int n = 0;
            boolean valid = false;
            for (int x = 0; x < row.length; x++) {
                char c = row[x];

                if (DIGITS.contains(c)) {
                    n = n * 10 + Character.getNumericValue(c);

                    for (int[] dir : DIRECTIONS) {
                        if (valid)
                            break;

                        int xc = x + dir[0];
                        int yc = y + dir[1];

                        if (xc < 0 || xc > width - 1 || yc < 0 || yc > height - 1)
                            continue;

                        if (matrix[yc][xc] == MARKUP)
                            valid = true;
                    }
                } else if (n > 0) {
                    if (valid)
                        numbers.add(n);

                    n = 0;
                    valid = false;
                }
            }

            if (valid)
                numbers.add(n);
        }

        return Utils.sum(numbers);
    }

}
