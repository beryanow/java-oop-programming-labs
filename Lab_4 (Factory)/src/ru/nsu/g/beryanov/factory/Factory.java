package ru.nsu.g.beryanov.factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Factory {
    private int N = 400;
    private int M = 20;
    private int engineCapacity;
    private int bodyCapacity;
    private int accessoryCapacity;
    private int carCapacity;
    private int workerAmount;
    private int dealerAmount;
    private int supplierAmount;
    private boolean salesLogOn;
    private FileWriter write;
    private Warehouses myWarehouses;
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;
    private Warehouse<Car> myCarWarehouse;

    public Warehouse<Engine> getMyEngineWarehouse() {
        return myEngineWarehouse;
    }
    public Warehouse<Body> getMyBodyWarehouse() {
        return myBodyWarehouse;
    }
    public Warehouse<Accessory> getMyAccessoryWarehouse() {
        return myAccessoryWarehouse;
    }
    public Warehouse<Car> getMyCarWarehouse() {
        return myCarWarehouse;
    }
    public int getWorkerAmount() {
        return workerAmount;
    }
    private void finishThreads() throws InterruptedException {
        Helpers.setIsNotInterruptedFalse();
    }
    public void readConfig() throws IOException {
        Properties prop = new Properties();
        InputStream configStream = Factory.class.getClassLoader().getResourceAsStream("ru/nsu/g/beryanov/config.properties");
        try {
            prop.load(configStream);
        } catch (Exception e) {
            System.err.println("config.properties cannot be found");
            System.exit(1);
        }

        engineCapacity = Integer.parseInt(prop.getProperty("engineStorageCapacity"));
        bodyCapacity = Integer.parseInt(prop.getProperty("bodyStorageCapacity"));
        accessoryCapacity = Integer.parseInt(prop.getProperty("accessoriesStorageCapacity"));
        carCapacity = Integer.parseInt(prop.getProperty("carsStorageCapacity"));
        workerAmount = Integer.parseInt(prop.getProperty("workersAmount"));
        dealerAmount = Integer.parseInt(prop.getProperty("dealersAmount"));
        supplierAmount = Integer.parseInt(prop.getProperty("accessoriesSuppliersAmount"));
        salesLogOn = prop.getProperty("salesLog").equals("true");
        if (salesLogOn) {
            write = new FileWriter("logs.txt", false);
        }
        myEngineWarehouse = new Warehouse<>();
        myBodyWarehouse = new Warehouse<>();
        myAccessoryWarehouse = new Warehouse<>();
        myCarWarehouse = new Warehouse<>();
        myWarehouses = new Warehouses();
        myWarehouses.setEngine(myEngineWarehouse, new Engine());
        myWarehouses.setBody(myBodyWarehouse, new Body());
        myWarehouses.setAccessory(myAccessoryWarehouse, new Accessory());
        myWarehouses.setCar(myCarWarehouse, new Car());

        myEngineWarehouse.setCapacity(engineCapacity);
        myBodyWarehouse.setCapacity(bodyCapacity);
        myAccessoryWarehouse.setCapacity(accessoryCapacity);
        myCarWarehouse.setCapacity(carCapacity);

        myEngineWarehouse.createObservation(myWarehouses);
        myBodyWarehouse.createObservation(myWarehouses);
        myAccessoryWarehouse.createObservation(myWarehouses);
        myCarWarehouse.createObservation(myWarehouses);
    }
    public void makePerformance() throws InterruptedException {
        DealerThread[] myDealerThread = new DealerThread[dealerAmount];
        for (int i = 0; i < dealerAmount; i++) {
            myDealerThread[i] = new DealerThread(M, myCarWarehouse, salesLogOn, write);
        }
        Thread[] dealerThread = new Thread[dealerAmount];
        for (int i = 0; i < dealerAmount; i++) {
            dealerThread[i] = new Thread(myDealerThread[i]);
        }
        for (int i = 0; i < dealerAmount; i++) {
            dealerThread[i].start();
        }
        EngineThread myEngineThread = new EngineThread(N, myEngineWarehouse, myBodyWarehouse, myAccessoryWarehouse);
        BodyThread myBodyThread = new BodyThread(N, myEngineWarehouse, myBodyWarehouse, myAccessoryWarehouse);
        AccessoryThread[] myAccessoryThread = new AccessoryThread[supplierAmount];
        for (int i = 0; i < supplierAmount; i++) {
            myAccessoryThread[i] = new AccessoryThread(N, myEngineWarehouse, myBodyWarehouse, myAccessoryWarehouse);
        }
        Thread engineThread = new Thread(myEngineThread);
        Thread bodyThread = new Thread(myBodyThread);
        Thread[] accessoryThread = new Thread[supplierAmount];
        for (int i = 0; i < supplierAmount; i++) {
            accessoryThread[i] = new Thread(myAccessoryThread[i]);
        }

        engineThread.start();
        bodyThread.start();
        for (int i = 0; i < supplierAmount; i++) {
            accessoryThread[i].start();
        }
        WorkerThread[] myWorkerThread = new WorkerThread[workerAmount];
        for (int i = 0; i < workerAmount; i++) {
            myWorkerThread[i] = new WorkerThread(N, myCarWarehouse, myEngineWarehouse, myBodyWarehouse, myAccessoryWarehouse);
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(workerAmount);
        for (int i = 0; i < workerAmount; i++) {
            threadPool.submit(myWorkerThread[i]);
        }

        Thread.sleep(7000);

        finishThreads();
        for (int i = 0; i < workerAmount; i++) {
            myWorkerThread[i].finishThreads(i + 1);
        }
        ThreadFinisher myThreadFinisher = new ThreadFinisher();
        Thread threadFinisher = new Thread(myThreadFinisher);
        threadFinisher.start();
        threadFinisher.join();
        engineThread.join();
        bodyThread.join();
        for (int i = 0; i < supplierAmount; i++) {
            accessoryThread[i].join();
        }
        for (int i = 0; i < dealerAmount; i++) {
            dealerThread[i].join();
        }
        threadPool.shutdown();
    }
}
