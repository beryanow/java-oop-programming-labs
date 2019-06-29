package ru.nsu.g.beryanov.minesweeper;

import ru.nsu.g.beryanov.minesweeper.gui.MakeMineGraph;

import java.util.Random;
import java.util.Vector;

public class MinesweeperLogic {
    private int[][] matrix;
    private int fieldSize, minesAmount;
    private int time;
    private Vector<PlayerResult> namesCollection;
    private String playerName;

    public void fieldMaking(MakeField makeField, MinesweeperLogic minesweeper, int isGraph) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (isGraph == 1) {
                    MakeMineGraph Factory = new MakeMineGraph();
                    makeField.getMatrixButtons()[i][j] = Factory.implement(i + 1, j + 1, minesweeper, makeField);
                }
                minesweeper.getMatrix()[i][j] = 0;
            }
        }

        int minesAmount = minesweeper.getMinesAmount();
        Random random = new Random();
        while (minesAmount != 0) {
            int x = random.nextInt(fieldSize);
            int y = random.nextInt(fieldSize);
            if (minesweeper.getMatrix()[x][y] == 0) {
                minesweeper.getMatrix()[x][y] = -1;
                minesAmount--;
            }
        }
    }

    public void initMatrix(int x) {
        matrix = new int[x][x];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Vector<PlayerResult> getNamesCollection() {
        return namesCollection;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String newPlayer) {
        playerName = newPlayer;
    }

    public void setFieldSize(int x) {
        fieldSize = x;
    }

    public int getMinesAmount() {
        return minesAmount;
    }

    public void setMinesAmount(int x) {
        minesAmount = x;
    }

    public void decreaseMinesAmount() {
        minesAmount--;
    }

    public void increaseMinesAmount() {
        minesAmount++;
    }

    public void incTime() {
        time++;
    }

    public void initTime() {
        time = 0;
    }

    public void initNamesCollections() {
        namesCollection = new Vector<PlayerResult>();
    }

    public void clearTime() {
        time = 0;
    }

    public int getTime() {
        return time;
    }

    public void finishConsole() {
        System.exit(0);
    }

    public void selectingRight(int x, int y, MakeField makeField, MinesweeperLogic minesweeper, int isGraph) {
        makeField.increaseSuccessAmount();
        int sum = 0;
        int size = minesweeper.getFieldSize() - 1;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!((i < 0) || (i > size) || (j > size) || (j < 0))) {
                    int value = minesweeper.getMatrix()[i][j];
                    if (isGraph == 1) {
                        if (value < -1) {
                            value = 0;
                        }
                        if (value == -1) {
                            value = 1;
                        }
                    } else {
                        if (value == -3) {
                            value = 1;
                        } else if (value < -1) {
                            value = 0;
                        } else if (value == -1) {
                            value = 1;
                        } else if (value > 0) {
                            value = 0;
                        }
                    }
                    sum += value;
                }
            }
        }

        if (sum == 0) {
            if (isGraph == 1) {
                makeField.getMatrixButtons()[x][y].setEnabled(false);
            }
            minesweeper.getMatrix()[x][y] = -2;

            if ((x - 1 >= 0) && (minesweeper.getMatrix()[x - 1][y] == 0)) {
                if (isGraph == 1) {
                    minesweeper.selectingRight(x - 1, y, makeField, minesweeper, 1);
                } else {
                    minesweeper.selectingRight(x - 1, y, makeField, minesweeper, 0);
                }
            }

            if ((x + 1 <= size) && (minesweeper.getMatrix()[x + 1][y] == 0)) {
                if (isGraph == 1) {
                    minesweeper.selectingRight(x + 1, y, makeField, minesweeper, 1);
                } else {
                    minesweeper.selectingRight(x + 1, y, makeField, minesweeper, 0);
                }
            }

            if ((y - 1 >= 0) && (minesweeper.getMatrix()[x][y - 1] == 0)) {
                if (isGraph == 1) {
                    minesweeper.selectingRight(x, y - 1, makeField, minesweeper, 1);
                } else {
                    minesweeper.selectingRight(x, y - 1, makeField, minesweeper, 0);
                }
            }

            if ((y + 1 <= size) && (minesweeper.getMatrix()[x][y + 1] == 0)) {
                if (isGraph == 1) {
                    minesweeper.selectingRight(x, y + 1, makeField, minesweeper, 1);
                } else {
                    minesweeper.selectingRight(x, y + 1, makeField, minesweeper, 0);
                }
            }

        } else {
            if (isGraph == 1) {
                minesweeper.getMatrix()[x][y] = -2;
                makeField.getMatrixButtons()[x][y].setText(Integer.toString(sum));
                makeField.getMatrixButtons()[x][y].setEnabled(false);
            } else {
                minesweeper.getMatrix()[x][y] = sum;
            }
        }
    }

    public void printMatrix(MinesweeperLogic minesweeper) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                String sym;
                int value = minesweeper.getMatrix()[i][j];
                if (value == -1)
                    sym = "0";
                else if (value == -2)
                    sym = "X";
                else if (value == -3)
                    sym = "F";
                else if (value == -4)
                    sym = "F";
                else sym = Integer.toString(value);
                System.out.print(sym + " ");
            }
            System.out.print('\n');
        }
    }
}
