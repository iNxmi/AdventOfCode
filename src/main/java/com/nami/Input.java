package com.nami;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Input {

    public static List<String> load(int year, int day) throws IOException, URISyntaxException {
        String path = String.format("/%s/%s.txt", year, day);
        URI uri = Objects.requireNonNull(Input.class.getResource(path)).toURI();
        return Files.readAllLines(Path.of(uri));
    }

}
