package com.esmo;

import javafx.scene.paint.Color;

public class Particle {
    private Color color;
    private ParticleType type;
    private boolean hasMoved;

    public Particle(ParticleType type, Color color) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
    }

    public Color getColor() {
        return color;
    }

    public ParticleType getType() {
        return type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    public int getDensity() {
        return type.getDensity();
    }

}

enum ParticleType {
    AIR(0),
    SAND(2),
    WATER(1),
    ROCK(10);

    private final int density;

    ParticleType(int density) {
        this.density = density;
    }

    public int getDensity() {
        return density;
    }
}
