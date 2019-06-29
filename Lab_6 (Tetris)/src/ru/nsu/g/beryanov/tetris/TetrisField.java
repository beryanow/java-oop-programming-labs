package ru.nsu.g.beryanov.tetris;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import ru.nsu.g.beryanov.tetris.ElementCharacteristics.elementShapes;

public class TetrisField extends JPanel implements ActionListener {
    private Timer timerUpdate;
    private static final int gameBoardWidth = 10;
    private static final int gameBoardHeight = 22;
    private boolean endGame = false;
    private boolean gameOn = false;
    private ElementCharacteristics currentBlock;
    private elementShapes[] gameBoard;
    private Color colorTable;
    private int currentTimerResolution;
    private int currentBlockX = 0;
    private int currentBlockY = 0;

    private int elementWidth() {
        return (int) getSize().getWidth() / gameBoardWidth;
    }
    private int elementHeight() {
        return (int) getSize().getHeight() / gameBoardHeight;
    }
    private elementShapes currentElementPosition(int x, int y) {
        return gameBoard[(y * gameBoardWidth) + x];
    }
    private void createBoard() {
        for (int i = 0; i < gameBoardWidth * gameBoardHeight; i++) {
            gameBoard[i] = elementShapes.noShape;
        }
    }
    TetrisField(int timerResolution) {
        setFocusable(true);
        setBackground(new Color(0, 0, 0));
        currentBlock = new ElementCharacteristics();
        timerUpdate = new Timer(timerResolution, this);
        timerUpdate.start();    // activate timerUpdate
        currentTimerResolution = timerResolution;
        gameBoard = new elementShapes[gameBoardWidth * gameBoardHeight];
        colorTable = new Color(189, 189, 189);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOn || currentBlock.getShape() == elementShapes.noShape) {
                    return;
                }
                int keycode = e.getKeyCode();
                switch (keycode) {
                    case KeyEvent.VK_LEFT:
                        isMovableMain(currentBlock, currentBlockX - 1, currentBlockY);
                        break;
                    case KeyEvent.VK_RIGHT:
                        isMovableMain(currentBlock, currentBlockX + 1, currentBlockY);
                        break;
                    case KeyEvent.VK_UP:
                        isMovableMain(currentBlock.rotateRight(), currentBlockX, currentBlockY);
                        break;
                    case KeyEvent.VK_DOWN:
                        advanceOneLine();
                        break;
                    case KeyEvent.VK_SPACE:
                        deliverElement();
                        break;
                }
            }
        });
        createBoard();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (endGame) {
            endGame = false;
            createElement();
        } else {
            advanceOneLine();
        }
    }
    void start() {
        gameOn = true;
        endGame = false;
        createBoard();
        createElement();
        timerUpdate.start();
    }
    @Override
    public void paint(Graphics g) {
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - gameBoardHeight * elementHeight();
        int temporaryY = currentBlockY;
        while (temporaryY > 0) {
            if (!isPossibleToMove(currentBlock, currentBlockX, temporaryY - 1, false))
                break;
            temporaryY--;
        }
        for (int i = 0; i < 4; i++) {
            int x = currentBlockX + currentBlock.getX(i);
            int y = temporaryY - currentBlock.getY(i);
            drawElement(g, x * elementWidth(), boardTop + (gameBoardHeight - y - 1) * elementHeight(), true);
        }
        for (int i = 0; i < gameBoardHeight; i++) {
            for (int j = 0; j < gameBoardWidth; j++) {
                elementShapes shape = currentElementPosition(j, gameBoardHeight - i - 1);
                if (shape != elementShapes.noShape)
                    drawElement(g, j * elementWidth(), boardTop + i * elementHeight(), false);
            }
        }
        if (currentBlock.getShape() != elementShapes.noShape) {
            for (int i = 0; i < 4; i++) {
                int x = currentBlockX + currentBlock.getX(i);
                int y = currentBlockY - currentBlock.getY(i);
                drawElement(g, x * elementWidth(), boardTop + (gameBoardHeight - y - 1) * elementHeight(), false);
            }
        }
    }
    private void drawElement(Graphics g, int x, int y, boolean isShadow) {
        Color curColor = colorTable;
        if (!isShadow) {
            g.setColor(curColor);
            g.fillRect(x + 1, y + 1, elementWidth() - 2, elementHeight() - 2);
        } else {
            g.setColor(curColor.darker().darker());
            g.fillRect(x + 1, y + 1, elementWidth() - 2, elementHeight() - 2);
        }
    }
    private boolean isMovableMain(ElementCharacteristics chkBlock, int chkX, int chkY) {
        return isPossibleToMove(chkBlock, chkX, chkY, true);
    }
    private void createElement() {
        currentBlock.setRandomShape();
        currentBlockX = gameBoardWidth / 2 + 1;
        currentBlockY = gameBoardHeight - 1 + currentBlock.minimalY();

        if (!isMovableMain(currentBlock, currentBlockX, currentBlockY)) {
            currentBlock.setShape(elementShapes.noShape);
            timerUpdate.stop();
            gameOn = false;
            start();
        }
    }
    private void makeEmpty() {
        boolean endGame = false;
        for (int i = gameBoardHeight - 1; i >= 0; i--) {
            boolean isFull = true;
            for (int j = 0; j < gameBoardWidth; j++) {
                if (currentElementPosition(j, i) == elementShapes.noShape) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                endGame = true;
                for (int k = i; k < gameBoardHeight - 1; k++) {
                    for (int l = 0; l < gameBoardWidth; ++l)
                        gameBoard[(k * gameBoardWidth) + l] = currentElementPosition(l, k + 1);
                }
            }
        }
        if (endGame) {
            this.endGame = true;
            currentBlock.setShape(elementShapes.noShape);
            timerUpdate.setDelay(currentTimerResolution);
            repaint();
        }
    }
    private boolean isPossibleToMove(ElementCharacteristics blockWatched, int watchedX, int watchedY, boolean flag) {
        for (int i = 0; i < 4; i++) {
            int x = watchedX + blockWatched.getX(i);
            int y = watchedY - blockWatched.getY(i);
            if (x < 0 || x >= gameBoardWidth || y < 0 || y >= gameBoardHeight)
                return false;
            if (currentElementPosition(x, y) != elementShapes.noShape) {
                return false;
            }
        }
        if (flag) {
            currentBlock = blockWatched;
            currentBlockX = watchedX;
            currentBlockY = watchedY;
            repaint();
        }
        return true;
    }
    private void makeMovable() {
        for (int i = 0; i < 4; i++) {
            int x = currentBlockX + currentBlock.getX(i);
            int y = currentBlockY - currentBlock.getY(i);
            gameBoard[(y * gameBoardWidth) + x] = currentBlock.getShape();
        }
        makeEmpty();
        if (!endGame) {
            createElement();
        }
    }
    private void advanceOneLine() {
        if (!isMovableMain(currentBlock, currentBlockX, currentBlockY - 1)) {
            makeMovable();
        }
    }
    private void deliverElement() {
        int temporaryY = currentBlockY;
        while (temporaryY > 0) {
            if (!isMovableMain(currentBlock, currentBlockX, temporaryY - 1))
                break;
            --temporaryY;
        }
        makeMovable();
    }
}
