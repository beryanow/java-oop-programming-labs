package ru.nsu.g.beryanov.tetris;

import java.awt.GridLayout;
import javax.swing.JFrame;

class TetrisWindow extends JFrame {
    TetrisWindow() {
        setTitle("Тетрис");
        setSize(280, 570);
        setResizable(false);
        setLayout(new GridLayout(1, 1));
        TetrisField myTetrisField = new TetrisField(380);
        add(myTetrisField);
        myTetrisField.start();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
