package ru.nsu.g.beryanov.stack_calculator;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class RotTest {

    @Test
    public void test_1() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(10);
        Calculator.getWordStack().push(5);
        Calculator.getWordStack().push(0);
        String temp = "rot";

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        Operation op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        int h1 = Calculator.getWordStack().pop();
        int h2 = Calculator.getWordStack().pop();
        int h3 = Calculator.getWordStack().pop();

        assertEquals(h1, 10);
        assertEquals(h2, 0);
        assertEquals(h3, 5);
    }

    @Test
    public void test_2() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(10);
        Calculator.getWordStack().push(5);
        Calculator.getWordStack().push(0);
        Calculator.getWordStack().push(2);

        String temp = "rot";

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        Operation op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        int h1 = Calculator.getWordStack().pop();
        int h2 = Calculator.getWordStack().pop();
        int h3 = Calculator.getWordStack().pop();

        assertEquals(h1, 5);
        assertEquals(h2, 2);
        assertEquals(h3, 0);
    }
}