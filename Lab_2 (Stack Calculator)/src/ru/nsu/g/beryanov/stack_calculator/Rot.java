package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Rot extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.pop();
        int h2 = wordStack.pop();
        int h3 = wordStack.pop();
        wordStack.push(h2);
        wordStack.push(h1);
        wordStack.push(h3);
    }
}
