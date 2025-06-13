package com.esmo.particle;

import com.esmo.Field;
import com.esmo.ParticleType;

import javafx.scene.paint.Color;

public class Gas extends Particle {

    public Gas(ParticleType type, Color color, int density) {
        super(type, color, density);
    }

    @Override
    public void update(int x, int y, Field field) {
        if (field.tryWind(x, y, field.getParticle(x, y).getType())) {
            return;
        }

        if (field.tryMove(x, y, x, y - 1)) {
            return;
        }

        int maxJump = 50;
        boolean leftGas = true;
        boolean rightGas = true;

        for (int offset = 1; offset <= maxJump; offset++) {
            if (field.inBounds(x - offset, y) && field.getParticle(x - offset, y) == null &&
                    field.inBounds(x - offset, y - 1) && field.getParticle(x - offset, y - 1) == null && leftGas) {
                field.tryMove(x, y, x - offset, y - 1);
                return;
            }
            if (field.inBounds(x + offset, y) && field.getParticle(x + offset, y) == null &&
                    field.inBounds(x + offset, y - 1) && field.getParticle(x + offset, y - 1) == null && rightGas) {
                field.tryMove(x, y, x + offset, y - 1);
                return;
            }
            if (field.inBounds(x - offset, y) && field.getParticle(x - offset, y) != null
                    && !(field.getParticle(x - offset, y) instanceof Gas)) {
                leftGas = false;
            }
            if (field.inBounds(x + offset, y) && field.getParticle(x + offset, y) != null
                    && !(field.getParticle(x + offset, y) instanceof Gas)) {
                rightGas = false;
            }
            if (!leftGas && !rightGas) {
                break;
            }
        }

        if (Math.random() < .5) {
            if (field.tryMove(x, y, x - 1, y - 1))
                return;
            if (field.tryMove(x, y, x + 1, y - 1))
                return;
        } else {
            if (field.tryMove(x, y, x + 1, y - 1))
                return;
            if (field.tryMove(x, y, x - 1, y - 1))
                return;
        }
    }
}
