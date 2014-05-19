package ru.nsu.fit.vasilieva.computer_graphics.action_controllers;

import java.awt.Color;
import java.awt.Dimension;

public class ControllerDrawLemniscateOfBernoulli extends ControllerDrawCoordinateLines
{
    private Drawable picture;
    private int length;
    private int width;
    private int height;
    private Dimension position;

    private double maxY, minY, maxX, minX;
    private double rightLimit, leftLimit;

    private static final int CONST_C = 1;

    /**
     * Draws lemniscate of Bernoull on {@link Drawable}.
     *
     * @param picture - the {@link Drawable} which will be field for drawing lemniscate of Bernoull.
     * @param length - the number of pixels in the unit interval.
     * @param position - the {@link Dimension} which contains coordinates of the upper left corner of display on the coordinate plane.
     */
    public void drawLemniscate(Drawable picture, int length, Dimension position)
    {
        drawCoordinateLines(picture, length, position);
        picture.setColor(Color.RED);

        this.picture = picture;
        this.length = length;
        this.width = picture.getWidth();
        this.height = picture.getHeight();
        this.position = position;

        maxY = ((double) height / 2 - position.height)/length;
        minY = - ((double) height / 2 + position.height)/length;
        minX = - ((double) width / 2 - position.width)/length;
        maxX = ((double) width / 2 + position.width)/length;

        double deltaX = ((double)maxY - minY) / height;
        calculateLimits();

        double currentX = leftLimit + deltaX / 2;
        int lastY = setSymmetricPoints(currentX, calculateY(currentX));
        currentX += deltaX;

        while (currentX <= rightLimit)
        {
            int currentY = setSymmetricPoints(currentX, calculateY(currentX));

            if (currentY - lastY != 1)
            {
                int coordinateX1 = (int) Math.round((currentX - minX) * length);
                int deltaY = currentY - lastY - 1;
                if (deltaY > 0)
                {
                    for(int i = 1; i <= deltaY - deltaY / 2; ++i)
                    {
                        setSymmetricPoints(coordinateX1, lastY + i);
                    }
                    for(int i = 1; i <= deltaY / 2; ++i)
                    {
                        setSymmetricPoints(coordinateX1, currentY - i);
                    }
                }
                else
                {
                    for(int i = 1; i < deltaY - deltaY / 2; ++i)
                    {
                        setSymmetricPoints(coordinateX1, lastY - i);
                    }
                    for(int i = 1; i < deltaY / 2; ++i)
                    {
                        setSymmetricPoints(coordinateX1, currentY + i);
                    }
                }

            }
            lastY = currentY;


            currentX += deltaX / 1.01;
        }

        int coordinateX1 = (int) Math.round(((currentX - deltaX) - minX) * length);

        while (lastY < height / 2 - position.height)
        {
            setSymmetricPoints(coordinateX1, lastY);
            ++lastY;
        }

    }

    private void calculateLimits()
    {
        double limit = Math.sqrt(2) * CONST_C;
        if ((minX > limit) || (maxX < -limit))
        {
            rightLimit = 0;
            leftLimit = 0;
            return;
        }
        if (minX >= 0)
        {
            leftLimit = minX;
            rightLimit = (maxX < limit) ? maxX : limit;
            return;
        }
        if (maxX <= 0)
        {
            leftLimit = - maxX;
            rightLimit = (minX < -limit) ? limit : (-minX);
            return;
        }
        rightLimit = (maxX > limit) ? limit : maxX;
        leftLimit = (Math.abs(minX) > limit) ? limit : (-minX);
        rightLimit = (rightLimit > leftLimit) ? rightLimit : leftLimit;
        leftLimit = 0;
    }

    private double calculateY(double x)
    {
        double D = 16 * x * x * CONST_C * CONST_C + 4 * Math.pow(CONST_C, 4);
        double y = (-2 * x * x - 2 * CONST_C * CONST_C + Math.sqrt(D)) / 2;
        return Math.sqrt(y);
    }

    private int setSymmetricPoints(double x, double y)
    {
        int coordinateX1 = (int) Math.round((x - minX) * length);
        int coordinateY1 = (int) Math.round((maxY - y) * length);

        int centerX = width / 2 - position.width;
        int centerY = height / 2 - position.height;
        int coordinateX2 = centerX - (coordinateX1 - centerX);
        int coordinateY2 = centerY -  (coordinateY1 - centerY);

        setSymmetricPoints(coordinateX1, coordinateY1, coordinateX2, coordinateY2);

        return coordinateY1;
    }

    private void setSymmetricPoints(int coordinateX1, int coordinateY1)
    {
        int centerX = width / 2 - position.width;
        int centerY = height / 2 - position.height;
        int coordinateX2 = centerX - (coordinateX1 - centerX);
        int coordinateY2 = centerY -  (coordinateY1 - centerY);
        setSymmetricPoints(coordinateX1, coordinateY1, coordinateX2, coordinateY2);
    }

    private void setSymmetricPoints(int coordinateX1, int coordinateY1, int coordinateX2, int coordinateY2)
    {
        if ((coordinateX1 > 0) && (coordinateX1 < width))
        {
            if ((coordinateY1 > 0) && (coordinateY1 < height))
            {
                picture.setPixel(coordinateX1, coordinateY1);
            }
            if ((coordinateY2 > 0) && (coordinateY2 < height))
            {
                picture.setPixel(coordinateX1, coordinateY2);
            }
        }

        if ((coordinateX2 > 0) && (coordinateX2 < width))
        {
            if ((coordinateY1 > 0) && (coordinateY1 < height))
            {
                picture.setPixel(coordinateX2, coordinateY1);
            }
            if ((coordinateY2 > 0) && (coordinateY2 < height))
            {
                picture.setPixel(coordinateX2, coordinateY2);
            }
        }
    }
}
