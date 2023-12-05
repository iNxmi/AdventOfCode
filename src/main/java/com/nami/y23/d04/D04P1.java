package com.nami.y23.d04;

import com.nami.frame.Part;
import com.nami.tools.Utils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class D04P1 implements Part {
    @Override
    public long solve(String input) throws Exception {
        JSONObject json = JSONConverter.convert(input);

        List<Integer> numbers = new ArrayList<>();
        for (String key : json.keySet()) {
            JSONObject game = json.getJSONObject(key);

            List<Object> win = game.getJSONArray("win").toList();
            List<Object> have = game.getJSONArray("have").toList();

            int count = 0;
            for (Object h : have)
                if (win.contains(h))
                    count++;

            numbers.add(count <= 0 ? 0 : (int) Math.pow(2, count - 1));
        }

        return Utils.sum(numbers);
    }

}
