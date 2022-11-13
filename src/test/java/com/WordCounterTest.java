package com;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void countWordsParallelTest(int worldSize) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        String[] environmentVars = {"MPJ_HOME=./mpj-v0_44"};
        String launchWordFrequencyCount = String.format("java -jar ./mpj-v0_44/lib/starter.jar -cp ./target/classes com.Parallel -np %1$s", worldSize);

        Process process = runtime.exec(launchWordFrequencyCount, environmentVars);

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        System.out.println(output);

        int exitCode = process.waitFor();

        assertEquals(0, exitCode);

        byte[] expectedResultBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("reference_result.txt").getPath()));
        Path resultFile = Paths.get(String.format("./results/parallelfinal%1$s.txt", worldSize));
        byte[] actualResultBytes = Files.readAllBytes(resultFile);

        assertArrayEquals(expectedResultBytes, actualResultBytes);
    }

    @Test
    void countWordsSequenceTest() throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        String launchWordFrequencyCount = "java -cp ./target/classes:./lib/log4j.jar com.Sequence ./text/input_text.txt";

        Process process = runtime.exec(launchWordFrequencyCount);

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        System.out.println(output);

        int exitCode = process.waitFor();

        assertEquals(0, exitCode);

        byte[] expectedResultBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("reference_result.txt").getPath()));
        Path resultFile = Paths.get("./results/finalsequencial.txt");
        byte[] actualResultBytes = Files.readAllBytes(resultFile);

        assertArrayEquals(expectedResultBytes, actualResultBytes);
    }
}
