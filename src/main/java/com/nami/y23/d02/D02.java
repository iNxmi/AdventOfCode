package com.nami.y23.d02;

import com.nami.frame.Day;
import com.nami.frame.Part;

public class D02 extends Day {

    @Override
    public int day() {
        return 2;
    }

    @Override
    public Part partOne() {
        return new D02P1();
    }

    @Override
    public Part partTwo() {
        return new D02P2();
    }
}
