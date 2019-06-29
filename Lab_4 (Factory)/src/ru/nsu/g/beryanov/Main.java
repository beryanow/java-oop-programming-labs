package ru.nsu.g.beryanov;

import ru.nsu.g.beryanov.factory.*;
import ru.nsu.g.beryanov.gui.Graphics;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, InvocationTargetException {
        Factory carFactory = new Factory();
        carFactory.readConfig();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                Graphics myGraphics;
                myGraphics = new Graphics(carFactory.getWorkerAmount(), carFactory.getMyBodyWarehouse().getCapacity(), carFactory.getMyEngineWarehouse().getCapacity(), carFactory.getMyAccessoryWarehouse().getCapacity(), carFactory.getMyCarWarehouse().getCapacity());
                new UniversalChannel(myGraphics);
                new WorkChannel(myGraphics);
                new WaitChannel(myGraphics);
                new SleepChannel(myGraphics);
                new FinishChannel(myGraphics);
            }
        });
        Thread.sleep(2500);
        carFactory.makePerformance();
    }
}
