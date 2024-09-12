/*
 * Ayman Karm
 * Description: This file contains the TrafficLightPanel class, which extends JPanel and is responsible
 * for rendering a traffic light in the simulation.
 */

import javax.swing.*;
import java.awt.*;

public class TrafficLightPanel extends JPanel {
    private int intersectionId;
    private Color lightColor;
    private String state;
    public static final int LIGHT_POSITION = 150;

    public TrafficLightPanel(int intersectionId) {
        this.intersectionId = intersectionId;
        this.state = "RED";
        this.lightColor = Color.RED;
        setPreferredSize(new Dimension(200, 50));
        setBackground(Color.DARK_GRAY);
    }

    public void setLightState(String state) {
        this.state = state;
        switch (state) {
            case "RED":
                lightColor = Color.RED;
                break;
            case "GREEN":
                lightColor = Color.GREEN;
                break;
            case "YELLOW":
                lightColor = Color.YELLOW;
                break;
        }
        repaint();
    }

    public String getLightState() {
        return state;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw traffic light
        g.setColor(Color.BLACK);
        g.fillRect(LIGHT_POSITION - 10, 5, 20, 40);
        g.setColor(lightColor);
        g.fillOval(LIGHT_POSITION - 5, 10, 10, 10);

        // Draw intersection info
        g.setColor(Color.WHITE);
        g.drawString("Intersection " + intersectionId, 10, 45);
    }

    public int getIntersectionId() {
        return intersectionId;
    }
}