package com.esmo;

import java.util.ArrayList;
import java.util.Random;

import com.esmo.model.Grid;
import com.esmo.model.Particle;
import com.esmo.model.Particle.ParticleType;
import com.esmo.utils.Palette;
import com.esmo.view.Options;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ThePane extends Pane {

    private Point2D mousePos;
    private boolean mousePressed;
    private double unit;
    private Grid grid;
    private boolean reset;
    private Options options;

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    private Canvas canvas;
    private GraphicsContext gc;
    protected boolean auto;
    private double autoSpeed;

    public void setAutoSpeed(double autoSpeed) {
        if (autoSpeed < 0) {
            autoSpeed = 0;
        }
        this.autoSpeed = autoSpeed;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public ThePane(Grid grid, double unit, Options options) {
        this.grid = grid;
        this.unit = unit;
        this.canvas = new Canvas();
        this.gc = canvas.getGraphicsContext2D();
        this.mousePos = new Point2D(0, 0);
        this.mousePressed = false;
        this.options = options;
        this.autoSpeed = 1;

        setStyle("-fx-background-color: #404040;");
        setPrefWidth(grid.getWidth() * unit);
        setPrefHeight(grid.getHeight() * unit);
        setMinHeight(unit);

        widthProperty().addListener((obs, oldWidth, newWidth) -> updateCanvasSize());
        heightProperty().addListener((obs, oldHeight, newHeight) -> updateCanvasSize());

        setOnMousePressed(e -> handleMousePressed(e));
        setOnMouseReleased(e -> handleMouseReleased(e));
        setOnMouseDragged(e -> logPosition(e));
        setOnMouseMoved(e -> logPosition(e));

        getChildren().add(canvas);
        updateCanvasSize();

        animationTimer().start();
    }

    private AnimationTimer animationTimer() {
        return new AnimationTimer() {
            private int resetCount = 0;
            private Color color;
            private Random random = new Random();
            private ArrayList<Color> colorList = Palette.getColorList()
                    .get(random.nextInt(Palette.getColorList().size()));

            @Override
            public void handle(long now) {
                calcFrames(now);
                if (reset) {
                    reset = reset();
                } else {
                    handleInput();
                }
                grid.logic(options.getWindStrength().getValue(),
                        options.getWindDirection().getValue());

                drawGrid();

            }

            private boolean reset() {
                if (resetCount > 60 * 7) {
                    grid.clear();
                    resetCount = 0;
                    return false;
                }
                int existing = 0;
                for (int i = 0; i < grid.getWidth(); i++) {
                    for (int j = 0; j < grid.getHeight(); j++) {
                        if (grid.getGrid()[i][j].exists) {
                            existing++;
                            if (Math.random() < .02) {
                                grid.remove(i, j);
                            }
                        }
                    }
                }
                if (existing > (0)) {
                    resetCount++;
                    return true;
                } else {
                    resetCount = 0;
                    return false;
                }
            }

            private void handleInput() {
                if (auto) {
                    auto();
                }
                if (isMousePressed()) {
                    addToGrid(getMousePos(), options.getSandColor(), options.getParticleType());
                }
            }

            private void auto() {
                color = colorList.get(random.nextInt(colorList.size()));
                if (autoSpeed < 1) {
                    int speed = (int) (1 / autoSpeed);
                    if (frameCount % speed == 0) {
                        grid.addToGrid(random.nextInt(grid.getWidth()), 0, color, ParticleType.Sand);
                    }
                } else {
                    for (int i = 0; i < autoSpeed; i++) {
                        grid.addToGrid(random.nextInt(grid.getWidth()), 0, color, ParticleType.Sand);
                    }

                }

                int count = 0;
                for (int i = 0; i < grid.getGrid().length; i++) {
                    if (grid.getGrid()[i][0].exists == true) {
                        count++;
                        if (count > grid.getWidth() / 2) {
                            reset = true;
                            colorList = Palette.getColorList().get(random.nextInt(Palette.getColorList().size()));
                        }
                    }
                }

            }

            private void addToGrid(Point2D position, Color color, ParticleType type) {
                double x = position.getX() - (position.getX() % getUnit());
                double y = position.getY() - (position.getY() % getUnit());

                if (x >= 0 && x < (grid.getWidth() * getUnit()) && y >= 0
                        && y < (grid.getHeight() * getUnit())) {
                    int gridX = (int) (x / getUnit());
                    int gridY = (int) (y / getUnit());
                    grid.addToGrid(gridX, gridY, color, type);
                }
            }

            private long lastTime = 0;
            private int frameCount = 0;

            private void calcFrames(long now) {
                frameCount++;
                if (now - lastTime >= App.ONE_SECOND) {
                    frameCount = 0;
                    lastTime = now;
                }
            }

        };
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public Point2D getMousePos() {
        return mousePos;
    }

    public void drawGrid() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Particle[][] particleGrid = grid.getGrid();
        for (int x = 0; x < particleGrid.length; x++) {
            for (int y = 0; y < particleGrid[0].length; y++) {
                if (particleGrid[x][y].exists) {
                    gc.setFill(particleGrid[x][y].getColor());
                    gc.fillRect(x * unit, y * unit, unit, unit);
                }
            }
        }
    }

    private void handleMousePressed(MouseEvent e) {
        mousePressed = true;
        logPosition(e);
    }

    private void handleMouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    private void logPosition(MouseEvent e) {
        if (mousePressed) {
            double x = e.getX();
            double y = e.getY();
            mousePos = new Point2D(x, y);
        }
    }

    private void updateCanvasSize() {
        double newWidth = getWidth();
        double newHeight = getHeight();
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
        grid.updateGridSize((int) (newWidth / unit), (int) (newHeight / unit));
    }
}
