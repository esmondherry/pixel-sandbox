package com.esmo;

public class WindController {
    private double currentWind = 0.0;
    private double prevWind = 0.0;
    private double targetWind = 0.0;
    private int transitionTicks = 600;
    private int ticksRemaining = 0;
    private int cooldown = 0;

    public void update() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (ticksRemaining > 0) {
            double time = (double) (transitionTicks - ticksRemaining) / transitionTicks;
            currentWind = lerp(prevWind, targetWind, time);
            ticksRemaining--;
        } else {

            prevWind = currentWind;
            targetWind = (Math.random() * 1.4) - 0.7;
            ticksRemaining = transitionTicks;
            cooldown = 300;
        }
    }

    private double lerp(double start, double end, double time) {
        return start + (end - start) * time;
    }

    public double getWind() {
        return currentWind;
    }
}
