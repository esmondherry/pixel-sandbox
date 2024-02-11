package com.esmo;

import java.util.ArrayList;
import java.util.Random;

import com.esmo.model.Grid;
import com.esmo.utils.Palette;
import com.esmo.view.Hud;
import com.esmo.view.Options;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    static int ONE_SECOND = 1_000_000_000;

    private SimpleIntegerProperty fps = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty visible = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty nodes = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty heightProp = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty widthProp = new SimpleIntegerProperty(0);
    final private int width = 90;
    final private int height = 45;
    final private double unit = 10;
    private Grid grid;

    boolean auto = false;
    boolean reset = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        grid = new Grid(width, height);
        Options options = new Options();

        Hud hud = new Hud();
        hud.addItem("fps", fps);
        hud.addItem("visible", visible);
        hud.addItem("nodes", nodes);
        hud.addItem("h", heightProp);
        hud.addItem("w", widthProp);

        ThePane pane = new ThePane(grid, unit);

        Button optionsButton = new Button("⚙");

        StackPane stackPane = new StackPane();
        StackPane.setAlignment(hud, Pos.TOP_LEFT);
        StackPane.setAlignment(optionsButton, Pos.TOP_RIGHT);
        stackPane.getChildren().addAll(pane, hud, optionsButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(stackPane);

        options.getReset().setOnAction(e -> {
            reset = true;
        });
        options.getAuto().setOnAction(e -> {
            auto = options.getAuto().isSelected();
        });
        options.getOnTop().setOnAction(e -> {
            primaryStage.setAlwaysOnTop(options.getOnTop().isSelected());
        });
        optionsButton.setOnAction(e -> {
            if (borderPane.getRight() == null) {
                borderPane.setRight(options);
            } else {
                borderPane.setRight(null);
            }
        });

        Scene scene = new Scene(borderPane);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    auto = !auto;
                    options.getAuto().setSelected(auto);
                    break;
                case DIGIT1:
                    options.setParticleType("Sand");
                    break;
                case DIGIT2:
                    options.setParticleType("Water");
                    break;
            }
        });
        primaryStage.setTitle("1");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private int frameCount = 0;
            private Color color;
            private Random random = new Random();
            private ArrayList<Color> colorList = Palette.getColorList()
                    .get(random.nextInt(Palette.getColorList().size()));
            private int resetCount = 0;

            @Override
            public void handle(long now) {
                calcFrames(now);
                calcVisible();
                calcNodes();
                calcGridSize();

                if (reset) {
                    reset = reset();
                } else {
                    handleInput();
                }
                grid.logic(options.getWindStrength().getValue(), options.getWindDirection().getValue());

                pane.drawGrid();
            }

            private void calcFrames(long now) {
                frameCount++;
                if (now - lastTime >= ONE_SECOND) {
                    fps.set(frameCount);
                    frameCount = 0;
                    lastTime = now;
                }
            }

            private void calcVisible() {
                int visibleCount = 0;
                Particle[][] particleGrid = grid.getGrid();
                for (int x = 0; x < particleGrid.length; x++) {
                    for (int y = 0; y < particleGrid[0].length; y++) {
                        if (particleGrid[x][y].exists) {
                            visibleCount++;
                        }
                    }
                }
                visible.set(visibleCount);
            }

            private void calcNodes() {
                nodes.set((int) (grid.getHeight() * grid.getWidth()));
            }

            private void calcGridSize() {
                widthProp.set(grid.getGrid().length);
                heightProp.set(grid.getGrid()[0].length);
            }

            private boolean reset() {
                if (resetCount > 60 * 7) {
                    grid.clear();
                    resetCount = 0;
                    return false;
                }
                int existing = 0;
                for (int i = 0; i < grid.getWidth(); i++) {
                    for (int j = 0; j < grid.getHeight(); j++) {
                        if (grid.getGrid()[i][j].exists) {
                            existing++;
                            if (Math.random() < .02) {
                                grid.remove(i, j);
                            }
                        }
                    }
                }
                if (existing > (0)) {
                    resetCount++;
                    return true;
                } else {
                    resetCount = 0;
                    return false;
                }
            }

            private void handleInput() {
                if (auto) {
                    auto();
                }
                if (pane.isMousePressed()) {
                    addToGrid(pane.getMousePos(), options.getSandColor(), options.getParticleType());
                }
            }

            private void auto() {
                color = colorList.get(random.nextInt(colorList.size()));
                grid.addToGrid((int) (Math.random() * grid.getWidth()), 0, color, "Sand");

                int count = 0;
                for (int i = 0; i < grid.getGrid().length; i++) {
                    if (grid.getGrid()[i][0].exists == true) {
                        count++;
                        if (count > grid.getWidth() / (grid.getWidth() / 2)) {
                            reset = true;
                            colorList = Palette.getColorList().get(random.nextInt(Palette.getColorList().size()));
                        }
                    }
                }

            }

            private void addToGrid(Point2D position, Color color, String type) {
                double x = position.getX() - (position.getX() % pane.getUnit());
                double y = position.getY() - (position.getY() % pane.getUnit());

                if (x >= 0 && x < (grid.getWidth() * pane.getUnit()) && y >= 0 && y < (grid.getHeight() * pane.getUnit())) {
                    int gridX = (int) (x / pane.getUnit());
                    int gridY = (int) (y / pane.getUnit());
                    grid.addToGrid(gridX, gridY, color, type);
                }
            }

        };
        animationTimer.start();

    }

}