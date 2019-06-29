package ru.nsu.g.beryanov.minesweeper.text;

import java.util.Scanner;

import ru.nsu.g.beryanov.minesweeper.MakeField;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.PlayerResult;

public class SolveProblemConsole {
    SolveProblemConsole(MinesweeperLogic minesweeper, MakeField makeField) {
        Scanner consoleScanner = new Scanner(System.in);
        int over = 0;

        int x = 0, y;
        int z = -1;
        int size = minesweeper.getFieldSize();
        int amount = minesweeper.getMinesAmount();
        while (over == 0) {
            minesweeper.printMatrix(minesweeper);
            String probFlag;
            probFlag = consoleScanner.next();
            if (probFlag.equals("Flag")) {
                y = Integer.parseInt(consoleScanner.next());
                z = Integer.parseInt(consoleScanner.next());
            } else {
                x = Integer.parseInt(probFlag);
                y = Integer.parseInt(consoleScanner.next());
            }

            if (z == -1) {
                if (minesweeper.getMatrix()[x][y] == -1) {
                    System.out.println("Вы проиграли!");
                    over = 1;
                } else if (minesweeper.getMatrix()[x][y] == -2) {
                    System.out.println("Вы уже выбирали эту ячейку!");
                } else if (minesweeper.getMatrix()[x][y] == 0) {
                    minesweeper.selectingRight(x, y + 1, makeField, minesweeper, 0);
                    if (makeField.getSuccessAmount() == size * size - amount) {
                        double playerTime = (double) minesweeper.getTime() / 10;
                        minesweeper.printMatrix(minesweeper);
                        System.out.println("Вы победили! Ваше время: " + playerTime + " сек");

                        String playerName = minesweeper.getPlayerName();
                        int fieldSize = minesweeper.getFieldSize();
                        int minesAmount = minesweeper.getMinesAmount();
                        PlayerResult newResult = new PlayerResult(playerName, playerTime, fieldSize, minesAmount);
                        minesweeper.getNamesCollection().add(newResult);

                        over = 1;
                    }
                }
            } else if (minesweeper.getMatrix()[y][z] == -1) {
                minesweeper.decreaseMinesAmount();
                if (minesweeper.getMinesAmount() == 0) {
                    double playerTime = (double) minesweeper.getTime() / 10;
                    minesweeper.getMatrix()[y][z] = -3;
                    minesweeper.printMatrix(minesweeper);
                    System.out.println("Вы победили! Ваше время: " + playerTime + " сек");
                    String playerName = minesweeper.getPlayerName();
                    int fieldSize = minesweeper.getFieldSize();
                    int minesAmount = minesweeper.getMinesAmount();
                    PlayerResult newResult = new PlayerResult(playerName, playerTime, fieldSize, minesAmount);
                    minesweeper.getNamesCollection().add(newResult);
                    break;
                }
                minesweeper.getMatrix()[y][z] = -3;
                z = -1;
            } else if (minesweeper.getMatrix()[y][z] == -3) {
                minesweeper.increaseMinesAmount();
                minesweeper.getMatrix()[y][z] = -1;
                z = -1;
            } else if (minesweeper.getMatrix()[y][z] == 0) {
                minesweeper.getMatrix()[y][z] = -4;
                z = -1;
            } else if (minesweeper.getMatrix()[y][z] == -4) {
                minesweeper.getMatrix()[y][z] = 0;
                z = -1;
            }
        }
    }
}
