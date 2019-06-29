package ru.nsu.g.beryanov.phrases_count;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class MainFirstPart {
    public static void main(String[] args) throws java.io.IOException {
        InputConditions NewInputConditions = new InputConditions();
        NewInputConditions.resolve_conditions(args);

        Scanner fileScanner = null;
        try {
            if (args.length == 0)
                fileScanner = new Scanner(System.in);
            else if (NewInputConditions.get_s().equals("-"))
                fileScanner = new Scanner(System.in);
            else
                fileScanner = new Scanner(new FileReader(NewInputConditions.get_s()));
        } catch (FileNotFoundException err) {
            System.err.println(err);
            System.exit(1);
        }

        PhrasesResolver NewPhrasesResolver = new PhrasesResolver();
        NewPhrasesResolver.resolve(NewInputConditions, fileScanner);

        List<Map.Entry<String, Integer>> list = NewPhrasesResolver.get_final_array().entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> stringIntegerEntry : list) {
            if (stringIntegerEntry.getValue() >= NewInputConditions.get_m()) {
                System.out.println(stringIntegerEntry.getKey() + " (" + stringIntegerEntry.getValue() + ")");
            }
        }

        fileScanner.close();
    }
}