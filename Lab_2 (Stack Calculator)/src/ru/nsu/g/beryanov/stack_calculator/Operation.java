package ru.nsu.g.beryanov.stack_calculator;

import java.util.Stack;

public abstract class Operation {
    public abstract void performOperation(Stack<Integer> wordStack);
}