package com.nami.y23.d02;

import org.json.JSONObject;

import java.util.List;

public class JSONConverter {

    public static JSONObject convert(List<String> lines) {
        JSONObject json = new JSONObject();

        for (String line : lines) {
            JSONObject jsonGame = new JSONObject();
            int id = Integer.parseInt(line.split(":")[0].trim().split(" ")[1].trim());

            List<String> games = List.of(line.split(":")[1].trim().split(";"));
            for (int g = 0; g < games.size(); g++) {
                String game = games.get(g);

                int red = 0, green = 0, blue = 0;
                List<String> cubes = List.of(game.trim().split(","));

                for (String cube : cubes) {
                    int n = Integer.parseInt(cube.trim().split(" ")[0].trim());

                    if (cube.contains("red"))
                        red = n;
                    if (cube.contains("green"))
                        green = n;
                    if (cube.contains("blue"))
                        blue = n;
                }

                JSONObject jsonCubes = new JSONObject();
                jsonCubes.put("red", red);
                jsonCubes.put("green", green);
                jsonCubes.put("blue", blue);

                jsonGame.put(String.valueOf(g), jsonCubes.toMap());
            }

            json.put(String.valueOf(id), jsonGame);
        }

        return json;
    }

}
