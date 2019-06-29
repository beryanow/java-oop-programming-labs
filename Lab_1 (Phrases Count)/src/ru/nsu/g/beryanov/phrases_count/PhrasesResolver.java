package ru.nsu.g.beryanov.phrases_count;

import java.util.*;

public class PhrasesResolver {
    private Map<String, Integer> final_array;
    private ArrayList<String> final_phrases;

    Map<String, Integer> get_final_array() {
        return final_array;
    }
    ArrayList<String> get_phrases_list() { return final_phrases; }

    void resolve(InputConditions NewInputConditions, Scanner fileScanner) {
        ArrayList<String> phrases = new ArrayList<String>();
        Map<String, Integer> array = new TreeMap<>();

        String[] temp_array = new String[NewInputConditions.get_n()];

        int i = 0;
        int y = 0;
        while (fileScanner.hasNext()) {
            temp_array[i] = fileScanner.next();
            i++;
            if (i == NewInputConditions.get_n()) {
                String s = "";
                int k;
                for (k = 0; k < NewInputConditions.get_n() - 1; k++) {
                    s += temp_array[k] + " ";
                }
                s += temp_array[k];
                phrases.add(y, s);
                y++;
                if (array.containsKey(s)) {
                    array.put(s, array.get(s) + 1);
                    i--;
                    for (k = 0; k < temp_array.length - 1; k++) {
                        temp_array[k] = temp_array[k + 1];
                    }
                } else {
                    array.put(s, 1);
                    i--;
                    for (k = 0; k < temp_array.length - 1; k++) {
                        temp_array[k] = temp_array[k + 1];
                    }
                }
            }
        }
        final_phrases = phrases;
        final_array = array;
    }
}