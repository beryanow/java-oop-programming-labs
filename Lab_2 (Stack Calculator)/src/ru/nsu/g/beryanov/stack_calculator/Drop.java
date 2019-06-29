package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Drop extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        wordStack.pop();
    }
}
