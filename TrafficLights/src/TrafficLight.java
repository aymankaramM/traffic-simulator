/*
 * Ayman Karam
 * Description: This file contains the TrafficLight class, which represents a traffic light in the simulation.
 * It manages the light's state (red, yellow, green), handles state changes over time,
 * and provides methods for rendering the traffic light on the simulation panel.
 */

import java.awt.*;

public class TrafficLight {
    private int position;
    private String state;
    private long lastChangeTime;
    private static final int RED_DURATION = 3000; 
    private static final int GREEN_DURATION = 4000; 
    private static final int YELLOW_DURATION = 1000; 
    private static final int LIGHT_WIDTH = 20;
    private static final int LIGHT_HEIGHT = 50;

    public TrafficLight(int position) {
        this.position = position;
        this.state = "RED";
        this.lastChangeTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastChangeTime;

        switch (state) {
            case "RED":
                if (elapsedTime > RED_DURATION) {
                    state = "GREEN";
                    lastChangeTime = currentTime;
                }
                break;
            case "GREEN":
                if (elapsedTime > GREEN_DURATION) {
                    state = "YELLOW";
                    lastChangeTime = currentTime;
                }
                break;
            case "YELLOW":
                if (elapsedTime > YELLOW_DURATION) {
                    state = "RED";
                    lastChangeTime = currentTime;
                }
                break;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(position - LIGHT_WIDTH / 2, 5, LIGHT_WIDTH, LIGHT_HEIGHT);
        Color lightColor;
        switch (state) {
            case "RED":
                lightColor = Color.RED;
                break;
            case "YELLOW":
                lightColor = Color.YELLOW;
                break;
            case "GREEN":
                lightColor = Color.GREEN;
                break;
            default:
                lightColor = Color.GRAY;
        }
        g.setColor(lightColor);
        g.fillOval(position - LIGHT_WIDTH / 2 + 5, 10, LIGHT_WIDTH - 10, LIGHT_WIDTH - 10);
    }

    public int getPosition() {
        return position;
    }

    public String getState() {
        return state;
    }

    public void reset() {
        state = "RED";
        lastChangeTime = System.currentTimeMillis();
    }
}