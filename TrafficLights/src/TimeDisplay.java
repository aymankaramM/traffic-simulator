/*
 * Ayman Karam
 * Description: This file contains the TimeDisplay class, which is responsible for
 * updating and displaying the elapsed time in the simulation.
 */

import javax.swing.*;
import java.text.DecimalFormat;

public class TimeDisplay implements Runnable {
    private JLabel timeLabel;
    private double elapsedTime;
    private TrafficSimulationUI ui;

    public TimeDisplay(JLabel timeLabel, TrafficSimulationUI ui) {
        this.timeLabel = timeLabel;
        this.elapsedTime = 0.0;
        this.ui = ui;
    }

    @Override
    public void run() {
        DecimalFormat df = new DecimalFormat("#.00");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (timeLabel) {
                    while (ui.isPaused()) {
                        timeLabel.wait();
                    }
                }
                Thread.sleep(100);
                elapsedTime += 0.1;
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText(df.format(elapsedTime) + " seconds");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}