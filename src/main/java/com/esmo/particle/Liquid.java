package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Liquid extends Particle {

    private int flow;

    public Liquid(ParticleType type, Color color, int density, int flow) {
        super(type, color, density);
        this.flow = flow;
    }

    @Override
    public void update(int x, int y, Field field) {
        if (field.tryWind(x, y, getType())) {
            return;
        }

        if (field.trySwapIfHeavier(x, y, x, y + 1)) {
            return;
        }

        int maxJump = flow;
        boolean leftWater = true;
        boolean rightWater = true;
        for (int offset = 1; offset <= maxJump; offset++) {
            if (field.inBounds(x - offset, y) && field.getParticle(x - offset, y) == null &&
                    field.inBounds(x - offset, y + 1) && field.getParticle(x - offset, y + 1) == null && leftWater) {
                field.trySwapIfHeavier(x, y, x - offset, y
                        + 1);
                return;
            }
            if (field.inBounds(x + offset, y) && field.getParticle(x + offset, y) == null &&
                    field.inBounds(x + offset, y + 1) && field.getParticle(x + offset, y + 1) == null && rightWater) {
                field.trySwapIfHeavier(x, y, x + offset, y + 1);
                return;
            }
            if (field.inBounds(x - offset, y) && field.getParticle(x - offset, y) != null
                    && !(field.getParticle(x - offset, y) instanceof Liquid)) {
                leftWater = false;
            }
            if (field.inBounds(x + offset, y) && field.getParticle(x + offset, y) != null
                    && !(field.getParticle(x + offset, y) instanceof Liquid)) {
                rightWater = false;
            }
            if (!rightWater && !leftWater) {
                break;
            }
        }

        if (Math.random() < .5) {
            if (field.tryMove(x, y, x - 1, y))
                return;
            if (field.tryMove(x, y, x + 1, y))
                return;
        } else {
            if (field.tryMove(x, y, x + 1, y))
                return;
            if (field.tryMove(x, y, x - 1, y))
                return;
        }

    }

}
