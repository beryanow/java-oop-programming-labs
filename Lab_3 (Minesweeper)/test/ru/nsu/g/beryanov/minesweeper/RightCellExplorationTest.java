package ru.nsu.g.beryanov.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RightCellExplorationTest {
    @Test
    public void test1() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        MakeField newMakeField = new MakeField();

        newMinesweeper.setFieldSize(9);
        int size = newMinesweeper.getFieldSize();
        newMinesweeper.initMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMinesweeper.getMatrix()[i][j] = 0;
            }
        }
        newMinesweeper.getMatrix()[2][2] = -1;
        newMinesweeper.selectingRight(0, 0, newMakeField, newMinesweeper, 0);
        assertEquals(newMinesweeper.getMatrix()[0][0], -2);
        assertEquals(newMinesweeper.getMatrix()[2][2], -1);
        assertEquals(newMinesweeper.getMatrix()[2][1], 1);
    }

    @Test
    public void test2() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        MakeField newMakeField = new MakeField();

        newMinesweeper.setFieldSize(3);
        int size = newMinesweeper.getFieldSize();
        newMinesweeper.initMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMinesweeper.getMatrix()[i][j] = 0;
            }
        }
        newMinesweeper.getMatrix()[1][1] = -1;
        newMinesweeper.selectingRight(0, 0, newMakeField, newMinesweeper, 0);
        assertEquals(newMinesweeper.getMatrix()[0][0], 1);
        assertEquals(newMinesweeper.getMatrix()[0][1], 0);
        assertEquals(newMinesweeper.getMatrix()[0][2], 0);
    }

    @Test
    public void test3() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        MakeField newMakeField = new MakeField();

        newMinesweeper.setFieldSize(3);
        int size = newMinesweeper.getFieldSize();
        newMinesweeper.initMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMinesweeper.getMatrix()[i][j] = 0;
            }
        }
        newMinesweeper.getMatrix()[0][0] = -1;
        newMinesweeper.getMatrix()[2][2] = -1;
        newMinesweeper.selectingRight(1, 1, newMakeField, newMinesweeper, 0);
        assertEquals(newMinesweeper.getMatrix()[1][1], 2);
    }
}
