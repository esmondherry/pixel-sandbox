package com.esmo;

import java.util.Random;

import com.esmo.particle.Particle;
import com.esmo.particle.Smoke;
import com.esmo.particle.Steam;

public class Field {
    private Particle[][] grid;
    private int width;
    private int height;
    private Random random;
    private double wind = 0;

    public Field(int width, int height) {
        grid = new Particle[height][width];
        this.height = height;
        this.width = width;
        random = new Random();
    }

    public void update() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Particle p = grid[y][x];
                if (p != null) {
                    p.setHasMoved(false);
                }
            }
        }

        for (int y = height - 1; y >= 0; y--) {
            if (random.nextBoolean()) {
                for (int x = 0; x < width; x++) {
                    applyPhysics(x, y);
                }
            } else {
                for (int x = width - 1; x >= 0; x--) {
                    applyPhysics(x, y);
                }
            }
        }
    }

    public void addParticle(int x, int y, Particle particle) {
        if (inBounds(x, y) && grid[y][x] == null) {
            grid[y][x] = particle;
        }
    }

    public void replaceParticle(int x, int y, Particle particle) {
        if (inBounds(x, y)) {
            grid[y][x] = particle;
        }
    }

    public void removeParticle(int x, int y) {
        if (inBounds(x, y)) {
            grid[y][x] = null;
        }
    }

    public void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = null;
            }
        }
    }

    public int getParticleCount() {
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public Particle[][] getGrid() {
        return grid;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    private void applyPhysics(int x, int y) {
        Particle particle = grid[y][x];
        if (particle == null || particle.hasMoved() || particle.getType() == ParticleType.AIR) {
            return;
        }

        particle.update(x, y, this);

    }

    public boolean decay(int x, int y, Particle replacement) {
        Particle particle = grid[y][x];

        particle.decrementTTL();
        if (particle.getTTL() == 0) {
            grid[y][x] = replacement;
            return true;
        }
        return false;
    }

    public boolean decay(int x, int y) {
        Particle particle = grid[y][x];

        particle.decrementTTL();
        if (particle.getTTL() == 0) {
            grid[y][x] = null;
            return true;
        }
        return false;
    }

    public boolean tryWind(int x, int y, ParticleType type) {
        double windChance = Math.abs(wind) * (1.0 / getParticle(x, y).getDensity());
        if (random.nextDouble() < windChance) {
            int direction = (int) Math.signum(wind);
            tryMove(x, y, x + direction, y);
            return true;
        }
        return false;
    }

    public boolean tryMove(int fromX, int fromY, int toX, int toY) {
        if (!inBounds(toX, toY) || grid[toY][toX] != null) {
            return false;
        }

        Particle particle = grid[fromY][fromX];
        grid[toY][toX] = particle;
        grid[fromY][fromX] = null;

        particle.setHasMoved(true);
        return true;
    }

    public boolean trySwapIfHeavier(int fromX, int fromY, int toX, int toY) {
        if (!inBounds(toX, toY)) {
            return false;
        }

        Particle sourceParticle = grid[fromY][fromX];
        Particle destinationParticle = grid[toY][toX];
        if (tryMove(fromX, fromY, toX, toY)) {
            return true;
        }
        if (destinationParticle != null && !destinationParticle.hasMoved()
                && sourceParticle.getDensity() > destinationParticle.getDensity()) {
            grid[toY][toX] = sourceParticle;
            grid[fromY][fromX] = destinationParticle;

            sourceParticle.setHasMoved(true);
            destinationParticle.setHasMoved(true);

            return true;
        }

        return false;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Particle getParticle(int x, int y) {
        return grid[y][x];
    }

}