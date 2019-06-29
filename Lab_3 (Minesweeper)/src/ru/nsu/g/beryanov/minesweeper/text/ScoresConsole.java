package ru.nsu.g.beryanov.minesweeper.text;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.PlayerResult;

import java.util.Collections;
import java.util.Vector;

public class ScoresConsole {
    ScoresConsole(MinesweeperLogic minesweeper) {
        int size = minesweeper.getFieldSize();
        int amount = minesweeper.getMinesAmount();

        Vector<PlayerResult> neededNames = new Vector<PlayerResult>();
        int newSize = minesweeper.getNamesCollection().size();

        for (int i = 0; i < newSize; i++) {
            if ((minesweeper.getNamesCollection().elementAt(i).fieldSize == size) && (minesweeper.getNamesCollection().elementAt(i).minesAmount == amount)) {
                neededNames.add(minesweeper.getNamesCollection().elementAt(i));
            }
        }

        for (int i = 0; i < neededNames.size() - 1; i++) {
            for (int j = i + 1; j < neededNames.size(); j++) {
                double time1 = neededNames.elementAt(i).gameTime;
                double time2 = neededNames.elementAt(j).gameTime;
                if (time1 > time2) {
                    Collections.swap(neededNames, i, j);
                }
            }
        }
        System.out.println("Рекорды (Поле: " + size + " x " + size + ", мины: " + amount + ")");
        for (int i = 0; i < neededNames.size(); i++) {
            System.out.println(Integer.toString(i + 1) + ". " + neededNames.elementAt(i).playerName + " -> " + neededNames.elementAt(i).gameTime + " сек");
        }
    }
}
