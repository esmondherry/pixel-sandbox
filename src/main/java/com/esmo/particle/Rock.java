package com.esmo.particle;

import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Rock extends Solid {
    public Rock() {
        super(ParticleType.ROCK, Color.BROWN, 100);
    }
}
