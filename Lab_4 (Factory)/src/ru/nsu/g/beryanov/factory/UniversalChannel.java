package ru.nsu.g.beryanov.factory;

import ru.nsu.g.beryanov.gui.Graphics;

import java.util.Observable;
import java.util.Observer;

public class UniversalChannel implements Observer {
    private Warehouses myWarehouses;
    private static Graphics myGraphics;

    @Override
    public void update(Observable agency, Object newsItem) {
        synchronized (Helpers.singleUpdate) {
            if (newsItem instanceof Body) {
                int size = myWarehouses.getBodyWarehouse().getSize();
                myGraphics.getProgressBars()[0].setValue(size);
                myGraphics.getStatusBody().setText(size + "/" + myWarehouses.getBodyWarehouse().getCapacity());
            } else if (newsItem instanceof Engine) {
                int size = myWarehouses.getEngineWarehouse().getSize();
                myGraphics.getProgressBars()[1].setValue(size);
                myGraphics.getStatusEngine().setText(size + "/" + myWarehouses.getEngineWarehouse().getCapacity());
            } else if (newsItem instanceof Accessory) {
                int size = myWarehouses.getAccessoryWarehouse().getSize();
                myGraphics.getProgressBars()[2].setValue(size);
                myGraphics.getStatusAccessory().setText(size + "/" + myWarehouses.getAccessoryWarehouse().getCapacity());
            } else if (newsItem instanceof Car) {
                int size = myWarehouses.getCarWarehouse().getSize();
                myGraphics.getProgressBars()[3].setValue(size);
                myGraphics.getStatusCarSold().setText(String.valueOf(Helpers.carSold));
                myGraphics.getStatusCar().setText(size + "/" + myWarehouses.getCarWarehouse().getCapacity());
            }
        }
    }
    UniversalChannel(Warehouses x) {
        myWarehouses = x;
    }
    public UniversalChannel(Graphics y) {
        myGraphics = y;
    }
}
