package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.PlayerResult;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public class ScoresWindowGraph {
    private JFrame scoresFrame;

    ScoresWindowGraph(MinesweeperLogic minesweeper) {
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

        scoresFrame = new JFrame("Рекорды (Поле: " + minesweeper.getFieldSize() + " x " + minesweeper.getFieldSize() + ", мины: " + minesweeper.getMinesAmount() + ")");
        scoresFrame.setLayout(new FlowLayout());
        scoresFrame.setSize(300, neededNames.size() * 40);

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scoresFrame.getContentPane().setLayout(new GridLayout(1, newSize));

        BoxLayout boxy = new BoxLayout(scoresFrame.getContentPane(), BoxLayout.Y_AXIS);
        scoresFrame.getContentPane().setLayout(boxy);
        for (int i = 0; i < neededNames.size(); i++) {
            JLabel playerOverallInfo = new JLabel(Integer.toString(i + 1) + ". " + neededNames.elementAt(i).playerName + " -> " + neededNames.elementAt(i).gameTime + " сек");
            scoresFrame.getContentPane().add(playerOverallInfo);
        }

        scoresFrame.setVisible(true);

    }
}
