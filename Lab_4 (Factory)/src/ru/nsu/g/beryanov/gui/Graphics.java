package ru.nsu.g.beryanov.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class Graphics extends Observable {
    private JFrame overallFrame;
    private JLabel labelBody;
    private JLabel labelEngine;
    private JLabel labelAccessory;
    private JLabel[] labelsWorkers;
    private JLabel labelCar;
    private JLabel labelCarSold;
    private JLabel statusBody;
    private JLabel statusEngine;
    private JLabel statusAccessory;
    private JLabel[] statusWorkers;
    private JLabel statusCar;
    private JLabel statusCarSold;
    private JProgressBar[] progressBars;

    public JLabel getStatusEngine() {
        return statusEngine;
    }
    public JLabel[] getStatusWorkers() {
        return statusWorkers;
    }
    public JProgressBar[] getProgressBars() {
        return progressBars;
    }
    public  JLabel getStatusBody() {
        return statusBody;
    }
    public JLabel getStatusAccessory() {
        return statusAccessory;
    }
    public JLabel getStatusCar() {
        return statusCar;
    }
    public JLabel getStatusCarSold() {
        return statusCarSold;
    }
    private void createFactoryWindow(int workerAmount, int maxBody, int maxEngine, int maxAccessory, int maxCar) {
        labelsWorkers = new JLabel[workerAmount];
        statusWorkers = new JLabel[workerAmount];
        progressBars = new JProgressBar[4];
        overallFrame = new JFrame("Factory");
        overallFrame.setLayout(new GridLayout(1, 3));
        JPanel labels = new JPanel(new GridLayout(5 + workerAmount, 1));
        JPanel status = new JPanel(new GridLayout(5 + workerAmount, 1));
        JPanel progress = new JPanel(new GridLayout(5 + workerAmount, 1));
        labels.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 10));
        progress.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30));
        progressBars[0] = new JProgressBar();
        progressBars[0].setStringPainted(true);
        progressBars[0].setMinimum(0);
        progressBars[0].setMaximum(maxBody);
        progressBars[0].setValue(0);
        progress.add(progressBars[0]);
        progressBars[1] = new JProgressBar();
        progressBars[1].setStringPainted(true);
        progressBars[1].setMinimum(0);
        progressBars[1].setMaximum(maxEngine);
        progressBars[1].setValue(0);
        progress.add(progressBars[1]);
        progressBars[2] = new JProgressBar();
        progressBars[2].setStringPainted(true);
        progressBars[2].setMinimum(0);
        progressBars[2].setMaximum(maxAccessory);
        progressBars[2].setValue(0);
        progress.add(progressBars[2]);
        for (int i = 0; i < workerAmount; i++) {
            JLabel emptyLabel = new JLabel("");
            progress.add(emptyLabel);
        }
        progressBars[3] = new JProgressBar();
        progressBars[3].setStringPainted(true);
        progressBars[3].setMinimum(0);
        progressBars[3].setMaximum(maxCar);
        progressBars[3].setValue(0);
        progress.add(progressBars[3]);
        JLabel emptyLabel = new JLabel("");
        progress.add(emptyLabel);
        labelBody = new JLabel("Bodies");
        labelEngine = new JLabel("Engines");
        labelAccessory = new JLabel("Accessories");
        labels.add(labelBody);
        labels.add(labelEngine);
        labels.add(labelAccessory);
        for (int i = 0; i < workerAmount; i++) {
            labelsWorkers[i] = new JLabel("Worker " + (i + 1));
            labels.add(labelsWorkers[i]);
        }
        labelCar = new JLabel("Cars");
        labelCarSold = new JLabel("Sold cars");
        labels.add(labelCar);
        labels.add(labelCarSold);
        statusBody = new JLabel("0" + "/" + maxBody);
        statusEngine = new JLabel("0" + "/" + maxEngine);
        statusAccessory = new JLabel("0" + "/" + maxAccessory);
        status.add(statusBody);
        status.add(statusEngine);
        status.add(statusAccessory);
        for (int i = 0; i < workerAmount; i++) {
            statusWorkers[i] = new JLabel("Sleeping... ");
            status.add(statusWorkers[i]);
        }
        statusCar = new JLabel("0" + "/" + maxCar);
        statusCarSold = new JLabel("0");
        status.add(statusCar);
        status.add(statusCarSold);
        overallFrame.setSize(600, 600);
        overallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        overallFrame.add(labels);
        overallFrame.add(status);
        overallFrame.add(progress);
        overallFrame.setVisible(true);
    }
    public Graphics(int workerAmount, int maxBody, int maxEngine, int maxAccessory, int maxCar) {
        createFactoryWindow(workerAmount, maxBody, maxEngine, maxAccessory, maxCar);
    }
}
