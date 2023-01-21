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
        Path inputTextFile = Paths.get(args[0]);

        String resultsDirectoryName = "results";
        if (Files.exists(inputTextFile)) {
            LOGGER.info("Creating result directory");
            Path resultsDirectory = Paths.get(resultsDirectoryName);
            if (Files.notExists(resultsDirectory)) {
                Files.createDirectory(resultsDirectory);
                LOGGER.info("Created result directory");
            } else {
                LOGGER.info("Results directory already created");
            }
        } else {
            LOGGER.warn("Input text file does not exist");
            throw new IllegalArgumentException("Input text file does not exist");
        }

        Map<String, Long> wordFrequency = Files.lines(inputTextFile)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .map(Parallel::normalizeWord)
                .filter(s -> !s.equals(""))
                .collect(groupingBy(Function.identity(), counting()));

        List<String> wordFrequencyStringRecords = wordFrequency.entrySet().stream()
                .map(stringLongEntry -> stringLongEntry.getKey() + ": " + stringLongEntry.getValue())
                .sorted()
                .collect(toList());

        Path resultFile = Paths.get(resultsDirectoryName, "finalsequencial.txt");
        Files.deleteIfExists(resultFile);
        Files.createFile(resultFile);

        Files.write(resultFile, wordFrequencyStringRecords);

        Instant end = Instant.now();
        LOGGER.info("Milliseconds: " + Duration.between(start, end).toMillis());
    }
}
