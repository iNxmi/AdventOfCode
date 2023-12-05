package com.nami.y23.d01;

import com.nami.frame.Day;
import com.nami.frame.Part;

public class D01 extends Day {

    @Override
    public int day() {
        return 1;
    }

    @Override
    public Part partOne() {
        return new D01P1();
    }

    @Override
    public Part partTwo() {
        return new D01P2();
    }

}
