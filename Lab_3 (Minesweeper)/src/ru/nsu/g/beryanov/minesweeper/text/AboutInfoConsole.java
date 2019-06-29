package ru.nsu.g.beryanov.minesweeper.text;

public class AboutInfoConsole {
    AboutInfoConsole() {
        System.out.println("Команды:");
        System.out.println("NewGames -> начать новую игру");
        System.out.println("HighScores -> узнать таблицу рекордов");
        System.out.println("Exit -> завершить программу");
        System.out.println("Flag x y -> установить флаг на ячейку [x,y]");
        System.out.println();
        System.out.println("Поле:");
        System.out.println("X -> возле клетки мин не было, ячейка была выбрана ранее");
        System.out.println("0 -> свободная ячейка, можно выбрать");
        System.out.println("F -> установлен флаг");
        System.out.println("Цифра, отличная от нуля -> сколько мин находится возле этой ячейки");
    }
}
