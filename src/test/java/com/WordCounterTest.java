package com;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @Test
    void countWordsTest() throws IOException, InterruptedException {

        Runtime runtime = Runtime.getRuntime();

        Process process = runtime.exec("java -jar ./mpj-v0_44/lib/starter.jar -cp ./target/classes com.MainBig -np 1 > results_1_big");

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        System.out.println(output);

        int i = process.waitFor();
        assertEquals(0, i);
    }
}
