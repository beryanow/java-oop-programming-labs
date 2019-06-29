package ru.nsu.g.beryanov.phrases_count;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import ru.nsu.g.beryanov.phrases_count.PhrasesResolver;
import ru.nsu.g.beryanov.phrases_count.InputConditions;


public class PhrasesResolverTest {
    @Test
    public void testResolveConditions() {
        String[] args1 = {"-n", "2", "-m", "2", "-"};

        InputConditions NewIC = new InputConditions();
        NewIC.resolve_conditions(args1);

        PhrasesResolver NewPhrasesResolver = new PhrasesResolver();
        Scanner fileScanner = new Scanner("new example new example");

        NewPhrasesResolver.resolve(NewIC, fileScanner);

        List<String> keys = new ArrayList<>(NewPhrasesResolver.get_final_array().keySet());

        String s = "";
        for (String key : keys) {
            if (NewPhrasesResolver.get_final_array().get(key) == 2)
                s = key;
        }

        assertEquals(s, "new example");
    }

    @Test
    public void testResolveConditions2() {
        String[] args1 = {"-n", "2", "-m", "3", "-"};

        InputConditions NewIC = new InputConditions();
        NewIC.resolve_conditions(args1);

        PhrasesResolver NewPhrasesResolver = new PhrasesResolver();
        Scanner fileScanner = new Scanner("three example three example three example");

        NewPhrasesResolver.resolve(NewIC, fileScanner);

        List<String> keys = new ArrayList<>(NewPhrasesResolver.get_final_array().keySet());

        String s = "";
        for (String key : keys) {
            if (NewPhrasesResolver.get_final_array().get(key) == 3)
                s = key;
        }

        assertEquals(s, "three example");
    }
}