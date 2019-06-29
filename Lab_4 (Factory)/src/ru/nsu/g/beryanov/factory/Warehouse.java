package ru.nsu.g.beryanov.factory;

import java.util.*;

public class Warehouse<T> extends Observable implements Publisher {
    private Vector<T> elements = new Vector<>();
    private int capacity;
    private List<Observer> channels = new ArrayList<>();

    private void register(Observer outlet) {
        channels.add(outlet);
    }
    void createObservation(Warehouses x) {
        UniversalChannel universalChannel = new UniversalChannel(x);
        register(universalChannel);
        SleepChannel sleepChannel = new SleepChannel();
        register(sleepChannel);
        WorkChannel workChannel = new WorkChannel();
        register(workChannel);
    }
    void setCapacity(int x) {
        capacity = x;
    }
    public int getCapacity() {
        return capacity;
    }
    int getSize() {
        return elements.size();
    }
    int getAvailable(T object) {
        if (object instanceof Accessory) {
            synchronized (Helpers.singleAccessory) {
                return elements.size();
            }
        } else if (object instanceof Engine) {
            synchronized (Helpers.singleEngine) {
                return elements.size();
            }
        } else if (object instanceof Body) {
            synchronized (Helpers.singleBody) {
                return elements.size();
            }
        } else if (object instanceof Car) {
            synchronized (Helpers.singleCar) {
                return elements.size();
            }
        } else return elements.size();
    }
    T getElement() {
        T element;
        synchronized (Helpers.singleUniversal) {
            element = elements.elementAt(elements.size() - 1);
        }
        return element;
    }
    void addElement(T object) throws InterruptedException {
        if (object instanceof Accessory) {
            synchronized (Helpers.addAccessory) {
                if (elements.size() == capacity) {
                    Helpers.addAccessory.wait();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
                elements.add(object);
                channels.get(0).update(this, new Accessory());
            }
        } else if (object instanceof Engine) {
            synchronized (Helpers.addEngine) {
                if (elements.size() == capacity) {
                    Helpers.addEngine.wait();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
                elements.add(object);
                channels.get(0).update(this, new Engine());
            }
        } else if (object instanceof Body) {
            synchronized (Helpers.addBody) {
                if (elements.size() == capacity) {
                    Helpers.addBody.wait();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
                elements.add(object);
                channels.get(0).update(this, new Body());
            }
        } else if (object instanceof Car) {
            synchronized (Helpers.addCar) {
                if (elements.size() == capacity) {
                    int i = Integer.parseInt(Thread.currentThread().getName().substring(14));
                    channels.get(1).update(this, i);
                    Helpers.addCar.wait();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
                int i = Integer.parseInt(Thread.currentThread().getName().substring(14));
                channels.get(2).update(this, i);
                elements.add(object);
                channels.get(0).update(this, new Car());
            }
        }
    }
    void removeElement(T object) {
        if (object instanceof Accessory) {
            synchronized (Helpers.addAccessory) {
                if (elements.size() > 0) {
                    elements.removeElementAt(elements.size() - 1);
                    channels.get(0).update(this, new Accessory());
                    Helpers.addAccessory.notify();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } else if (object instanceof Engine) {
            synchronized (Helpers.addEngine) {
                if (elements.size() > 0) {
                    elements.removeElementAt(elements.size() - 1);
                    channels.get(0).update(this, new Engine());
                    Helpers.addEngine.notify();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } else if (object instanceof Body) {
            synchronized (Helpers.addBody) {
                if (elements.size() > 0) {
                    elements.removeElementAt(elements.size() - 1);
                    channels.get(0).update(this, new Body());
                    Helpers.addBody.notify();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } else if (object instanceof Car) {
            synchronized (Helpers.addCar) {
                if (elements.size() > 0) {
                    elements.removeElementAt(elements.size() - 1);
                    Helpers.carSold++;
                    channels.get(0).update(this, new Car());
                    Helpers.addCar.notify();
                    if (!Helpers.getInfo()) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
