package com.esmo.view;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Options extends ScrollPane {

    private Slider windDirection;
    private Slider windStrength;
    private ColorPicker sandColor;
    private Button reset;
    private CheckBox auto;
    private ComboBox<String> particleType;
    private CheckBox onTop;
    private Button clear;

    public Button getClear() {
        return clear;
    }

    public CheckBox getOnTop() {
        return onTop;
    }

    public Options() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);

        windDirection = new Slider(0, 1, .5);
        windDirection.setBlockIncrement(.10);
        windDirection.setShowTickMarks(true);
        windDirection.setMajorTickUnit(.50);

        windStrength = new Slider(0, 1, .1);
        windStrength.setBlockIncrement(.10);
        windStrength.setShowTickMarks(true);

        particleType = new ComboBox<>();
        particleType.getItems().addAll("Sand", "Water");
        particleType.getSelectionModel().selectFirst();

        windStrength.setMajorTickUnit(.50);

        sandColor = new ColorPicker(Color.CYAN);

        reset = new Button("Reset");
        clear = new Button("Clear");

        auto = new CheckBox("Auto");

        onTop = new CheckBox("Always On Top");

        vBox.getChildren().addAll(new Label("Direction"), windDirection, new Label("Strength"), windStrength,
                new Label("Type"), particleType,
                new Label("Sand Color"), sandColor, auto, new HBox(reset, clear), new Separator(), onTop);
        setContent(vBox);
    }

    public Slider getWindDirection() {
        return windDirection;
    }

    public Slider getWindStrength() {
        return windStrength;
    }

    public Color getSandColor() {
        return sandColor.getValue();
    }

    public Button getReset() {
        return reset;
    }

    public CheckBox getAuto() {
        return auto;
    }

    public String getParticleType() {
        return particleType.getSelectionModel().getSelectedItem();
    }

    public void setParticleType(String type) {
        particleType.getSelectionModel().select(type);
    }
}
