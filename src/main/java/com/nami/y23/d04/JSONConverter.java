package com.nami.y23.d04;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONConverter {

    public static final JSONObject convert(String input) {
        JSONObject json = new JSONObject();

        List<String> lines = List.of(input.replaceAll("Card", "").trim().split("\n"));
        lines.forEach(l -> {
            String id = l.split(":")[0].trim();

            List<Integer> win = new ArrayList<>();
            List<String> winList = List.of(l.split(":")[1].trim().split("[|]")[0].trim().split(" "));
            winList.forEach(s -> {
                if (!s.isEmpty())
                    win.add(Integer.parseInt(s));
            });

            List<Integer> have = new ArrayList<>();
            List<String> haveList = List.of(l.split(":")[1].trim().split("[|]")[1].trim().split(" "));
            haveList.forEach(s -> {
                if (!s.isEmpty())
                    have.add(Integer.parseInt(s));
            });

            JSONObject jsonGame = new JSONObject();

            jsonGame.put("win", win);
            jsonGame.put("have", have);

            json.put(id, jsonGame);
        });

        return json;
    }

}
