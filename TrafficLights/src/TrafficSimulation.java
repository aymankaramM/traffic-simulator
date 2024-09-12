/*
 * Ayman Karm
 * Description: This file contains the main class TrafficSimulation, which serves as the entry point
 * for the traffic simulation application. It initializes the TrafficSimulationUI and starts the simulation.
 */


public class TrafficSimulation {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TrafficSimulationUI ui = new TrafficSimulationUI();
            ui.createAndShowGUI();
        });
    }
}
