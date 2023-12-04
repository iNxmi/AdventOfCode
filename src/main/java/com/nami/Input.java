package com.nami;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Input {

    public static List<String> load(int year, int day) throws IOException, URISyntaxException {
        String path = String.format("/%s/%s.txt", year, day);
        URI uri = Input.class.getResource(path).toURI();
        return Files.readAllLines(Path.of(uri));
    }

}
