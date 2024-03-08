package com.esmo.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Grid {
    private Particle grid[][];
    private int width;
    private int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        intGrid();
    }

    public Particle[][] getGrid() {
        return grid;
    }

    private void intGrid() {
        grid = new Particle[width][height];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = new Air(x, y);
            }
        }
    }

    public void addToGrid(int x, int y, Color color, String type) {
        if (x >= 0 && x < (grid.length) && y >= 0 && y < (grid[0].length)) {
            switch (type) {
                case "Sand":
                    grid[x][y] = new Sand(x, y);
                    break;
                case "Water":
                    grid[x][y] = new Water(x, y);
                    break;
            }
            grid[x][y].exists = true;
            grid[x][y].setColor(color);
        }
    }

    public void addToGrid(Point2D position, Color color, String type) {
        int x = (int) position.getX();
        int y = (int) position.getY();
        addToGrid(x, y, color, type);
    }

    public void updateGridSize(int newWidth, int newHeight) {
        if (width == newWidth && height == newHeight) {
            return;
        }

        width = newWidth;
        height = newHeight;

        Particle newGrid[][] = new Particle[newWidth][newHeight];

        for (int x = 0; x < newGrid.length; x++) {
            for (int y = 0; y < newGrid[0].length; y++) {
                try {
                    newGrid[x][y] = grid[x][y];
                } catch (Exception e) {
                    newGrid[x][y] = new Air(x, y);
                }

            }
        }
        this.grid = newGrid;
    }

    public void logic(double windStrength, double windDirection) {

        Particle tmpgrid[][] = new Particle[width][];
        for (int i = 0; i < width; i++) {
            tmpgrid[i] = new Particle[height];
            for (int j = 0; j < height; j++) {
                tmpgrid[i][j] = grid[i][j];
            }
        }
        for (int y = 0; y < height; y++) {
            if (y % 2 == 0) {
                for (int x = 0; x < width; x++) {
                    grid[x][y].logic(grid, tmpgrid, windStrength,
                            windDirection);
                }
            } else {
                for (int x = width - 1; x >= 0; x--) {
                    grid[x][y].logic(grid, tmpgrid, windStrength,
                            windDirection);
                }
            }
        }
    }

    public void clear() {
        intGrid();
    }

    public void remove(int x, int y) {
        grid[x][y] = new Air(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
