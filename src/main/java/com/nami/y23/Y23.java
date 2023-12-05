package com.nami.y23;

import com.nami.frame.Year;
import com.nami.y23.d01.D01;
import com.nami.y23.d02.D02;
import com.nami.y23.d03.D03;
import com.nami.y23.d04.D04;

public class Y23 extends Year {

    public Y23() {
        addDays(
                new D01(),
                new D02(),
                new D03(),
                new D04()
        );
    }

    @Override
    public int year() {
        return 23;
    }

}
