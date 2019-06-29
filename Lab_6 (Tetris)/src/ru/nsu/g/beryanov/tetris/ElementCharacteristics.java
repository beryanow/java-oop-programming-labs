package ru.nsu.g.beryanov.tetris;

import java.util.Random;

class ElementCharacteristics {
    private elementShapes currentShape;
    private int[][] coordinates;
    private int[][][] elementsTable;

    enum elementShapes {
        noShape, zShape, sShape, iShape, tShape, oShape, lShape, jShape
    }
    void setShape(elementShapes shape) {
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].length >= 0)
                System.arraycopy(elementsTable[shape.ordinal()][i], 0, coordinates[i], 0, coordinates[i].length);
        }
        currentShape = shape;
    }
    void setRandomShape() {
        Random number = new Random();
        int index = Math.abs(number.nextInt()) % 7 + 1;
        setShape(elementShapes.values()[index]);
    }
    private void initializeTable() {
        elementsTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };
    }
    ElementCharacteristics() {
        coordinates = new int[4][2];
        initializeTable();
        setShape(elementShapes.noShape);
    }
    elementShapes getShape() {
        return currentShape;
    }
    private void setX(int index, int x) {
        coordinates[index][0] = x;
    }
    private void setY(int index, int y) {
        coordinates[index][1] = y;
    }
    int getX(int index) {
        return coordinates[index][0];
    }
    int getY(int index) {
        return coordinates[index][1];
    }
    int minimalY() {
        int edited = 0;
        for (int[] coord : coordinates) {
            edited = Math.min(edited, coord[1]);
        }
        return edited;
    }
    ElementCharacteristics rotateRight() {
        if (currentShape == elementShapes.oShape) {
            return this;
        }
        ElementCharacteristics edited = new ElementCharacteristics();
        edited.currentShape = currentShape;
        for (int i = 0; i < coordinates.length; i++) {
            edited.setX(i, -getY(i));
            edited.setY(i, getX(i));
        }
        return edited;
    }
}