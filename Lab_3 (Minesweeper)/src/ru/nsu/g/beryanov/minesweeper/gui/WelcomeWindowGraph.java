package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MakeField;
import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindowGraph implements ActionListener {
    MinesweeperLogic newMinesweeper;
    MakeField newMakeField;
    private JLabel welcomeMessage1;
    private JLabel welcomeMessage2;
    private JLabel welcomeMessage3;
    private JTextField fieldSizeText;
    private JTextField minesAmountText;
    private JTextField name;
    private JButton readyButton;
    private JFrame welcomeFrame;

    void createWelcomeWindow() {
        welcomeFrame = new JFrame("MinesweeperLogic");
        welcomeMessage1 = new JLabel("Размер поля:      ");
        welcomeMessage2 = new JLabel("Количество мин:");
        welcomeMessage3 = new JLabel("Ваше имя:");

        fieldSizeText = new JTextField(2);
        minesAmountText = new JTextField(2);
        name = new JTextField(10);

        readyButton = new JButton("Готово");
        readyButton.setActionCommand("ReadyButtonPressed");
        readyButton.addActionListener(this);

        welcomeFrame.setLayout(new FlowLayout());
        welcomeFrame.setSize(200, 180);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        welcomeFrame.add(welcomeMessage1);
        welcomeFrame.add(fieldSizeText);
        welcomeFrame.add(welcomeMessage2);
        welcomeFrame.add(minesAmountText);
        welcomeFrame.add(welcomeMessage3);
        welcomeFrame.add(name);
        welcomeFrame.add(readyButton);
        welcomeFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("ReadyButtonPressed")) {
            String fieldSize = fieldSizeText.getText();
            String minesAmount = minesAmountText.getText();
            String knownName = name.getText();

            if (!(fieldSize.equals(""))) {
                newMinesweeper.setFieldSize(Integer.parseInt(fieldSize));
            } else newMinesweeper.setFieldSize(9);

            if (!(minesAmount.equals(""))) {
                newMinesweeper.setMinesAmount(Integer.parseInt(minesAmount));
            } else newMinesweeper.setMinesAmount(10);

            newMinesweeper.setMinesAmount(newMinesweeper.getMinesAmount());

            newMinesweeper.setPlayerName(knownName);
        }
        welcomeFrame.setVisible(false);
        new MakeFieldGraph(newMinesweeper, newMakeField);
    }

    public WelcomeWindowGraph(MinesweeperLogic minesweeper, MakeField makeField) {
        newMinesweeper = minesweeper;
        newMakeField = makeField;
        createWelcomeWindow();
    }
}
