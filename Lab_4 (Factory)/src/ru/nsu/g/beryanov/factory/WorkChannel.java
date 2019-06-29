package ru.nsu.g.beryanov.factory;

import ru.nsu.g.beryanov.gui.Graphics;

import java.util.Observable;
import java.util.Observer;

public class WorkChannel implements Observer {
    private static Graphics myGraphics;

    @Override
    public void update(Observable agency, Object newsItem) {
        myGraphics.getStatusWorkers()[(int) newsItem - 1].setText("Working...");
    }
    public WorkChannel(Graphics y) {
        myGraphics = y;
    }
    WorkChannel() {}
}

