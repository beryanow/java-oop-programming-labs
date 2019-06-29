package ru.nsu.g.beryanov.factory;

class Warehouses {
    private Warehouse<Engine> myEngineWarehouse;
    private Warehouse<Body> myBodyWarehouse;
    private Warehouse<Accessory> myAccessoryWarehouse;
    private Warehouse<Car> myCarWarehouse;

    void setEngine(Warehouse x, Engine y) {
        myEngineWarehouse = x;
    }
    void setBody(Warehouse x, Body y) {
        myBodyWarehouse = x;
    }
    void setAccessory(Warehouse x, Accessory y) {
        myAccessoryWarehouse = x;
    }
    void setCar(Warehouse x, Car y) {
        myCarWarehouse = x;
    }
    Warehouse<Engine> getEngineWarehouse() {
        return myEngineWarehouse;
    }
    Warehouse<Body> getBodyWarehouse() {
        return myBodyWarehouse;
    }
    Warehouse<Accessory> getAccessoryWarehouse() {
        return myAccessoryWarehouse;
    }
    Warehouse<Car> getCarWarehouse() {
        return myCarWarehouse;
    }
}
