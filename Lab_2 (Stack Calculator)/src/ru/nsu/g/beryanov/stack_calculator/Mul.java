package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Mul extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.pop();
        int h2 = wordStack.pop();
        wordStack.push(h1 * h2);
    }
}