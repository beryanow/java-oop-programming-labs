package ru.nsu.g.beryanov.factory;

class Helpers {
    static final Object addAccessory = new Object();
    static final Object addEngine = new Object();
    static final Object addBody = new Object();
    static final Object addCar = new Object();
    static final Object singleAccessory = new Object();
    static final Object singleEngine = new Object();
    static final Object singleBody = new Object();
    static final Object singleCar = new Object();
    static final Object finish = new Object();
    static final Object singleCheck = new Object();
    static final Object singleDealer = new Object();
    static final Object singleUniversal = new Object();
    static final Object singleUpdate = new Object();
    private static boolean isNotInterrupted = true;
    static int carSold = 0;

    static boolean getInfo() {
        return isNotInterrupted;
    }
    static void setIsNotInterruptedFalse() throws InterruptedException {
        isNotInterrupted = false;
    }
}
