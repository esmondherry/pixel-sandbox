package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Air extends Particle {

    public Air() {
        super(ParticleType.AIR, Color.TRANSPARENT, 0);
    }

    @Override
    public void update(int x, int y, Field field) {

    }

}
