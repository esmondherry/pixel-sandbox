package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Steam extends Gas {

    public Steam() {
        super(ParticleType.STEAM, Color.LIGHTGREY, 1);
        setTTL(600 + (int) (Math.random() * 600));

    }

    @Override
    public void update(int x, int y, Field field) {
        if (field.decay(x, y, new Water())) {
            return;
        }
        super.update(x, y, field);
    }

}
