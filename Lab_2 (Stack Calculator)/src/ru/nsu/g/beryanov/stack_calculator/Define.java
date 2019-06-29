package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Define extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        String nextOperation;
        String functionName = Calculator.getWordSequence().next();
        String combinedOperations = "";
        while (!((nextOperation = Calculator.getWordSequence().next()).equals(";"))) {
            if (!(combinedOperations.equals(""))) {
                combinedOperations += " ";
            }
            combinedOperations += nextOperation;
        }
        Calculator.getFunctions().put(functionName, combinedOperations);
    }
}