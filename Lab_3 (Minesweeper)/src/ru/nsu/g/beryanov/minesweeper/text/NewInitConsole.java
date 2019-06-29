package ru.nsu.g.beryanov.minesweeper.text;

import ru.nsu.g.beryanov.minesweeper.MakeField;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;

public class NewInitConsole {
    NewInitConsole(MakeField makeField, MinesweeperLogic minesweeper) {
        new InitializeConsole(makeField, minesweeper);
        minesweeper.initMatrix(minesweeper.getFieldSize());
        minesweeper.fieldMaking(new MakeField(), minesweeper, 0);
    }
}
