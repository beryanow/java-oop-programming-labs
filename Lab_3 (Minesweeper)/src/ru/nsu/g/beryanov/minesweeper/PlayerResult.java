package ru.nsu.g.beryanov.minesweeper;

public class PlayerResult {
    public String playerName;
    public double gameTime;
    public int fieldSize;
    public int minesAmount;

    public PlayerResult(String playerNameIn, double gameTimeIn, int fieldSizeIn, int minesAmountIn) {
        playerName = playerNameIn;
        gameTime = gameTimeIn;
        fieldSize = fieldSizeIn;
        minesAmount = minesAmountIn;
    }
}
