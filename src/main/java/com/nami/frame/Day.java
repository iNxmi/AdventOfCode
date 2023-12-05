package com.nami.frame;

import java.util.TreeMap;

public abstract class Day {

    public abstract int day();

    public abstract Part partOne();

    public abstract Part partTwo();

    private Year year;

    public void setYear(Year year) {
        this.year = year;
    }

    public TreeMap<Integer, Long> solve() throws Exception {
        TreeMap<Integer, Long> solutions = new TreeMap<>();

        String input = year.loadInput(day());

        solutions.put(1, partOne().solve(input));
        solutions.put(2, partTwo().solve(input));

        return solutions;
    }

}
