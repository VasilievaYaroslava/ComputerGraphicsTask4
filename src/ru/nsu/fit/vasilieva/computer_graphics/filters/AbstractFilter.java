package ru.nsu.fit.vasilieva.computer_graphics.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface AbstractFilter
{
    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth);

    public BufferedImage getImage();
}
