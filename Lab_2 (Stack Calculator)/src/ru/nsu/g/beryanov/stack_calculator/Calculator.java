package ru.nsu.g.beryanov.stack_calculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Calculator {
    static private Stack<Integer> wordStack = new Stack<>();
    static private Scanner wordSequence = new Scanner(System.in);
    static private Map<String, String> functions = new HashMap<String, String>();

    static Stack<Integer> getWordStack() {
        return wordStack;
    }

    static Scanner getWordSequence() {
        return wordSequence;
    }

    static Map<String, String> getFunctions() {
        return functions;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
        Properties prop = new Properties();

        InputStream inputStream = Calculator.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(inputStream);

        while (Calculator.getWordSequence().hasNext()) {
            int possibleNumber;
            String temp = null;
            try {
                temp = Calculator.getWordSequence().next();
                possibleNumber = Integer.parseInt(temp);
                Calculator.getWordStack().push(possibleNumber);
            } catch (NumberFormatException e) {
                if (temp.equals("#")) {
                    Calculator.getWordSequence().nextLine();
                    continue;
                }

                if (temp.equals("[")) {
                    String newCycle = "";
                    int y = 0;
                    while (!((temp = Calculator.getWordSequence().next()).equals("]") && (y == 0))) {
                        if (temp.equals("]")) {
                            y--;
                        }

                        if (!(newCycle.equals(""))) {
                            newCycle += " ";
                        }

                        if (temp.equals("[")) {
                            y++;
                        }

                        newCycle += temp;
                    }
                    CycleImplementation.implementOperations(newCycle);
                    continue;
                }

                if (Calculator.getFunctions().containsKey(temp)) {
                    String newOperations = Calculator.getFunctions().get(temp);
                    DefinedImplementation.implementOperations(newOperations);
                    continue;
                }

                temp = temp.toLowerCase();
                Operation op = null;

                String tmp = temp;
                temp = prop.getProperty(temp);

                if (temp == null) {
                    temp = tmp;
                    temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
                    op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
                }
                else {
                    temp = temp.substring(0, 1).toUpperCase() + temp.substring(1).toLowerCase();
                    op = (Operation) Class.forName(("ru.nsu.g.beryanov.stack_calculator." + temp)).getDeclaredConstructor().newInstance();
                }

                op.performOperation(getWordStack());
            }
        }
        wordSequence.close();
    }

}
