package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Dup extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.lastElement();
        wordStack.push(h1);
    }
}
