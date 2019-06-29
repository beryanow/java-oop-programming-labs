package ru.nsu.g.beryanov.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinesAmountFieldGenTest {

    @Test
    public void test1() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        int size = newMinesweeper.getFieldSize();
        newMinesweeper.initMatrix(size);
        newMinesweeper.fieldMaking(new MakeField(), newMinesweeper, 0);
        int check = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (newMinesweeper.getMatrix()[i][j] == -1) {
                    check++;
                }
            }
        }
        assertEquals(check, newMinesweeper.getMinesAmount());
    }

    @Test
    public void test2() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();

        int size = newMinesweeper.getFieldSize();
        int amount = newMinesweeper.getMinesAmount();
        amount--;
        newMinesweeper.initMatrix(size);
        newMinesweeper.fieldMaking(new MakeField(), newMinesweeper, 0);
        int check = 0;
        for (int i = 0; i < size; i++) {
             for (int j = 0; j < size; j++) {
                if (newMinesweeper.getMatrix()[i][j] == -1) {
                    check++;
                }
            }
        }
        assertEquals(check, ++amount);
    }
}