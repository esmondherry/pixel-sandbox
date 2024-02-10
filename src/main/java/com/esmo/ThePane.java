package com.esmo;

import com.esmo.model.Grid;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ThePane extends Pane {

    private Point2D mousePos;
    private boolean mousePressed;

    public Rectangle sGrid[][];

    private double unit;
    private double height;
    private double width;

    private Grid grid;

    public ThePane(Grid grid, int unit) {
        this.grid = grid;
        this.width = grid.getWidth();
        this.height = grid.getHeight();
        this.unit = unit;

        setStyle("-fx-background-color: #404040;");
        setPrefHeight(unit * height);
        setPrefWidth(unit * width);
        minHeight(unit);
        minWidth(unit);

        setOnMouseMoved(e -> logPos(e));
        setOnMouseDragged(e -> logPos(e));

        widthProperty().addListener((obs, oldWidth, newWidth) -> {
            updateGridSize();
        });
        heightProperty().addListener((obs, oldHeight, newHeight) -> {
            updateGridSize();
        });

        mousePos = new Point2D(0, 0);
        setOnMousePressed(e -> {
            mousePressed = true;
            logPos(e);
        });
        setOnMouseReleased(e -> {
            mousePressed = false;
        });

        initSGrid();

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

    public void initSGrid() {
        sGrid = new Rectangle[(int) width][(int) height];

        getChildren().clear();
        for (int x = 0; x < sGrid.length; x++) {
            for (int y = 0; y < sGrid[0].length; y++) {
                Rectangle rect = new Rectangle(unit, unit);
                rect.setVisible(false);
                rect.setLayoutX(x * unit);
                rect.setLayoutY(y * unit);
                getChildren()
                        .add(rect);

                sGrid[x][y] = rect;
            }
        }
    }

    public void updateSGridSize(double newHeight, double newWidth) {
        this.height = newHeight;
        this.width = newWidth;
        initSGrid();
    }

    public void drawGrid() {
        for (int x = 0; x < grid.getGrid().length; x++) {
            for (int y = 0; y < grid.getGrid()[0].length; y++) {
                if (grid.getGrid()[x][y].exists) {
                    sGrid[x][y].setFill(grid.getGrid()[x][y].getColor());
                    sGrid[x][y].setVisible(true);
                } else {
                    sGrid[x][y].setVisible(false);
                }
            }
        }
    }

    private void logPos(MouseEvent e) {
        if (!mousePressed) {
            return;
        }
        double x = e.getX();
        double y = e.getY();

        mousePos = new Point2D(x, y);
    }

    private void updateGridSize() {
        int newWidth = (int) (getWidth() / unit);
        int newHeight = (int) (getHeight() / unit);

        if (width == newWidth && height == newHeight) {
            return;
        }

        grid.updateGridSize(newWidth, newHeight);
        updateSGridSize(newHeight, newWidth);

        height = newHeight;
        width = newWidth;
    }

}