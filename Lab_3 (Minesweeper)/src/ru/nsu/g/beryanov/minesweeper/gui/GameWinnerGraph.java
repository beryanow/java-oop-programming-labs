package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.PlayerResult;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.MakeField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWinnerGraph implements ActionListener {
    MinesweeperLogic newMinesweeper;
    MakeField newMakeField;
    private JLabel message;
    private JLabel information;
    private JButton restart;
    private JButton highScores;
    private JFrame overFrame;

    GameWinnerGraph(MinesweeperLogic minesweeper, MakeField makeField) {
        newMinesweeper = minesweeper;
        newMakeField = makeField;

        double playerTime = (double) newMinesweeper.getTime() / 10;
        String playerName = newMinesweeper.getPlayerName();
        int fieldSize = newMinesweeper.getFieldSize();
        int minesAmount =  newMinesweeper.getMinesAmount();
        PlayerResult newResult = new PlayerResult(playerName, playerTime, fieldSize, minesAmount);
        newMinesweeper.getNamesCollection().add(newResult);

        overFrame = new JFrame("Победа!");
        overFrame.setSize(300, 105);
        overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        overFrame.setLayout(new FlowLayout());
        message = new JLabel("Вы выиграли!");
        information = new JLabel("Ваше время: " + playerTime + " сек");
        restart = new JButton("Новая игра");
        restart.addActionListener(this);
        restart.setActionCommand("Restart");
        highScores = new JButton("Таблица рекордов");
        highScores.setActionCommand("Scores");
        highScores.addActionListener(this);
        overFrame.add(message);
        overFrame.add(information);
        overFrame.add(restart);
        overFrame.add(highScores);
        overFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Restart")) {
            newMakeField.getGameFrame().getContentPane().removeAll();
            newMakeField.setSuccessAmount(0);
            new WelcomeWindowGraph(newMinesweeper, newMakeField);
            overFrame.setVisible(false);
        } else if (ae.getActionCommand().equals("Scores")) {
            new ScoresWindowGraph(newMinesweeper);
        }
    }
}
