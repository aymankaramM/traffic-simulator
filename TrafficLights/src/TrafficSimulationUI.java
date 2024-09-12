/*
 * Ayman Karm
 * Description: This file contains the TrafficSimulationUI class, which is responsible for
 * creating and managing the graphical user interface of the traffic simulation.
 * It handles the initialization of traffic lights and cars, updates their states,
 * and manages the simulation loop and user controls (start, pause, stop).
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TrafficSimulationUI {
    private JFrame frame;
    private JLabel timeLabel;
    private RoadPanel roadPanel;
    private List<TrafficLight> trafficLights;
    private List<Car> cars;

    private Thread simulationThread;

    private volatile boolean isPaused = true;
    private volatile boolean isRunning = false;

    private static final int ROAD_LENGTH = 1000;
    private static final int NUM_TRAFFIC_LIGHTS = 3;
    private static final int NUM_CARS = 3;
    private static final int INITIAL_CAR_SPACING = 200;

    public TrafficSimulationUI() {
        trafficLights = new ArrayList<>();
        cars = new ArrayList<>();
        initializeSimulation();
    }

    private void initializeSimulation() {
        for (int i = 0; i < NUM_TRAFFIC_LIGHTS; i++) {
            int position = (i + 1) * ROAD_LENGTH / (NUM_TRAFFIC_LIGHTS + 1);
            trafficLights.add(new TrafficLight(position));
        }

        for (int i = 0; i < Math.min(NUM_CARS, RoadPanel.NUM_LANES); i++) {
            int initialY = i * RoadPanel.LANE_HEIGHT + (RoadPanel.LANE_HEIGHT - Car.CAR_HEIGHT) / 2;
            cars.add(new Car(i + 1, -INITIAL_CAR_SPACING * (i + 1), initialY));
        }
    }

    public void createAndShowGUI() {
        frame = new JFrame("Traffic Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ROAD_LENGTH + 20, RoadPanel.LANE_HEIGHT * RoadPanel.NUM_LANES + 100);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel timePanel = new JPanel();
        timeLabel = new JLabel("0.00 seconds");
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        timePanel.add(timeLabel);
        mainPanel.add(timePanel, BorderLayout.NORTH);

        roadPanel = new RoadPanel(ROAD_LENGTH, trafficLights, cars);
        mainPanel.add(roadPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton startResumeButton = new JButton("Start/Resume");
        JButton pauseButton = new JButton("Pause");
        JButton stopButton = new JButton("Stop");

        controlPanel.add(startResumeButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        startResumeButton.addActionListener(e -> startResumeSimulation());
        pauseButton.addActionListener(e -> pauseSimulation());
        stopButton.addActionListener(e -> stopSimulation());

        frame.setVisible(true);
    }

    private synchronized void startResumeSimulation() {
        if (!isRunning) {
            isRunning = true;
            isPaused = false;
            
            simulationThread = new Thread(this::runSimulation);
            simulationThread.start();
        } else {
            resumeSimulation();
        }
    }

    private void runSimulation() {
        long startTime = System.currentTimeMillis();
        while (isRunning) {
            if (!isPaused) {
                long currentTime = System.currentTimeMillis();
                double elapsedSeconds = (currentTime - startTime) / 1000.0;
                
                updateTrafficLights();
                updateCars();
                updateTimeDisplay(elapsedSeconds);
                
                roadPanel.repaint();
            }
            try {
                Thread.sleep(50); // 20 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void updateTrafficLights() {
        for (TrafficLight light : trafficLights) {
            light.update();
        }
    }

    private void updateCars() {
        for (Car car : cars) {
            car.move(trafficLights, cars);
            if (car.getPosition() > ROAD_LENGTH) {
                car.setPosition(-Car.CAR_LENGTH);
            }
        }
    }

    private void updateTimeDisplay(double elapsedSeconds) {
        SwingUtilities.invokeLater(() -> {
            timeLabel.setText(String.format("%.2f seconds", elapsedSeconds));
        });
    }

    private synchronized void resumeSimulation() {
        isPaused = false;
        notifyAll();
    }

    private synchronized void pauseSimulation() {
        isPaused = true;
    }

    private synchronized void stopSimulation() {
        isRunning = false;
        isPaused = true;
        if (simulationThread != null) {
            simulationThread.interrupt();
        }
        resetSimulation();
    }

    private void resetSimulation() {
        for (TrafficLight light : trafficLights) {
            light.reset();
        }
        for (int i = 0; i < cars.size(); i++) {
            int initialY = i * RoadPanel.LANE_HEIGHT + (RoadPanel.LANE_HEIGHT - Car.CAR_HEIGHT) / 2;
            cars.get(i).setPosition(-INITIAL_CAR_SPACING * (i + 1));
            cars.get(i).setYPosition(initialY);
        }
        timeLabel.setText("0.00 seconds");
        roadPanel.repaint();
    }

    public synchronized boolean isPaused() {
        return isPaused;
    }
}