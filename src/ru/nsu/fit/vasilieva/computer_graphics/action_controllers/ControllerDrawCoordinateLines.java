package ru.nsu.fit.vasilieva.computer_graphics.action_controllers;

import java.awt.Color;
import java.awt.Dimension;

public class ControllerDrawCoordinateLines implements ControllerGraphicAction
{
    /**
     * Draws x-axis and y-axis on {@link Drawable}.
     *
     * @param picture - the {@link Drawable} which will be field for drawing x-axis and y-axis.
     * @param length - the number of pixels in the unit interval.
     * @param position - the {@link Dimension} which contains coordinates of the upper left corner of display on the coordinate plane.
     */
    public void drawCoordinateLines(Drawable picture, int length, Dimension position)
    {
        int width = picture.getWidth();
        int height = picture.getHeight();

        picture.setColor(Color.BLACK);

        int x = width / 2 - position.width;
        if ((x >= 0) && (x < width))
        {
            for(int i = 0; i < height; ++i)
            {
                picture.setPixel(x, i);
            }
        }

        int y = height / 2 - position.height;
        if ((y >= 0) && (y < height))
        {
            for(int i = 0; i < width; ++i)
            {
                picture.setPixel(i, y);
            }
        }

        markAxisX(picture, x, y, width, length, height);
        markAxisY(picture, x, y, width, length, height);
    }

    private void drawStroke(Drawable picture, int constCoordinate, int secondCoordinate,  int limit, int length, int strokeCoordinate, boolean axisX)
    {
        int j;
        int number;
        for(j = constCoordinate - 3; j < constCoordinate + 3; ++j)
        {
            if ((j < 0) || (j >= limit))
            {
                continue;
            }
            else
            {
                if (axisX)
                {
                    picture.setPixel(strokeCoordinate, j);
                }
                else
                {
                    picture.setPixel(j, strokeCoordinate);
                }
            }
        }

        if (axisX)
        {
            number = (secondCoordinate - strokeCoordinate) / length;
            picture.setText(new Integer(-number).toString(), Color.BLACK, strokeCoordinate, j);
        }
        else
        {
            number = (strokeCoordinate - secondCoordinate) / length;
            if (number != 0)
            {
                picture.setText(new Integer(-number).toString(), Color.BLACK, j, strokeCoordinate);
            }
        }

    }

    private void markAxisX(Drawable picture, int x, int y, int width, int length, int height)
    {
        int i;
        for (i = 0; i <  width; ++i)
        {
            if (Math.abs(x - i) % length == 0)
            {
                drawStroke(picture, y, x, height, length, i, true);
                break;
            }
        }

        i += length;
        while (i < width)
        {
            drawStroke(picture, y, x, height, length, i, true);
            i += length;
        }

    }

    private void markAxisY(Drawable picture, int x, int y, int width, int length, int height)
    {
        int i;
        for (i = 0; i < height; ++i)
        {
            if (Math.abs(y - i) % length == 0)
            {
                drawStroke(picture, x, y, width, length, i, false);
                break;
            }
        }

        i += length;
        while (i < height)
        {
            drawStroke(picture, x, y, width, length, i, false);
            i +=length;
        }
    }
}
