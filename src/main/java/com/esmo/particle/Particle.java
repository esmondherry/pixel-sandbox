package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public abstract class Particle {
    private Color color;
    private ParticleType type;
    private boolean hasMoved;
    private int ttl;
    private int density;

    public Particle(ParticleType type, Color color, int density) {
        this.type = type;
        this.color = color;
        this.hasMoved = false;
        this.ttl = -1;
        this.density = density;
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

    public void setColor(Color color) {
        this.color = color;
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
        return density;
    }

    public abstract void update(int x, int y, Field field);

}
