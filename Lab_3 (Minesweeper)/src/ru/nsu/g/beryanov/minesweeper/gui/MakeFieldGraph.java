package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.MakeField;

import javax.swing.*;
import java.awt.*;

public class MakeFieldGraph {
    MakeFieldGraph(MinesweeperLogic minesweeper, MakeField makeField) {
        minesweeper.clearTime();

        makeField.getGameFrame().repaint();
        int fieldSize = minesweeper.getFieldSize();
        makeField.getGameFrame().setSize(700, 700);
        makeField.getGameFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel();
        gridPanel.setSize(600, 600);
        gridPanel.setLayout(new GridLayout(fieldSize, fieldSize));

        makeField.initMines();
        makeField.initButtons(fieldSize);
        minesweeper.initMatrix(fieldSize);

        minesweeper.fieldMaking(makeField, minesweeper, 1);

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                gridPanel.add(makeField.getMatrixButtons()[i][j]);
            }
        }

        makeField.getGameFrame().add(gridPanel);
        makeField.getGameFrame().setVisible(true);
    }
}
