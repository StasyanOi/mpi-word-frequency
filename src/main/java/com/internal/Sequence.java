package com.internal;

import org.apache.log4j.Logger;

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

public class Sequence {

    private static final Logger LOGGER = Logger.getLogger(Sequence.class);

    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        Path path = Paths.get(args[0]);

        Path resultsDirectory = Paths.get("results");
        LOGGER.info("Creating result directory");
        if (!Files.notExists(path)) {
            LOGGER.info("Created result directory");
            Files.createDirectory(resultsDirectory);
        }

        Map<String, Long> freq = Files.lines(path)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(Parallel::normalizeWord)
                .filter(s -> !s.equals(""))
                .collect(groupingBy(Function.identity(), counting()));

        List<String> stringsFreq = freq.entrySet().stream()
                .map(stringLongEntry -> stringLongEntry.getKey() + ": " + stringLongEntry.getValue())
                .sorted()
                .collect(toList());

        Path resultFile = Paths.get(resultsDirectory.toString(), "finalsequencial.txt");
        Files.deleteIfExists(resultFile);
        Files.createFile(resultFile);

        Files.write(resultFile, stringsFreq);

        Instant end = Instant.now();
        LOGGER.info("Milliseconds: " + Duration.between(start, end).toMillis());
    }
}
