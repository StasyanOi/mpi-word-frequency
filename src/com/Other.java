package com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

public class Other {

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        Path path = Paths.get(args[0]);

        Map<String, Long> freq = Files.lines(path)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(Main::normalizeWord)
                .filter(s -> !s.equals(""))
                .collect(groupingBy(Function.identity(), counting()));


        List<String> stringsFreq = freq.entrySet().stream()
                .map(stringLongEntry -> stringLongEntry.getKey() + ": " + stringLongEntry.getValue())
                .sorted()
                .collect(toList());

        Path path1 = Paths.get("./text/finalsequencial.txt");
        Files.deleteIfExists(path1);
        Files.createFile(path1);
        Files.write(path1,stringsFreq);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}
