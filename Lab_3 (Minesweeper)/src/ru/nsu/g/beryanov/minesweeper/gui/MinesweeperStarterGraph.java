package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MakeField;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class MinesweeperStarterGraph {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        MakeField newMakeField = new MakeField();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        newMinesweeper.incTime();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        newMinesweeper.initTime();
        newMakeField.initFrame();
        newMinesweeper.initNamesCollections();

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                new WelcomeWindowGraph(newMinesweeper, newMakeField);
            }
        });

    }
}
