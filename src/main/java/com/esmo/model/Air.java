package com.esmo.model;

public class Air extends Particle {

    public Air(int x, int y) {
        super(x, y);
    }

    @Override
    public void logic(Particle[][] grid, Particle[][] tmpgrid,  double windStrength,
            double windDirection) {
    }

}
