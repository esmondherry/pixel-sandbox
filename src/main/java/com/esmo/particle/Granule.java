package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Granule extends Particle {

    public Granule(ParticleType type, Color color, int density) {
        super(type, color, density);
    }

    @Override
    public void update(int x, int y, Field field) {
        if (field.tryWind(x, y, getType())) {
            return;
        }

        if (field.trySwapIfHeavier(x, y, x, y + 1)) {
            return;
        }

        if (Math.random() < .5) {
            if (Math.random() < .5) {
                if (field.trySwapIfHeavier(x, y, x - 1, y + 1)) {
                    return;
                }
                if (field.trySwapIfHeavier(x, y, x + 1, y + 1)) {
                    return;
                }
            } else {
                if (field.trySwapIfHeavier(x, y, x + 1, y + 1)) {
                    return;
                }
                if (field.trySwapIfHeavier(x, y, x - 1, y + 1)) {
                    return;
                }
            }
        }
    }

}
