package ru.nsu.g.beryanov.minesweeper.gui;

import ru.nsu.g.beryanov.minesweeper.MinesweeperLogic;
import ru.nsu.g.beryanov.minesweeper.MakeField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

public class MakeMineGraph implements ActionListener, MouseListener {
    MinesweeperLogic newMinesweeper = null;
    MakeField newMakeField = null;
    public JButton implement(int x, int y, MinesweeperLogic object, MakeField object2) {
        if (newMinesweeper == null) {
            newMinesweeper = object;
        }
        if (newMakeField == null) {
            newMakeField = object2;
        }
        JButton newMine = new JButton();
        newMine.setActionCommand(Integer.toString(x) + " " + Integer.toString(y));
        newMine.addMouseListener(this);
        newMine.addActionListener(this);
        return newMine;
    }

    public void actionPerformed(ActionEvent ae) {
        String actionCommand = ae.getActionCommand();
        Scanner stringScanner = new Scanner(actionCommand);
        int x1;
        int x2;
        x1 = Integer.parseInt(stringScanner.next()) - 1;
        x2 = Integer.parseInt(stringScanner.next()) - 1;
        if (newMinesweeper.getMatrix()[x1][x2] == -1) {

            ImageIcon icon = new ImageIcon("src/ru/nsu/g/beryanov/minesweeper/resources/mine.png");
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            newMakeField.getMatrixButtons()[x1][x2].setIcon(icon);

            for (int i = 0; i < newMinesweeper.getFieldSize(); i++) {
                for (int j = 0; j < newMinesweeper.getFieldSize(); j++) {
                    newMakeField.getMatrixButtons()[i][j].setEnabled(false);
                }
            }
            new GameOverGraph(newMinesweeper, newMakeField);

        } else {
            int size = newMinesweeper.getFieldSize();
            newMinesweeper.selectingRight(x1, x2, newMakeField, newMinesweeper, 1);
            if (newMakeField.getSuccessAmount() == size * size - newMinesweeper.getMinesAmount()) {
                new GameWinnerGraph(newMinesweeper, newMakeField);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            JButton chosen = (JButton) e.getSource();

            Scanner stringScanner = new Scanner(chosen.getActionCommand());
            int x1;
            int x2;
            x1 = Integer.parseInt(stringScanner.next()) - 1;
            x2 = Integer.parseInt(stringScanner.next()) - 1;

            if (chosen.isEnabled()) {
                if (newMinesweeper.getMatrix()[x1][x2] == -1) {
                    ImageIcon icon = new ImageIcon("src/ru/nsu/g/beryanov/minesweeper/resources/flag.png");
                    Image img = icon.getImage();
                    Image newimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    newMakeField.getMatrixButtons()[x1][x2].setIcon(icon);

                    newMinesweeper.decreaseMinesAmount();
                    if (newMinesweeper.getMinesAmount() == 0) {
                        new GameWinnerGraph(newMinesweeper, newMakeField);
                    }
                    newMinesweeper.getMatrix()[x1][x2] = -2;
                } else if (newMinesweeper.getMatrix()[x1][x2] == -2) {
                    newMakeField.getMatrixButtons()[x1][x2].setIcon(null);
                    newMinesweeper.getMatrix()[x1][x2] = -1;
                    newMinesweeper.increaseMinesAmount();
                } else if (newMinesweeper.getMatrix()[x1][x2] == 0) {
                    ImageIcon icon = new ImageIcon("src/ru/nsu/g/beryanov/minesweeper/resources/flag.png");
                    Image img = icon.getImage();
                    Image newimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    newMakeField.getMatrixButtons()[x1][x2].setIcon(icon);
                    newMinesweeper.getMatrix()[x1][x2] = -3;
                } else if (newMinesweeper.getMatrix()[x1][x2] == -3) {
                    newMakeField.getMatrixButtons()[x1][x2].setIcon(null);
                    newMinesweeper.getMatrix()[x1][x2] = 0;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
