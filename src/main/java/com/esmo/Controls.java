package com.esmo;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Controls extends VBox {
    private double dragOffsetX;
    private double dragOffsetY;

    public Controls(AppState state, Field field) {
        super(8);
        this.setMaxWidth(156);
        this.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-padding: 8px;");

        this.setOnMousePressed(e -> {
            dragOffsetX = e.getSceneX() - getTranslateX();
            dragOffsetY = e.getSceneY() - getTranslateY();
        });

        this.setOnMouseDragged(e -> {
            setTranslateX(e.getSceneX() - dragOffsetX);
            setTranslateY(e.getSceneY() - dragOffsetY);
        });

        this.getChildren().add(
                new Label("Tool:"));

        Button sandButton = new Button("Sand");
        sandButton.setOnAction(e -> {
            state.setType(ParticleType.SAND);
            state.setColor(Color.WHEAT);
        });

        Button waterButton = new Button("Water");
        waterButton.setOnAction(e -> {
            state.setType(ParticleType.WATER);
            state.setColor(null);
        });

        Button rockButton = new Button("Rock");
        rockButton.setOnAction(e -> {
            state.setType(ParticleType.ROCK);
            state.setColor(null);
        });

        Button steamButton = new Button("Steam");
        steamButton.setOnAction(e -> {
            state.setType(ParticleType.STEAM);
            state.setColor(null);
        });

        Button smokeButton = new Button("Smoke");
        smokeButton.setOnAction(e -> {
            state.setType(ParticleType.SMOKE);
            state.setColor(null);
        });

        Button eraseButton = new Button("Air");
        eraseButton.setOnAction(e -> {
            state.setType(ParticleType.AIR);
            state.setColor(Color.TRANSPARENT);
        });
        this.getChildren()
                .add(new FlowPane(4, 4, sandButton, waterButton, rockButton, steamButton, smokeButton, eraseButton));

        Slider brushSlider = new Slider(1, 50, state.getBrushSize());
        brushSlider.setShowTickLabels(true);
        brushSlider.setShowTickMarks(true);
        brushSlider.setMajorTickUnit(10);
        brushSlider.setSnapToTicks(true);

        brushSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            state.setBrushSize(newVal.intValue());
        });
        this.getChildren().addAll(new Label("Brush Size:"), brushSlider);

        this.getChildren().add(new Label("Wind:"));

        Slider windSlider = new Slider(-1.0, 1.0, 0.0);
        windSlider.setShowTickLabels(true);
        windSlider.setShowTickMarks(true);
        windSlider.setMajorTickUnit(0.5);
        windSlider.setBlockIncrement(0.1);
        windSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!state.isAutoWind()) {
                field.setWind(newVal.doubleValue());
            }
        });
        this.getChildren().add(windSlider);

        CheckBox autoWindCheckbox = new CheckBox("Auto Wind");
        autoWindCheckbox.setSelected(false);
        autoWindCheckbox.setOnAction(e -> {
            state.setAutoWind(autoWindCheckbox.isSelected());
            windSlider.setDisable(state.isAutoWind());
        });
        this.getChildren().add(autoWindCheckbox);

        CheckBox replaceCheckBox = new CheckBox("Replace");
        replaceCheckBox.setSelected(state.isReplaceParticle());
        replaceCheckBox.setOnAction(e -> {
            state.setReplaceParticle(replaceCheckBox.isSelected());
        });
        this.getChildren().add(replaceCheckBox);

        // TODO set defaults
        sandButton.fire();
    }
}
