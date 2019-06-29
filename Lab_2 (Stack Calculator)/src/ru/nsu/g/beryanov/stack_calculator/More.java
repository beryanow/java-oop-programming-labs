package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class More extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.pop();
        int h2 = wordStack.pop();
        if (h2 > h1) {
            wordStack.push(1);
        } else {
            wordStack.push(0);
        }
    }
}
