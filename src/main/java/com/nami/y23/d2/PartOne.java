package com.nami.y23.d2;

import com.nami.Input;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PartOne {

    private final int RED = 12, GREEN = 13, BLUE = 14;

    public PartOne() throws IOException, URISyntaxException {
        List<Integer> valid = new ArrayList<>();

        JSONObject json = JSONConverter.convert(Input.load(23,2));
        for (String idGame : json.keySet()) {
            JSONObject jsonGame = (JSONObject) json.get(idGame);

            int red = 0, green = 0, blue = 0;
            for (String idCubes : jsonGame.keySet()) {
                JSONObject jsonCubes = (JSONObject) jsonGame.get(idCubes);

                red = Math.max(red, jsonCubes.getInt("red"));
                green = Math.max(green, jsonCubes.getInt("green"));
                blue = Math.max(blue, jsonCubes.getInt("blue"));
            }

            if (red <= RED && green <= GREEN && blue <= BLUE)
                valid.add(Integer.parseInt(idGame));
        }

        int sum = valid.stream().mapToInt(a -> a).sum();
        System.out.println(sum);
    }


    @SuppressWarnings("InstantiationOfUtilityClass")
    public static void main(String[] args) {
        try {
            new PartOne();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
