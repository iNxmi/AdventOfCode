package com.nami.y23.d03;

import com.nami.frame.Part;
import com.nami.tools.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
//        String input = "467..114..\n" +
//                "...*......\n" +
//                "..35..633.\n" +
//                "......#...\n" +
//                "617*......\n" +
//                ".....+.58.\n" +
//                "..592.....\n" +
//                "......755.\n" +
//                "...$.*....\n" +
//                ".664.598..";
        input = input.replaceAll("[^0-9.\n]", String.valueOf(MARKUP));

        List<String> lines = List.of(input.split("\n"));

        char[][] matrix = new char[lines.size()][];
        for (int y = 0; y < matrix.length; y++)
            matrix[y] = lines.get(y).toCharArray();

        int height = matrix.length, width = matrix[0].length;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        List<Integer> numbers = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            char[] row = matrix[y];

            int n = 0;
            boolean valid = false;
            for (int x = 0; x < row.length; x++) {
                char c = row[x];

                if (DIGITS.contains(c)) {
                    if (!valid)
                        for (int[] d : DIRECTIONS) {
                            if(valid)
                                break;

                            int xc = x + d[0];
                            int yc = y + d[1];

                            if (xc < 0 || xc > width - 1 || yc < 0 || yc > height - 1)
                                continue;

                            valid = valid || matrix[yc][xc] == MARKUP;
                        }

                    n = n * 10 + Character.getNumericValue(c);
                } else if (n > 0) {
                    if (valid)
                        numbers.add(n);

                    n = 0;
                    valid = false;
                }

                img.setRGB(x, y, new Color(Character.isDigit(c) ? 1f : 0.0f, Character.isLetter(c) ? 1f : 0f, (c == MARKUP || valid) ? 1f : 0f).getRGB());
            }
        }

        ImageIO.write(img, "png", new File("E:/Windows/Desktop/out.png"));

        return Utils.sum(numbers);
    }

}
