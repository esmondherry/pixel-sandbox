package com.esmo;

import com.esmo.model.Grid;
import com.esmo.model.Particle;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ThePane extends Pane {

    private Point2D mousePos;
    private boolean mousePressed;
    private double unit;
    private Grid grid;

    private Canvas canvas;
    private GraphicsContext gc;

    public ThePane(Grid grid, double unit) {
        this.grid = grid;
        this.unit = unit;
        this.canvas = new Canvas();
        this.gc = canvas.getGraphicsContext2D();
        this.mousePos = new Point2D(0, 0);
        this.mousePressed = false;

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
