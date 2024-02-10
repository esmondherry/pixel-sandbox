package com.esmo.model;

public class Water extends Particle {

    public Water(int x, int y) {
        super(x, y);
    }

    @Override
    public void logic(Particle[][] grid, Particle[][] tmpgrid, double windStrength,
            double windDirection) {
        if (fallDown(grid, tmpgrid)) {
            return;
        }

        if (Math.random() < .05) {
            if (Math.random() < .5) {
                if (warpDownLeft(grid, tmpgrid)) {
                    return;
                }
                if (warpDownRight(grid, tmpgrid)) {
                    return;
                }
            } else {
                if (warpDownRight(grid, tmpgrid)) {
                    return;
                }
                if (warpDownLeft(grid, tmpgrid)) {
                    return;
                }
            }
        }
    }

    public boolean warpDownLeft(Particle[][] grid, Particle[][] tmpgrid) {
        for (int i = 1; i <= x; i++) {

            if (y < grid[0].length - 1 && x > 0) {
                if (!(grid[x - i][y + 1] instanceof Water || grid[x - i][y + 1] instanceof Air)) {
                    return false;
                }
                if (tmpgrid[x][y].exists == true && tmpgrid[x - i][y + 1].exists == false
                        && grid[x - i][y + 1].exists == false) {
                    grid[x - i][y + 1] = grid[x][y];
                    grid[x][y] = new Air(x, y);
                    x-=i;
                    y++;
                    return true;
                }
            }

        }
        return false;
    }

    public boolean warpDownRight(Particle[][] grid, Particle[][] tmpgrid) {
        for (int i = 1; i <= grid.length - x; i++) {
            if (y < grid[0].length - 1 && x + i < grid.length) {
                if (!(grid[x + i][y + 1] instanceof Water || grid[x + i][y + 1] instanceof Air)) {
                    return false;
                }
                if (tmpgrid[x][y].exists == true &&
                        tmpgrid[x + i][y + 1].exists == false && grid[x + i][y + 1].exists == false) {
                    grid[x + i][y + 1] = grid[x][y];
                    grid[x][y] = new Air(x, y);
                    x+=i;
                    y++;
                    return true;
                }
            }

        }
        return false;
    }

}
