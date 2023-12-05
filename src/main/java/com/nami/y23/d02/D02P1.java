package com.nami.y23.d02;

import com.nami.frame.Part;
import com.nami.tools.Utils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class D02P1 implements Part {

    private final int RED = 12, GREEN = 13, BLUE = 14;

    @Override
    public long solve(String input) {
        List<Integer> valid = new ArrayList<>();

        JSONObject json = JSONConverter.convert(List.of(input.split("\n")));
        for (String idGame : json.keySet()) {
            JSONObject jsonGame = json.getJSONObject(idGame);

            int red = 0, green = 0, blue = 0;
            for (String idCubes : jsonGame.keySet()) {
                JSONObject jsonCubes = jsonGame.getJSONObject(idCubes);

                red = Math.max(red, jsonCubes.getInt("red"));
                green = Math.max(green, jsonCubes.getInt("green"));
                blue = Math.max(blue, jsonCubes.getInt("blue"));
            }

            if (red <= RED && green <= GREEN && blue <= BLUE)
                valid.add(Integer.parseInt(idGame));
        }

        return Utils.sum(valid);
    }

}
