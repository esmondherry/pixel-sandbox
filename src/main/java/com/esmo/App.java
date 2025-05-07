package com.esmo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PIXEL_SIZE = 2;

    private boolean isMousePressed = false;
    private double mouseX, mouseY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindController windController = new WindController();

        AppState state = new AppState();
        Field field = new Field(WIDTH, HEIGHT);
        VBox controls = new Controls(state, field);

        Label windLabel = new Label();
        Label toolLabel = new Label();
        Label brushLabel = new Label();
        Label particleCountLabel = new Label();
        Label frameTimeLabel = new Label();

        VBox hud = new VBox(4, windLabel, toolLabel, brushLabel, particleCountLabel, frameTimeLabel);
        hud.setTranslateX(10);
        hud.setTranslateY(10);
        hud.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 8px;");
        hud.setMouseTransparent(true);

        Canvas canvas = new Canvas(WIDTH * PIXEL_SIZE, HEIGHT * PIXEL_SIZE);
        canvas.setOnMousePressed(e -> isMousePressed = true);
        canvas.setOnMouseReleased(e -> isMousePressed = false);
        canvas.setOnMouseDragged(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });
        canvas.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        Scene scene = new Scene(new Pane(canvas, hud, controls));
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DIGIT1:
                    state.setType(ParticleType.SAND);
                    state.setColor(Color.YELLOW);
                    break;
                case DIGIT2:
                    state.setType(ParticleType.WATER);
                    state.setColor(Color.BLUE);
                    break;
                case DIGIT3:
                    state.setType(ParticleType.ROCK);
                    state.setColor(Color.BROWN);
                    break;
                case DIGIT0:
                    state.setType(ParticleType.AIR);
                    state.setColor(Color.TRANSPARENT);
                    break;
                case OPEN_BRACKET:
                    state.setBrushSize(state.getBrushSize() - 1);
                    break;
                case CLOSE_BRACKET:
                    state.setBrushSize(state.getBrushSize() + 1);
                    break;

                default:
                    break;
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        controls.setTranslateX(canvas.getWidth() - controls.getWidth() - 10);
        controls.setTranslateY(10);

        new AnimationTimer() {
            final long RATE = 16_666_667;
            int frameTimeRefresh = 0;
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long frameStart = System.nanoTime();
                windLabel.setText(formatWind(field.getWind()));
                toolLabel.setText(formatTool(state.getType()));
                brushLabel.setText("Brush Size: " + state.getBrushSize());
                particleCountLabel.setText("Particles: " + field.getParticleCount());

                if (isMousePressed) {
                    int cx = (int) (mouseX / PIXEL_SIZE);
                    int cy = (int) (mouseY / PIXEL_SIZE);
                    int r = state.getBrushSize();

                    for (int dx = -r; dx <= r; dx++) {
                        for (int dy = -r; dy <= r; dy++) {
                            int tx = cx + dx;
                            int ty = cy + dy;

                            if (dx * dx + dy * dy <= r * r) {
                                if (state.getType() == ParticleType.AIR) {
                                    field.removeParticle(tx, ty);
                                } else {
                                    field.addParticle(tx, ty, new Particle(state.getType(), state.getColor()));
                                }
                            }
                        }
                    }
                }

                if (now - lastUpdate >= RATE) {
                    if (state.isAutoWind()) {
                        windController.update();
                        field.setWind(windController.getWind());
                    }

                    field.update();
                    draw(canvas.getGraphicsContext2D(), field);

                    if (frameTimeRefresh > 15) {
                        double frameTimeMs = (System.nanoTime() - frameStart) / 1_000_000.0;
                        frameTimeLabel.setText(String.format("Frame: %.2f ms", frameTimeMs));
                        frameTimeRefresh = 0;
                    }
                    frameTimeRefresh++;
                    lastUpdate = now;
                }
            }

            private String formatWind(double wind) {
                String arrow = wind > 0 ? "Right" : wind < 0 ? "Left" : "None";
                return String.format("Wind: %s (%.2f)", arrow, Math.abs(wind));
            }

            private String formatTool(ParticleType type) {
                return "Tool: " + (type != null ? type.name() : "None");
            }

        }.start();

    }

    private void draw(GraphicsContext gc, Field field) {
        gc.clearRect(0, 0, WIDTH * PIXEL_SIZE, HEIGHT * PIXEL_SIZE);
        Particle[][] grid = field.getGrid();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Particle p = grid[y][x];
                if (p != null) {
                    gc.setFill(p.getColor());
                    gc.fillRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
