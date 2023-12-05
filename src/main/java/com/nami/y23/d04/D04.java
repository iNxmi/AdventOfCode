package com.nami.y23.d04;

import com.nami.frame.Day;
import com.nami.frame.Part;

public class D04 extends Day {

    @Override
    public int day() {
        return 4;
    }

    @Override
    public Part partOne() {
        return new D04P1();
    }

    @Override
    public Part partTwo() {
        return new D04P2();
    }

}
