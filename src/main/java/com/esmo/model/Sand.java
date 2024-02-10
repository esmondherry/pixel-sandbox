package com.esmo.model;

public class Sand extends Particle {

    public Sand(int x, int y) {
        super(x, y);
    }

    @Override
    public void logic(Particle[][] grid, Particle[][] tmpgrid, double windStrength,
            double windDirection) {

        if (Math.random() < windStrength) {
            if (Math.random() > windDirection) {
                fallLeft(grid, tmpgrid);
                return;
            } else {
                fallRight(grid, tmpgrid);
                return;
            }
        }
        if (fallDown(grid, tmpgrid))
            return;

        if (Math.random() < .05) {
            if (Math.random() > .5) {
                if (fallLeft(grid, tmpgrid)) {
                    return;
                }
                if (fallRight(grid, tmpgrid)) {
                    return;
                }
            } else {
                if (fallRight(grid, tmpgrid)) {
                    return;
                }
                if (fallLeft(grid, tmpgrid)) {
                    return;
                }
            }

        }
    }

}
