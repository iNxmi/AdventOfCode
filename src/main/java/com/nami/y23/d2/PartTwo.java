package com.nami.y23.d2;

import com.nami.Input;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PartTwo {

    public PartTwo() throws IOException, URISyntaxException {
        List<Integer> power = new ArrayList<>();

        JSONObject json = JSONConverter.convert(Input.load(23, 2));
        for (String idGame : json.keySet()) {
            JSONObject jsonGame = (JSONObject) json.get(idGame);

            int red = 1, green = 1, blue = 1;
            for (String idCubes : jsonGame.keySet()) {
                JSONObject jsonCubes = (JSONObject) jsonGame.get(idCubes);

                int r = jsonCubes.getInt("red");
                int g = jsonCubes.getInt("green");
                int b = jsonCubes.getInt("blue");

                if (r > 1)
                    red = Math.max(red, r);
                if (g > 1)
                    green = Math.max(green, g);
                if (b > 1)
                    blue = Math.max(blue, b);
            }

            power.add(red * green * blue);
        }

        int sum = power.stream().mapToInt(a -> a).sum();
        System.out.println(sum);
    }

    public static void main(String[] args) {
        try {
            new PartTwo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
