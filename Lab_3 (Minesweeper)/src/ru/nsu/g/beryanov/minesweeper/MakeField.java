package ru.nsu.g.beryanov.minesweeper;

import javax.swing.*;

public class MakeField {
    private JButton[][] matrixButtons;
    private JFrame gameFrame;
    private ButtonGroup mines;
    private int successAmount = 0;

    public JFrame getGameFrame() {
        return gameFrame;
    }

    public void initFrame() {
        gameFrame = new JFrame("Сапёр");
    }

    public void initButtons(int x) {
        matrixButtons = new JButton[x][x];
    }

    public void initMines() {
        mines = new ButtonGroup();
    }

    public int getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(int x) {
        successAmount = x;
    }

    public void increaseSuccessAmount() {
        successAmount++;
    }

    public ButtonGroup getMines() {
        return mines;
    }

    public JButton[][] getMatrixButtons() {
        return matrixButtons;
    }
}
