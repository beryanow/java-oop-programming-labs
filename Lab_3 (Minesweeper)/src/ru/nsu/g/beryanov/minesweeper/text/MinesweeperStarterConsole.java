package ru.nsu.g.beryanov.minesweeper.text;

import java.util.Scanner;

import ru.nsu.g.beryanov.minesweeper.MakeField;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;

public class MinesweeperStarterConsole {
    public static void main(String[] args) {
        MinesweeperLogic NewMinesweeper = new MinesweeperLogic();
        MakeField NewMakeField = new MakeField();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        NewMinesweeper.incTime();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        NewMinesweeper.initNamesCollections();
        NewMinesweeper.initTime();
        Scanner consoleScanner = new Scanner(System.in);
        new NewInitConsole(NewMakeField, NewMinesweeper);
        NewMinesweeper.clearTime();
        new SolveProblemConsole(NewMinesweeper, NewMakeField);
        String operation;

        while (!(operation = consoleScanner.nextLine()).equals("Exit")) {
            switch (operation) {
                case "New Game":
                    new NewInitConsole(NewMakeField, NewMinesweeper);
                    NewMinesweeper.clearTime();
                    new SolveProblemConsole(NewMinesweeper, NewMakeField);
                    break;
                case "High Scores":
                    new ScoresConsole(NewMinesweeper);
                    break;
                case "About":
                    new AboutInfoConsole();
                    break;
            }
        }
        NewMinesweeper.finishConsole();
    }
}
