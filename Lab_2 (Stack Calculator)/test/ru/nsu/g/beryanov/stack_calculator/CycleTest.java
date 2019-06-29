package ru.nsu.g.beryanov.stack_calculator;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class CycleTest {

    @Test
    public void deletingTop() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Calculator.getWordStack().push(10);
        Calculator.getWordStack().push(5);

        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        String temp = "dup";
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        Operation op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        String cycle = "1 - dup";
        CycleImplementation.implementOperations(cycle);

        int h1 = Calculator.getWordStack().pop();
        h1 = Calculator.getWordStack().pop();

        assertEquals(h1, 10);
    }

    @Test
    public void factorialFive() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(5);
        Calculator.getWordStack().push(1);

        String temp = "swap";
        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        Operation op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        temp = "dup";

        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        String cycle = " dup rot * swap 1 - dup";
        CycleImplementation.implementOperations(cycle);

        temp = "drop";
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        int h1 = Calculator.getWordStack().pop();
        assertEquals(h1, 120);
    }

    @Test
    public void factorialSix() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Calculator.getWordStack().push(6);
        Calculator.getWordStack().push(1);

        String temp = "swap";
        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        Operation op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        temp = "dup";
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        String cycle = " dup rot * swap 1 - dup";
        CycleImplementation.implementOperations(cycle);

        temp = "drop";
        temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
        op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
        op.performOperation(Calculator.getWordStack());

        int h1 = Calculator.getWordStack().pop();
        assertEquals(h1, 720);
    }
}

