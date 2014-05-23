package ru.nsu.fit.vasilieva.computer_graphics.task2specific;

import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.ControllerDrawParametricСurve;
import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.Drawable;
import ru.nsu.fit.vasilieva.computer_graphics.view.DrawableBufferedImage;

import java.awt.*;

public class MenuController extends ru.nsu.fit.vasilieva.computer_graphics.graphs_specific.MenuController
{
    private int NEW_DEFAULT_LENGTH = 6;
    private ControllerDrawParametricСurve controller;

    /**
     * Creates new {@link MenuController} for drawing lemniscate of Bernoulli.
     */
    protected MenuController()
    {
        controller = new ControllerDrawParametricСurve();
        length = NEW_DEFAULT_LENGTH;
        updatePicture();
    }

    @Override
    public void updatePicture()
    {
        drawParametricCurve();
    }

    @Override
    public void resetChanges()
    {
        position = new Dimension(0, 0);
        length = DEFAULT_LENGTH;
        updatePicture();
    }

    private void drawParametricCurve()
    {
        Dimension size = window.getImageSize();
        int width = size.width;
        int height = size.height;
        Drawable picture = new DrawableBufferedImage(width, height);
        ((ControllerDrawParametricСurve)controller).drawCurve(picture, DEFAUTL_TENSION * length, position);
        window.setDrawable((DrawableBufferedImage) picture);
    }
}