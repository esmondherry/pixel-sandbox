package com.esmo.model;

import javafx.scene.paint.Color;

public abstract class Particle {
    public enum ParticleType {
        Sand, Water, Rock, None
    }

    private Color color;
    protected int x;
    protected int y;
    public boolean exists;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void logic(Particle[][] grid, Particle[][] tmpgrid, double windStrength,
            double windDirection);

    protected boolean fallDown(Particle[][] grid, Particle[][] tmpgrid) {
        if (y < grid[0].length - 1 && tmpgrid[x][y]!=null == true && tmpgrid[x][y + 1]!=null == false
                && grid[x][y + 1]!=null == false) {
            grid[x][y + 1] = grid[x][y];
            grid[x][y] = null;
            y++;
            return true;
        }
        return false;
    }

    protected boolean fallLeft(Particle[][] grid, Particle[][] tmpgrid) {
        if (y < grid[0].length - 1 && x != 0 && tmpgrid[x][y]!=null == true && tmpgrid[x - 1][y + 1]!=null == false
                && grid[x - 1][y + 1]!=null == false) {
            grid[x - 1][y + 1] = grid[x][y];
            grid[x][y] = null;
            x--;
            y++;
            return true;
        }
        return false;
    }

    protected boolean fallRight(Particle[][] grid, Particle[][] tmpgrid) {
        if (y < grid[0].length - 1 && x != grid.length - 1 && tmpgrid[x][y]!=null == true
                && tmpgrid[x + 1][y + 1]!=null == false
                && grid[x + 1][y + 1]!=null == false) {
            grid[x + 1][y + 1] = grid[x][y];
            grid[x][y] = null;
            x++;
            y++;
            return true;
        }
        return false;
    }

    protected boolean moveLeft(Particle[][] grid, Particle[][] tmpgrid) {
        if (x != 0 && tmpgrid[x][y]!=null == true && tmpgrid[x - 1][y]!=null == false
                && grid[x - 1][y]!=null == false) {
            grid[x - 1][y] = grid[x][y];
            grid[x][y] = null;
            x--;
            return true;
        }
        return false;
    }

    protected boolean moveRight(Particle[][] grid, Particle[][] tmpgrid) {
        if (x != grid.length - 1 && tmpgrid[x][y]!=null == true && tmpgrid[x + 1][y]!=null == false
                && grid[x + 1][y]!=null == false) {
            grid[x + 1][y] = grid[x][y];
            grid[x][y] = null;
            x++;
            return true;
        }
        return false;
    }
}