package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Solid extends Particle {

    public Solid(ParticleType type, Color color, int density) {
        super(type, color, density);
    }

    @Override
    public void update(int x, int y, Field field) {

    }

}
