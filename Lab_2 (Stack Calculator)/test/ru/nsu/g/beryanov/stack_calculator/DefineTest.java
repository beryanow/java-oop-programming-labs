package ru.nsu.g.beryanov.stack_calculator;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class DefineTest {

    @Test
    public void factorialFour() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Calculator.getWordStack().push(4);

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        String temp = "factorial";
        Calculator.getFunctions().put("factorial", "1 swap dup [ dup rot * swap 1 - dup ] drop");
        DefinedImplementation.implementOperations(temp);

        int h1 = Calculator.getWordStack().pop();
        assertEquals(h1, 24);
    }

    @Test
    public void minTwo() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(5);
        Calculator.getWordStack().push(7);

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        String temp = "min";
        Calculator.getFunctions().put("min", "dup rot dup rot 1 rot rot  < [ drop swap drop 0 0 ] [ drop 0 ]");
        DefinedImplementation.implementOperations(temp);

        int h1 = Calculator.getWordStack().pop();
        assertEquals(h1, 5);
    }

    @Test
    public void anotherMinTwo() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(7);
        Calculator.getWordStack().push(5);

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        String temp = "min";
        Calculator.getFunctions().put("min", "dup rot dup rot 1 rot rot  < [ drop swap drop 0 0 ] [ drop 0 ]");
        DefinedImplementation.implementOperations(temp);

        int h1 = Calculator.getWordStack().pop();
        assertEquals(h1, 5);
    }
}

