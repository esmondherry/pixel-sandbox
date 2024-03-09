package com.esmo;

//45_000@60
import java.util.ArrayList;
import java.util.Random;

import com.esmo.model.Grid;
import com.esmo.model.Particle;
import com.esmo.model.Particle.ParticleType;
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
import javafx.scene.input.MouseEvent;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        grid = new Grid(width, height);
        Options options = new Options();
        options.setMaxWidth(155);
        StackPane.setAlignment(options, Pos.CENTER_RIGHT);

        Hud hud = new Hud();
        hud.addItem("fps", fps);
        hud.addItem("visible", visible);
        hud.addItem("nodes", nodes);
        hud.addItem("h", heightProp);
        hud.addItem("w", widthProp);

        ThePane pane = new ThePane(grid, unit, options);

        Button optionsButton = new Button("⚙");

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(optionsButton);
        borderPane.setCenter(options);

        borderPane.setMaxWidth(180);
        borderPane.setOnMouseEntered(e -> makeSolid(e));
        borderPane.setOnMouseExited(e -> makeTransparent(e));
        StackPane.setAlignment(borderPane, Pos.CENTER_RIGHT);

        StackPane stackPane = new StackPane();
        StackPane.setAlignment(hud, Pos.TOP_LEFT);
        StackPane.setAlignment(optionsButton, Pos.TOP_RIGHT);
        stackPane.getChildren().addAll(pane, hud, optionsButton);

        options.getReset().setOnAction(e -> {
            pane.setReset(true);
        });
        options.getAuto().setOnAction(e -> {
            pane.setAuto(options.getAuto().isSelected());
        });
        options.getOnTop().setOnAction(e -> {
            primaryStage.setAlwaysOnTop(options.getOnTop().isSelected());
        });
        options.getClear().setOnAction(e -> {
            grid.clear();
        });
        optionsButton.setOnAction(event -> {
            if (stackPane.getChildren().contains(borderPane)) {
                stackPane.getChildren().remove(borderPane);
                stackPane.getChildren().add(optionsButton);
                optionsButton.setOnMouseEntered(e -> makeSolid(e));
                optionsButton.setOnMouseExited(e -> makeTransparent(e));
            } else {
                optionsButton.setOnMouseEntered(null);
                optionsButton.setOnMouseExited(null);
                optionsButton.setOpacity(1);
                stackPane.getChildren().remove(optionsButton);
                borderPane.setLeft(optionsButton);
                stackPane.getChildren().add(borderPane);
            }
        });

        Scene scene = new Scene(stackPane);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    boolean auto = options.getAuto().isSelected();
                    auto = !auto;
                    options.getAuto().setSelected(auto);
                    pane.setAuto(auto);
                    break;
                case DIGIT1:
                    options.setParticleType(ParticleType.Sand);
                    break;
                case DIGIT2:
                    options.setParticleType(ParticleType.Water);
                    break;
                default:
                    break;
            }
        });
        primaryStage.setTitle("1");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;
            private int frameCount = 0;

            @Override
            public void handle(long now) {
                calcFrames(now);
                calcVisible();
                calcNodes();
                calcGridSize();
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

        };
        animationTimer.start();

    }

    private void makeTransparent(MouseEvent e) {
        ((Node) e.getSource()).setOpacity(.25);
    }

    private void makeSolid(MouseEvent e) {
        ((Node) e.getSource()).setOpacity(1);
    }

}