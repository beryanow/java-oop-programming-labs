package ru.nsu.g.beryanov.minesweeper.text;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.MakeField;

import java.util.Scanner;

public class InitializeConsole {
    InitializeConsole(MakeField makeField, MinesweeperLogic minesweeper) {
        makeField.setSuccessAmount(0);
        int size = 9, amount = 10;
        String name;
        Scanner consoleScanner = new Scanner(System.in);

        System.out.println("Введите размер поля: ");
        size = Integer.parseInt(consoleScanner.next());
        System.out.println("Введите количество мин: ");
        amount = Integer.parseInt(consoleScanner.next());
        System.out.println("Введите имя игрока: ");
        name = consoleScanner.next();

        minesweeper.setMinesAmount(amount);
        minesweeper.setFieldSize(size);
        minesweeper.setMinesAmount(amount);
        minesweeper.setPlayerName(name);
    }
}
