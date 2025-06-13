package com.esmo.particle;

import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Water extends Liquid {

    public Water() {
        super(ParticleType.WATER, Color.BLUE, 2, 10);
    }

}
