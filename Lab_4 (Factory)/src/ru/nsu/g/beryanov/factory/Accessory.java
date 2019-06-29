package ru.nsu.g.beryanov.factory;

class Accessory {
    private int number;

    Accessory(int x) {
        number = x;
    }
    Accessory() {
        number = 0;
    }
    int getNumber() {
        return number;
    }
}
