/*
 * Student Name: Ayman Karam
 * Description: This file contains the Car class, which represents a car in the traffic simulation.
 * It manages the car's position, movement, and interaction with traffic lights and other cars.
 * The class also handles the rendering of the car on the simulation panel.
 */

import java.awt.*;
import java.util.List;

public class Car {
    private int id;
    private int xPosition;
    private int yPosition;
    private int speed;
    private static final int MAX_SPEED = 5;
    public static final int CAR_LENGTH = 40;
    public static final int CAR_HEIGHT = 20;
    private static final int SAFE_DISTANCE = 10;

    public Car(int id, int initialXPosition, int initialYPosition) {
        this.id = id;
        this.xPosition = initialXPosition;
        this.yPosition = initialYPosition;
        this.speed = MAX_SPEED;
    }

    public void move(List<TrafficLight> trafficLights, List<Car> cars) {
        boolean shouldStop = false;
        for (TrafficLight light : trafficLights) {
            if (light.getState().equals("RED") && 
                xPosition < light.getPosition() && 
                xPosition + speed + CAR_LENGTH > light.getPosition()) {
                shouldStop = true;
                break;
            }
        }
        for (Car carAhead : cars) {
            if (carAhead != this && carAhead.yPosition == this.yPosition &&
                carAhead.xPosition > xPosition && 
                carAhead.xPosition < xPosition + speed + CAR_LENGTH + SAFE_DISTANCE) {
                shouldStop = true;
                break;
            }
        }

        if (shouldStop) {
            speed = 0;
        } else {
            speed = Math.min(speed + 1, MAX_SPEED);
        }

        xPosition += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(xPosition, yPosition, CAR_LENGTH, CAR_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String carNumber = String.valueOf(id);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(carNumber);
        int textHeight = fm.getHeight();
        g.drawString(carNumber, 
                     xPosition + (CAR_LENGTH - textWidth) / 2, 
                     yPosition + (CAR_HEIGHT + textHeight) / 2 - fm.getDescent());
        
        // Draw car info
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(getInfo(), xPosition, yPosition + CAR_HEIGHT + 15);
    }

    public int getPosition() {
        return xPosition;
    }

    public void setPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return String.format("Car %d: (%d, %d), %d km/h", id, xPosition, yPosition, speed);
    }
}