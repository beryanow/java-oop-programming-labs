package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.MakeField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverGraph implements ActionListener {
    MinesweeperLogic newMinesweeper;
    MakeField newMakeField;
    private JLabel message;
    private JLabel information;
    private JButton restart;
    private JButton highScores;
    private JFrame overFrame;

    GameOverGraph(MinesweeperLogic minesweeper, MakeField makeField) {
        newMinesweeper = minesweeper;
        newMakeField = makeField;

        int time = newMinesweeper.getTime();
        overFrame = new JFrame("Поражение!");
        overFrame.setSize(300, 120);
        overFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        overFrame.setLayout(new FlowLayout());
        message = new JLabel("Вы проиграли!");
        information = new JLabel("Количество закрытых ячеек: " + newMakeField.getSuccessAmount());
        restart = new JButton("Новая игра");

        restart.addActionListener(this);
        restart.setActionCommand("Restart");

        overFrame.add(message);
        overFrame.add(information);
        overFrame.add(restart);
        overFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Restart")) {
            newMakeField.getGameFrame().getContentPane().removeAll();
            newMakeField.setSuccessAmount(0);
            new WelcomeWindowGraph(newMinesweeper, newMakeField);
            overFrame.setVisible(false);
        }
    }
}
