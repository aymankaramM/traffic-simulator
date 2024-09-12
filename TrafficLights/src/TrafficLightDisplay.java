/*
 * Ayman Karam
 * Description: This file contains the TrafficLightDisplay class, which manages the state and timing
 * of a traffic light in the simulation. It runs as a separate thread to update the light's state.
 */

import javax.swing.SwingUtilities;

public class TrafficLightDisplay implements Runnable {
    private TrafficLightPanel trafficLightPanel;
    private String[] states = {"RED", "GREEN", "YELLOW"};
    private int currentState = 0;
    private int delay;
    private TrafficSimulationUI ui;

    public TrafficLightDisplay(TrafficLightPanel trafficLightPanel, int delay, TrafficSimulationUI ui) {
        this.trafficLightPanel = trafficLightPanel;
        this.delay = delay;
        this.ui = ui;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (trafficLightPanel) {
                    while (ui.isPaused()) {
                        trafficLightPanel.wait();
                    }
                }
                Thread.sleep(delay);
                currentState = (currentState + 1) % states.length;
                String state = states[currentState];
                SwingUtilities.invokeLater(() -> {
                    trafficLightPanel.setLightState(state);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}