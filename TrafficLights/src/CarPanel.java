/*
 * Student Name: Ayman Karam
 * Description: This file contains the CarPanel class, which extends JPanel and is responsible
 * for rendering a car in the simulation.
 */


import javax.swing.*;
import java.awt.*;

public class CarPanel extends JPanel {
    private int carId;
    private int xPosition;
    private int speed;
    private TrafficLightPanel trafficLightPanel;
    private static final int CAR_WIDTH = 10;
    private static final int CAR_HEIGHT = 20;
    private static final Color[] CAR_COLORS = {Color.BLUE, Color.GREEN, Color.ORANGE};

    public CarPanel(int carId, TrafficLightPanel trafficLightPanel) {
        this.carId = carId;
        this.xPosition = 0;
        this.speed = 0;
        this.trafficLightPanel = trafficLightPanel;
        setPreferredSize(new Dimension(200, 50));
        setBackground(Color.LIGHT_GRAY);
    }

    public void updateCarStatus(int xPosition, int speed) {
        this.xPosition = xPosition;
        this.speed = speed;
        repaint();
    }

    public TrafficLightPanel getTrafficLightPanel() {
        return trafficLightPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw road
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw lane markings
        g.setColor(Color.WHITE);
        for (int i = 0; i < getWidth(); i += 20) {
            g.fillRect(i, getHeight() / 2 - 1, 10, 2);
        }

        // Draw car
        g.setColor(CAR_COLORS[carId % CAR_COLORS.length]);
        g.fillRect(xPosition, getHeight() - CAR_HEIGHT - 5, CAR_WIDTH, CAR_HEIGHT);

        // Draw car info
        g.setColor(Color.WHITE);
        g.drawString("Car " + (carId + 1) + ": " + xPosition + " m, " + speed + " km/h", 10, 15);
        
        // Draw a line representing the traffic light position
        g.setColor(Color.YELLOW);
        g.drawLine(TrafficLightPanel.LIGHT_POSITION, 0, TrafficLightPanel.LIGHT_POSITION, getHeight());
    }

    public int getCarId() {
        return carId;
    }
}