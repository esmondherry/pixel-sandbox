package com.esmo;

import javafx.scene.paint.Color;

public class AppState {
    private ParticleType type;
    private Color color;
    private int brushSize;
    private boolean autoWind;

    public AppState() {
        type = ParticleType.SAND;
        color = Color.WHEAT;
        brushSize = 1;
        autoWind = false;
    }

    public boolean isAutoWind() {
        return autoWind;
    }

    public void setAutoWind(boolean autoWind) {
        this.autoWind = autoWind;
    }

    public ParticleType getType() {
        return type;
    }

    public void setType(ParticleType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int size) {
        this.brushSize = Math.max(1, size);
    }
}