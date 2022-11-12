package com;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Statistics {

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {


        Path pathBig4 = Paths.get("./scripts/results_4_big");
        int average4Big = getAverage(pathBig4);
        System.out.println(average4Big);

        Path pathMid4 = Paths.get("./scripts/results_4_mid");
        int average4Mid = getAverage(pathMid4);
        System.out.println(average4Mid);

        Path pathSmall4 = Paths.get("./scripts/results_4_small");
        int average4Small = getAverage(pathSmall4);
        System.out.println(average4Small);


        Path pathBig1 = Paths.get("./scripts/results_1_big_seq");
        int average1Big = getAverage(pathBig1);

        Path pathMid1 = Paths.get("./scripts/results_1_mid_seq");
        int average1Mid = getAverage(pathMid1);

        Path pathSmall1 = Paths.get("./scripts/results_1_small_seq");
        int average1Small = getAverage(pathSmall1);


        int average1BigParal = getAverage(Paths.get("./scripts/results_1_big"));
        int average2BigParal = getAverage(Paths.get("./scripts/results_2_big"));
        int average3BigParal = getAverage(Paths.get("./scripts/results_3_big"));
        int average4BigParal = getAverage(Paths.get("./scripts/results_4_big"));
        int average5BigParal = getAverage(Paths.get("./scripts/results_5_big"));


        System.out.println("         1     4 ");
        System.out.println("big:   " + average1Big + "  " + average4Big);
        System.out.println("mid:   " + average1Mid + "  " + average4Mid);
        System.out.println("small: " + average1Small + "  " + average4Small);
        System.out.println("\n");
        System.out.println("thead 1: " + average1BigParal);
        System.out.println("thead 2: " + average2BigParal);
        System.out.println("thead 3: " + average3BigParal);
        System.out.println("thead 4: " + average4BigParal);
        System.out.println("thead 5: " + average5BigParal);


    }

    private static int getAverage(Path pathBig4) throws IOException {
        String results4Big = Files.lines(pathBig4)
                .collect(joining("\n"));

        String[] split = results4Big.split("----------");

        List<List<String>> data4Big = Arrays.stream(split)
                .map(arr -> arr.split("\n"))
                .map(Arrays::asList)
                .map(strings -> strings.stream().filter(Statistics::isNumeric).collect(toList()))
                .collect(toList());

        data4Big = data4Big.subList(1, data4Big.size());

        int worldSize = data4Big.get(0).size() - 1;

        int sum = data4Big.stream()
                .map(list -> list.subList(1, list.size()))
                .map(sublist -> (sublist.stream().map(Integer::parseInt).mapToInt(integer -> integer).sum()) / worldSize)
                .mapToInt(integer -> integer)
                .sum();

        int sampleSize = data4Big.size();

        return sum / sampleSize;
    }
}
