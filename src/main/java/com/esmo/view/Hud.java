package com.esmo.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Hud extends VBox {


    public Hud() {
        setSpacing(1);
        setMouseTransparent(true);
    }

    public void addItem(String label, StringProperty property) {
        Text text = new Text();
        text.textProperty().bind(new SimpleStringProperty(label).concat(": ").concat(property));
        getChildren().add(text);
    }
    public void addItem(String label, IntegerProperty property) {
        Text text = new Text();
        text.textProperty().bind(new SimpleStringProperty(label).concat(": ").concat(property));
        getChildren().add(text);
    }

    public void setTextColor(Color color) {
        for (int i = 0; i < getChildren().size(); i++) {
            ((Text) getChildren().get(i)).setFill(color);
        }
    }
}
