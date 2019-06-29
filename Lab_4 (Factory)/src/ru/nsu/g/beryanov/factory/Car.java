package ru.nsu.g.beryanov.factory;

class Car {
    private int number;
    private int engineNumber;
    private int bodyNumber;
    private int accessoryNumber;

    Car(int x, int y1, int y2, int y3) {
        number = x;
        engineNumber = y1;
        bodyNumber = y2;
        accessoryNumber = y3;
    }
    Car() {}
    int getNumber() {
        return number;
    }
    int getEngineNumber() {
        return engineNumber;
    }
    int getBodyNumber() {
        return bodyNumber;
    }
    int getAccessoryNumber() {
        return accessoryNumber;
    }
}
