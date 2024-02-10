package com.esmo.utils;

import javafx.scene.paint.Color;
import java.util.*;

public class Palette {

    public static ArrayList<Color> bluePalette() {
        ArrayList<Color> bluePalette = new ArrayList<>();
        bluePalette.add(Color.web("#3498db")); // Dodger Blue
        bluePalette.add(Color.web("#2980b9")); // Darker Blue
        bluePalette.add(Color.web("#1f618d")); // Navy Blue
        bluePalette.add(Color.web("#21618c")); // Midnight Blue

        return bluePalette;
    }

    public static ArrayList<Color> sunsetPalette() {
        ArrayList<Color> sunsetPalette = new ArrayList<>();
        sunsetPalette.add(Color.rgb(255, 176, 124)); // Warm orange
        sunsetPalette.add(Color.rgb(255, 135, 94)); // Light orange
        sunsetPalette.add(Color.rgb(255, 105, 97)); // Coral
        sunsetPalette.add(Color.rgb(255, 87, 104)); // Pinkish red
        sunsetPalette.add(Color.rgb(255, 69, 138)); // Dark pink
        sunsetPalette.add(Color.rgb(255, 52, 183)); // Magenta
        sunsetPalette.add(Color.rgb(255, 94, 182)); // Lavender

        return sunsetPalette;
    }

    public static ArrayList<Color> purplePalette() {
        ArrayList<Color> purplePalette = new ArrayList<>();
        purplePalette.add(Color.rgb(138, 43, 226)); // Blue Violet
        purplePalette.add(Color.rgb(159, 121, 238)); // Lavender Blue
        purplePalette.add(Color.rgb(128, 0, 128)); // Purple
        purplePalette.add(Color.rgb(186, 85, 211)); // Medium Orchid
        purplePalette.add(Color.rgb(148, 0, 211)); // Dark Violet
        purplePalette.add(Color.rgb(138, 130, 255)); // Light Slate Blue
        purplePalette.add(Color.rgb(128, 128, 255)); // Light Steel Blue

        return purplePalette;
    }

    public static ArrayList<Color> greenPalette() {
        ArrayList<Color> greenPalette = new ArrayList<>();
        greenPalette.add(Color.rgb(0, 128, 0)); // Green
        greenPalette.add(Color.rgb(34, 139, 34)); // Forest Green
        greenPalette.add(Color.rgb(0, 255, 0)); // Lime
        greenPalette.add(Color.rgb(0, 128, 128)); // Teal
        greenPalette.add(Color.rgb(60, 179, 113)); // Medium Sea Green
        greenPalette.add(Color.rgb(46, 139, 87)); // Sea Green
        greenPalette.add(Color.rgb(0, 250, 154)); // Medium Spring Green

        return greenPalette;
    }

    public static ArrayList<Color> redPalette() {
        ArrayList<Color> redPalette = new ArrayList<>();
        redPalette.add(Color.rgb(255, 0, 0)); // Red
        redPalette.add(Color.rgb(220, 20, 60)); // Crimson
        redPalette.add(Color.rgb(178, 34, 34)); // Fire Brick
        redPalette.add(Color.rgb(255, 69, 0)); // Orange Red
        redPalette.add(Color.rgb(255, 99, 71)); // Tomato
        redPalette.add(Color.rgb(255, 127, 80)); // Coral
        redPalette.add(Color.rgb(255, 140, 0)); // Dark Orange

        return redPalette;
    }

    public static ArrayList<Color> gameboyPalette() {
        ArrayList<Color> gameboyPalette = new ArrayList<>();
        gameboyPalette.add(Color.rgb(155, 188, 15)); // Light Green
        gameboyPalette.add(Color.rgb(139, 172, 15)); // Dark Green
        gameboyPalette.add(Color.rgb(48, 98, 48)); // Dark Gray
        gameboyPalette.add(Color.rgb(15, 56, 15)); // Light Gray

        return gameboyPalette;
    }

    public static ArrayList<ArrayList<Color>> getColorList() {
        return new ArrayList<>(Arrays.asList(bluePalette(),
                sunsetPalette(), purplePalette(), greenPalette(), redPalette(),gameboyPalette()));
    }

}
