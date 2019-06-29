package ru.nsu.g.beryanov.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionsCheckTest {
    @Test
    public void test1() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        newMinesweeper.setFieldSize(10);
        int h = newMinesweeper.getFieldSize();
        assertEquals(h, 10);
    }

    @Test
    public void test2() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        newMinesweeper.setMinesAmount(2);
        int h = newMinesweeper.getMinesAmount();
        assertEquals(h, 2);
    }

    @Test
    public void test3() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        newMinesweeper.setPlayerName("example");
        String exampleStr = newMinesweeper.getPlayerName();
        assertEquals(exampleStr, "example");
    }

    @Test
    public void test4() {
        MinesweeperLogic newMinesweeper = new MinesweeperLogic();
        newMinesweeper.incTime();
        int y = newMinesweeper.getTime();
        assertEquals(y, 1);
    }
}