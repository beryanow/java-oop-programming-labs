package ru.nsu.g.beryanov.factory;

class Body {
    private int number;

    Body(int x) {
        number = x;
    }
    Body() {
        number = 0;
    }
    int getNumber() {
        return number;
    }
}
