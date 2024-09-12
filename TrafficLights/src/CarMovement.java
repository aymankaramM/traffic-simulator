/*
 * Student Name: Ayman Karam
 * Description: This file contains the CarMovement class, which is responsible for
 * managing the movement and behavior of a car in the traffic simulation.
 */

import javax.swing.SwingUtilities;

public class CarMovement implements Runnable {
    private CarPanel carPanel;
    private int xPosition;
    private int speed;
    private TrafficLightPanel trafficLightPanel;
    private static final int MAX_SPEED = 75; 
    private static final int ACCELERATION = 15; 
    private static final int CAR_LENGTH = 20; 
    private TrafficSimulationUI ui;

    public CarMovement(CarPanel carPanel, int carId, TrafficSimulationUI ui) {
        this.carPanel = carPanel;
        this.xPosition = 0;
        this.speed = MAX_SPEED;
        this.trafficLightPanel = carPanel.getTrafficLightPanel();
        this.ui = ui;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (carPanel) {
                    while (ui.isPaused()) {
                        carPanel.wait();
                    }
                }
                Thread.sleep(75);

                int lightPosition = TrafficLightPanel.LIGHT_POSITION;

                if ("RED".equals(trafficLightPanel.getLightState()) && 
                    xPosition + CAR_LENGTH >= lightPosition && 
                    xPosition < lightPosition) {
                    xPosition = lightPosition - CAR_LENGTH;
                    speed = 0;
                } else {
                    speed = Math.min(MAX_SPEED, speed + ACCELERATION);
                    xPosition += speed / 27; 
                }

                
                if (xPosition > carPanel.getWidth()) {
                    xPosition = -CAR_LENGTH; 
                }

                final int currentPosition = xPosition;
                final int currentSpeed = speed;
                SwingUtilities.invokeLater(() -> {
                    carPanel.updateCarStatus(currentPosition, currentSpeed);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}