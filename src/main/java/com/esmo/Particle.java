package com.esmo;

import javafx.scene.paint.Color;

public class Particle {
    private Color color;
    private ParticleType type;
    private boolean hasMoved;
    private int ttl;

    public Particle(ParticleType type, Color color) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
        this.ttl = -1;
    }

    public int getTTL() {
        return ttl;
    }

    public void setTTL(int ttl) {
        this.ttl = ttl;
    }

    public void decrementTTL() {
        if (ttl > 0)
            ttl--;
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
    AIR(1),
    SAND(3),
    WATER(2),
    ROCK(10),
    STEAM(1),
    FIRE(1),
    SMOKE(1),
    ;

    private final int density;

    ParticleType(int density) {
        this.density = density;
    }

    public int getDensity() {
        return density;
    }
}
