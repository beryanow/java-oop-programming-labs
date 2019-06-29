package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Sqrt extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.pop();
        wordStack.push((int) Math.sqrt(h1));
    }
}