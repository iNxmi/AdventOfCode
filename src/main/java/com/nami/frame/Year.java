package com.nami.frame;

import com.nami.tools.Input;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.TreeMap;

public abstract class Year {

    private TreeMap<Integer, Day> days = new TreeMap<>();

    public abstract int year();

    public void addDays(Day... days) {
        for (Day day : days) {
            day.setYear(this);
            this.days.put(day.day(), day);
        }
    }

    public String loadInput(int day) throws IOException, URISyntaxException {
        return Input.loadAsString(year(), day);
    }

    public TreeMap<Integer, Long> solve(int day) throws Exception {
        return days.get(day ).solve();
    }

}
