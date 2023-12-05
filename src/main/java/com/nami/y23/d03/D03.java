package com.nami.y23.d03;

import com.nami.frame.Day;
import com.nami.frame.Part;

public class D03 extends Day {

    @Override
    public int day() {
        return 3;
    }

    @Override
    public Part partOne() {
        return new D03P1();
    }

    @Override
    public Part partTwo() {
        return new D03P2();
    }
}
