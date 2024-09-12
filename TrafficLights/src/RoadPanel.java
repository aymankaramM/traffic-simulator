/*
 * Ayman Karam
 * Description: This file contains the RoadPanel class, which extends JPanel and is responsible for
 * rendering the road, traffic lights, and cars in the simulation. It manages the visual representation
 * of the entire simulation environment.
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RoadPanel extends JPanel {
    private int roadLength;
    private List<TrafficLight> trafficLights;
    private List<Car> cars;
    public static final int LANE_HEIGHT = 100;
    public static final int NUM_LANES = 3;

    public RoadPanel(int roadLength, List<TrafficLight> trafficLights, List<Car> cars) {
        this.roadLength = roadLength;
        this.trafficLights = trafficLights;
        this.cars = cars;
        setPreferredSize(new Dimension(roadLength, LANE_HEIGHT * NUM_LANES));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, roadLength, getHeight());
        g.setColor(Color.WHITE);
        for (int i = 1; i < NUM_LANES; i++) {
            int y = i * LANE_HEIGHT;
            for (int x = 0; x < roadLength; x += 50) {
                g.fillRect(x, y - 1, 30, 2);
            }
        }
        for (TrafficLight light : trafficLights) {
            light.draw(g);
        }
        for (Car car : cars) {
            car.draw(g);
        }
    }
}