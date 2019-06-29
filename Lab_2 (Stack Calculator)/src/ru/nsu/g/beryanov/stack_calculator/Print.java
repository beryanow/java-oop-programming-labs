package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public class Print extends Operation {
    public void performOperation(Stack<Integer> wordStack) {
        int h1 = wordStack.lastElement();
        System.out.println(h1);
    }
}