package ru.nsu.fit.vasilieva.computer_graphics.task3specific;

import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.ControllerDrawLemniscateOfBernoulli;
import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.Drawable;
import ru.nsu.fit.vasilieva.computer_graphics.view.DrawableBufferedImage;

import java.awt.*;

public class MenuController extends ru.nsu.fit.vasilieva.computer_graphics.graphs_specific.MenuController
{
    private int NEW_DEFAULT_LENGTH = 18;
    private ControllerDrawLemniscateOfBernoulli controller;

    /**
     * Creates new {@link MenuController} for drawing lemniscate of Bernoulli.
     */
    protected MenuController()
    {
        controller = new ControllerDrawLemniscateOfBernoulli();
        length = NEW_DEFAULT_LENGTH;
        updatePicture();
    }

    /**
     * Start function.
     *
     * @param args - the array of {@link String}, which are the programs arguments.
     */
    public static void main(String args[])
    {
        MenuController controller = new MenuController();
    }

    @Override
    public void updatePicture()
    {
        drawLemniscate();
    }

    @Override
    public void resetChanges()
    {
        position = new Dimension(0, 0);
        length = NEW_DEFAULT_LENGTH;
        updatePicture();
    }

    private void drawLemniscate()
    {
        if (controller == null)
        {
            return;
        }
        Dimension size = window.getImageSize();
        int width = size.width;
        int height = size.height;
        Drawable picture = new DrawableBufferedImage(width, height);
        controller.drawLemniscate(picture, DEFAUTL_TENSION * length, position);
        window.setDrawable((DrawableBufferedImage) picture);
    }
}