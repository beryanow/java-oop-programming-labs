package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Sub extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.pop();
        int h2 = wordStack.pop();
        wordStack.push(h2 - h1);
    }
}