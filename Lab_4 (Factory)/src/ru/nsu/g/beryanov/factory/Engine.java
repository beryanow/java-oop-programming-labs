package ru.nsu.g.beryanov.factory;

class Engine {
    private int number;

    Engine(int x) {
        number = x;
    }
    Engine() {
        number = 0;
    }
    int getNumber() {
        return number;
    }
}
