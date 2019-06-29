package ru.nsu.g.beryanov.factory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class EngineThread implements Runnable {
    private int time;
    private int count;
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;

    EngineThread(int x, Warehouse<Engine> y1, Warehouse<Body> y2, Warehouse<Accessory> y3) {
        time = x;
        myEngineWarehouse = y1;
        myBodyWarehouse = y2;
        myAccessoryWarehouse = y3;
        count = 0;
    }
    public void run() {
        while (Helpers.getInfo()) {
            Engine myEngine = new Engine(count);
            count++;
            try {
                myEngineWarehouse.addElement(myEngine);
                synchronized (Helpers.singleCheck) {
                    if (((myEngineWarehouse.getAvailable(new Engine()) > 0) && (myBodyWarehouse.getAvailable(new Body()) > 0) && (myAccessoryWarehouse.getAvailable(new Accessory()) > 0))) {
                        Helpers.singleCheck.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class BodyThread implements Runnable {
    private int time;
    private int count;
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;

    BodyThread(int x, Warehouse<Engine> y1, Warehouse<Body> y2, Warehouse<Accessory> y3) {
        time = x;
        myEngineWarehouse = y1;
        myBodyWarehouse = y2;
        myAccessoryWarehouse = y3;
        count = 0;
    }
    public void run() {
        while (Helpers.getInfo()) {
            Body myBody = new Body(count);
            count++;
            try {
                myBodyWarehouse.addElement(myBody);
                synchronized (Helpers.singleCheck) {
                    if (((myEngineWarehouse.getAvailable(new Engine()) > 0) && (myBodyWarehouse.getAvailable(new Body()) > 0) && (myAccessoryWarehouse.getAvailable(new Accessory()) > 0))) {
                        Helpers.singleCheck.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class AccessoryThread implements Runnable {
    private int time;
    private static int count = 0;
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;

    AccessoryThread(int x, Warehouse<Engine> y1, Warehouse<Body> y2, Warehouse<Accessory> y3) {
        time = x;
        myEngineWarehouse = y1;
        myBodyWarehouse = y2;
        myAccessoryWarehouse = y3;
    }
    public void run() {
        while (Helpers.getInfo()) {
            Accessory myAccessory;
            synchronized (Helpers.singleAccessory) {
                myAccessory = new Accessory(count);
                count++;
            }
            try {
                myAccessoryWarehouse.addElement(myAccessory);
                synchronized (Helpers.singleCheck) {
                    if (((myEngineWarehouse.getAvailable(new Engine()) > 0) && (myBodyWarehouse.getAvailable(new Body()) > 0) && (myAccessoryWarehouse.getAvailable(new Accessory()) > 0))) {
                        Helpers.singleCheck.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class WorkerThread extends Observable implements Runnable, Publisher {
    private int time;
    private static int count = 0;
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;
    private Warehouse<Car> myCarWarehouse;
    private List<Observer> channels = new ArrayList<>();

    private void register(Observer outlet) {
        channels.add(outlet);
    }
    void finishThreads(int x) {
        channels.get(3).update(this, x);
    }
    WorkerThread(int x, Warehouse<Car> y, Warehouse<Engine> y1, Warehouse<Body> y2, Warehouse<Accessory> y3) {
        time = x;
        myCarWarehouse = y;
        myEngineWarehouse = y1;
        myBodyWarehouse = y2;
        myAccessoryWarehouse = y3;
    }
    public void run() {
        SleepChannel sleepChannel = new SleepChannel();
        register(sleepChannel);
        WorkChannel workChannel = new WorkChannel();
        register(workChannel);
        WaitChannel waitChannel = new WaitChannel();
        register(waitChannel);
        FinishChannel finishChannel = new FinishChannel();
        register(finishChannel);
        while (Helpers.getInfo()) {
            Car myCar;
            synchronized (Helpers.singleCar) {
                synchronized (Helpers.singleCheck) {
                    if (((myEngineWarehouse.getAvailable(new Engine()) == 0) || (myBodyWarehouse.getAvailable(new Body()) == 0) || (myAccessoryWarehouse.getAvailable(new Accessory()) == 0))) {
                        try {
                            int i = Integer.parseInt(Thread.currentThread().getName().substring(14));
                            channels.get(2).update(this, i);
                            Helpers.singleCheck.wait();
                            if (!Helpers.getInfo()) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int i = Integer.parseInt(Thread.currentThread().getName().substring(14));
                channels.get(1).update(this, i);
                Engine recentEngine;
                Body recentBody;
                Accessory recentAccessory;
                recentBody = myBodyWarehouse.getElement();
                recentEngine = myEngineWarehouse.getElement();
                recentAccessory = myAccessoryWarehouse.getElement();
                myCar = new Car(count, recentEngine.getNumber(), recentBody.getNumber(), recentAccessory.getNumber());
                myAccessoryWarehouse.removeElement(new Accessory());
                myEngineWarehouse.removeElement(new Engine());
                myBodyWarehouse.removeElement(new Body());
                count++;
            }
            synchronized (Helpers.singleDealer) {
                try {
                    myCarWarehouse.addElement(myCar);
                    Helpers.singleDealer.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class DealerThread implements Runnable {
    private int time;
    private boolean salesLogOn;
    private FileWriter write;
    private Warehouse<Car> myCarWarehouse;

    DealerThread(int x, Warehouse<Car> y, boolean z, FileWriter w) {
        time = x;
        myCarWarehouse = y;
        salesLogOn = z;
        write = w;
    }
    public void run() {
        while (Helpers.getInfo()) {
            try {
                Car element;
                synchronized (Helpers.singleDealer) {
                    if (myCarWarehouse.getAvailable(new Car()) == 0) {
                        Helpers.singleDealer.wait();
                        if (!Helpers.getInfo()) {
                            break;
                        }
                    }
                    element = myCarWarehouse.getElement();
                    myCarWarehouse.removeElement(new Car());
                    if (salesLogOn) {
                        Date currentDate = new Date();
                        String text = currentDate.toString() + ": " + "Dealer " + Thread.currentThread().getName() + ": " + "Auto " + element.getNumber() + " (Body: " + element.getBodyNumber() + ", Motor: " + element.getEngineNumber() + ", Accessory: " + element.getAccessoryNumber() + ")" + "\n";
                        System.out.print(text);
                        write.write(text);
                        write.flush();
                    }
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class ThreadFinisher implements Runnable {
    public void run() {
        synchronized (Helpers.addAccessory) {
            Helpers.addAccessory.notifyAll();
        }
        synchronized (Helpers.addBody) {
            Helpers.addBody.notifyAll();
        }
        synchronized (Helpers.addCar) {
            Helpers.addCar.notifyAll();
        }
        synchronized (Helpers.addEngine) {
            Helpers.addEngine.notifyAll();
        }
        synchronized (Helpers.singleDealer) {
            Helpers.singleDealer.notifyAll();
        }
        synchronized (Helpers.singleCheck) {
            Helpers.singleCheck.notifyAll();
        }
        synchronized (Helpers.finish) {
            Helpers.finish.notifyAll();
        }
    }
}