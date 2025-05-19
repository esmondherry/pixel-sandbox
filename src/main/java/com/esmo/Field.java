package com.esmo;

import java.util.Random;

import javafx.scene.paint.Color;

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
        if (particle == null || particle.hasMoved()) {
            return;
        }
        switch (particle.getType()) {
            case SAND:
                updateSand(x, y);
                break;
            case WATER:
                updateWater(x, y);
                break;
            case ROCK:
            case AIR:
                break;
            case STEAM:
                updateSteam(x, y);
                break;
            case FIRE:
                updateFire(x, y);
                break;
            case SMOKE:
                updateSmoke(x, y);
                break;

        }
    }

    private void updateSmoke(int x, int y) {
        if (decay(x, y, ParticleType.AIR, null)) {
            return;
        }
        moveGas(x, y);
    }

    private void updateFire(int x, int y) {
        if (decay(x, y, ParticleType.SMOKE, Color.GREY)) {
            return;
        }
        if (random.nextDouble() < .01) {
            addParticle(x - 1, y, new Particle(ParticleType.SMOKE, Color.GREY));

        } else if (random.nextDouble() < .02) {

            addParticle(x, y - 1, new Particle(ParticleType.SMOKE, Color.GREY));
        } else if (random.nextDouble() < .03) {

            addParticle(x + 1, y, new Particle(ParticleType.SMOKE, Color.GREY));
        }

    }

    private void updateSand(int x, int y) {
        if (tryWind(x, y, ParticleType.SAND)) {
            return;
        }

        if (trySwapIfHeavier(x, y, x, y + 1)) {
            return;
        }

        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                if (trySwapIfHeavier(x, y, x - 1, y + 1)) {
                    return;
                }
                if (trySwapIfHeavier(x, y, x + 1, y + 1)) {
                    return;
                }
            } else {
                if (trySwapIfHeavier(x, y, x + 1, y + 1)) {
                    return;
                }
                if (trySwapIfHeavier(x, y, x - 1, y + 1)) {
                    return;
                }
            }
        }
    }

    private void updateWater(int x, int y) {
        if (tryWind(x, y, ParticleType.WATER)) {
            return;
        }

        if (trySwapIfHeavier(x, y, x, y + 1)) {
            return;
        }

        int maxJump = 10;
        boolean leftWater = true;
        boolean rightWater = true;
        for (int offset = 1; offset <= maxJump; offset++) {
            if (inBounds(x - offset, y) && grid[y][x - offset] == null &&
                    inBounds(x - offset, y + 1) && grid[y + 1][x - offset] == null && leftWater) {
                trySwapIfHeavier(x, y, x - offset, y
                        + 1);
                return;
            }
            if (inBounds(x + offset, y) && grid[y][x + offset] == null &&
                    inBounds(x + offset, y + 1) && grid[y + 1][x + offset] == null && rightWater) {
                trySwapIfHeavier(x, y, x + offset, y + 1);
                return;
            }
            if (inBounds(x - offset, y) && grid[y][x - offset] != null
                    && grid[y][x - offset].getType() != ParticleType.WATER) {
                leftWater = false;
            }
            if (inBounds(x + offset, y) && grid[y][x + offset] != null
                    && grid[y][x + offset].getType() != ParticleType.WATER) {
                rightWater = false;
            }
            if (!rightWater && !leftWater) {
                break;
            }
        }

        if (random.nextBoolean()) {
            if (tryMove(x, y, x - 1, y))
                return;
            if (tryMove(x, y, x + 1, y))
                return;
        } else {
            if (tryMove(x, y, x + 1, y))
                return;
            if (tryMove(x, y, x - 1, y))
                return;
        }

    }

    private void updateSteam(int x, int y) {
        if (decay(x, y, ParticleType.WATER, Color.BLUE)) {
            return;
        }
        moveGas(x, y);
    }

    private boolean decay(int x, int y, ParticleType particleType, Color color) {
        grid[y][x].decrementTTL();
        if (grid[y][x].getTTL() == 0) {
            if (particleType == null || particleType == ParticleType.AIR) {
                grid[y][x] = null;
            } else {
                grid[y][x] = new Particle(particleType, color);
                grid[y][x].setTTL(600 + new Random().nextInt(600));

            }
            return true;
        }
        return false;
    }

    private boolean moveGas(int x, int y) {
        if (tryWind(x, y, grid[y][x].getType())) {
            return true;
        }

        if (tryMove(x, y, x, y - 1))
            return true;

        if (random.nextBoolean()) {
            if (tryMove(x, y, x - 1, y - 1))
                return true;
            if (tryMove(x, y, x + 1, y - 1))
                return true;
        } else {
            if (tryMove(x, y, x + 1, y - 1))
                return true;
            if (tryMove(x, y, x - 1, y - 1))
                return true;
        }
        return false;
    }

    private boolean tryWind(int x, int y, ParticleType type) {
        double windChance = Math.abs(wind) * (1.0 / type.getDensity());
        if (random.nextDouble() < windChance) {
            int direction = (int) Math.signum(wind);
            tryMove(x, y, x + direction, y);
            return true;
        }
        return false;
    }

    private boolean tryMove(int fromX, int fromY, int toX, int toY) {
        if (!inBounds(toX, toY) || grid[toY][toX] != null) {
            return false;
        }

        Particle particle = grid[fromY][fromX];
        grid[toY][toX] = particle;
        grid[fromY][fromX] = null;

        particle.setHasMoved(true);
        return true;
    }

    private boolean trySwapIfHeavier(int fromX, int fromY, int toX, int toY) {
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

    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

}