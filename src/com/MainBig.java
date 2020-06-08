package com;

import mpi.MPI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class MainBig {

    public static String normalize(String str) {
        String str1 = str.trim();
        String[] s1 = str1.split(" ");
        String collect = Arrays.stream(s1).filter(s2 -> !s2.isEmpty()).collect(Collectors.joining(" "));
        String s = collect.replaceAll("[\\p{Punct}]", "");
        s = s.toUpperCase();
        return s;
    }

    public static String normalizeWord(String str) {
        String s = str.replaceAll("[\\p{Punct}]", "");
        s = s.toUpperCase();
        return s;
    }

    public static List<List<String>> loadBalance(int workerNumber, List<String> words) {
        List<List<String>> loads = new ArrayList<>();
        for (int i = 0; i < workerNumber; i++) {
            loads.add(new ArrayList<>());
        }

        int listNumber = 0;

        for (int i = 0; i < words.size(); i++) {
            if (listNumber == workerNumber) {
                listNumber = 0;
            }
            loads.get(listNumber).add(words.get(i));
            ++listNumber;
        }

        return loads;
    }


    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        //Init
        MPI.Init(args);

        //World info
        int rank = MPI.COMM_WORLD.Rank();
        int worldSize = MPI.COMM_WORLD.Size();

        //Set Root process
        int root = 0;


        //Print world size
        if (rank == root) {
            System.out.println("world size: " + worldSize);
            Path inputPath = Paths.get("./text/input_small.txt");
            
            List<String> words = Files.lines(inputPath)
                    .parallel()
                    .map(MainBig::normalize)
                    .flatMap(s -> Arrays.stream(s.split(" ")))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            List<List<String>> workerLoads = loadBalance(worldSize, words);

            List<String> filenames = Stream.iterate(0, integer -> ++integer)
                    .limit(worldSize)
                    .map(String::valueOf)
                    .map(number -> "./text/" + number + ".txt")
                    .collect(toList());

            List<Path> filepaths = filenames.stream().map(name -> Paths.get(name)).collect(toList());

//            for (Path filepath : filepaths) {
//                Files.deleteIfExists(filepath);
//                Files.createFile(filepath);
//            }

            for (int i = 0; i < worldSize; i++) {
                Files.write(filepaths.get(i), workerLoads.get(i));
            }
        }

        //Create send buffer
        int[] sendbuf = new int[worldSize];
        for (int i = 0; i < sendbuf.length; i++) {
            sendbuf[i] = i;
        }

        //Create receive buffer
        int[] recvbuf = new int[1];

        //Scatter
        MPI.COMM_WORLD.Scatter(sendbuf, 0, 1, MPI.INT
                , recvbuf, 0, 1, MPI.INT, root);


        String filename = "./text/" + recvbuf[0] + ".txt";
        String answerFilename = "./text/" + "answer" + recvbuf[0] + ".txt";

        Path path = Paths.get(filename);
        List<String> wordFreq = Files.lines(path)
                .collect(collectingAndThen(
                groupingBy(Function.identity(), Collectors.counting()),
                stringLongMap -> stringLongMap
                        .entrySet()
                        .stream()
                        .map(stringLongEntry -> stringLongEntry.getKey() + "-" + stringLongEntry.getValue())
                        .collect(toList())));

        Path answerPath = Paths.get(answerFilename);
//        Files.deleteIfExists(answerPath);
//        Files.createFile(answerPath);
        Files.write(answerPath, wordFreq);

        //Gather
        MPI.COMM_WORLD.Gather(recvbuf, 0, 1, MPI.INT
                , sendbuf, 0, 1, MPI.INT, root);

        //Root print processed data
        if (rank == root) {
            List<String> anwserFiles = Arrays.stream(sendbuf)
                    .mapToObj(i -> "./text/" + "answer" + i + ".txt")
                    .collect(toList());

            StringBuilder answerString = new StringBuilder("");

            for (String answerFile : anwserFiles) {
                String discreteAnswer = Files.lines(Paths.get(answerFile)).collect(Collectors.joining(" "));
                answerString.append(discreteAnswer).append(" ");
            }

            Map<String, List<String>> wordFreqDuplicates = Arrays.stream(answerString.toString().split(" "))
                    .collect(groupingBy(s -> s.split("-")[0]));

            List<String> wordFreqReal = wordFreqDuplicates.entrySet().stream()
                    .map(stringListEntry -> stringListEntry.getKey() + ": " + (stringListEntry.getValue()
                            .stream()
                            .map(s -> s.split("-")[1])
                            .mapToInt(Integer::parseInt)
                            .sum())
                    )
                    .sorted()
                    .collect(toList());


            Path pathFinal = Paths.get("./text/parallelfinal.txt");
//            Files.deleteIfExists(pathFinal);
//            Files.createFile(pathFinal);
            Files.write(pathFinal,wordFreqReal);
        }

        //Finalize
        MPI.Finalize();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}
