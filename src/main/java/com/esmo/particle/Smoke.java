package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Smoke extends Gas {

    public Smoke() {
        super(ParticleType.SMOKE, Color.GREY, 1);
        setTTL(600 + (int) (Math.random() * 1200));
    }

    @Override
    public void update(int x, int y, Field field) {
        if (field.decay(x, y)) {
            return;
        }
        super.update(x, y, field);
    }
}
