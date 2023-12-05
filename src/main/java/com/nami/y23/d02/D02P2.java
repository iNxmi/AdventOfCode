package com.nami.y23.d02;

import com.nami.frame.Part;
import com.nami.tools.Utils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class D02P2 implements Part {

    @Override
    public long solve(String input) {
        List<Integer> power = new ArrayList<>();

        JSONObject json = JSONConverter.convert(List.of(input.split("\n")));
        for (String idGame : json.keySet()) {
            JSONObject jsonGame = json.getJSONObject(idGame);

            int red = 1, green = 1, blue = 1;
            for (String idCubes : jsonGame.keySet()) {
                JSONObject jsonCubes = jsonGame.getJSONObject(idCubes);

                int r = jsonCubes.getInt("red");
                red = Math.max(red, r);

                int g = jsonCubes.getInt("green");
                green = Math.max(green, g);

                int b = jsonCubes.getInt("blue");
                blue = Math.max(blue, b);
            }

            power.add(red * green * blue);
        }

        return Utils.sum(power);
    }

}
