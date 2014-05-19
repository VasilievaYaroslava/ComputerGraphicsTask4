package ru.nsu.fit.vasilieva.computer_graphics.action_controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

public class ControllerDrawParametricСurve extends ControllerDrawCoordinateLines
{
    private double maxY, minY, maxX, minX;
    /**
     * Draws parametric curve on the {@link Drawable}.
     *
     * @param picture - the {@link Drawable} which will be field for drawing parametric curve.
     * @param length - the number of pixels in the unit interval.
     */
    public void drawCurve(Drawable picture, int length, Dimension position)
    {
        drawCoordinateLines(picture, length, position);

        int width = picture.getWidth();
        int height = picture.getHeight();

        maxY = ((double) height / 2 - position.height)/length;
        minY = - ((double) height / 2 + position.height)/length;
        minX = - ((double) width / 2 - position.width)/length;
        maxX = ((double) width / 2 + position.width)/length;

        picture.setColor(Color.CYAN);


        double t = -2 - Math.sqrt(5.0);
        double realMaxX = t * t / (t * t - 1);
        double realMaxY = (t * t + 1) / (t + 2);
        Double[] interval = calculateInterval(Double.NEGATIVE_INFINITY, -2 - Math.sqrt(5), realMaxX, 1, realMaxY, Double.NEGATIVE_INFINITY, true, false);
        calculateLine(picture, length, width, height, interval);

        double realMinX = realMaxX;
        t = -2;
        realMaxX = t * t / (t * t - 1);
        interval = calculateInterval(-2 - Math.sqrt(5), -2, realMaxX, realMinX, realMaxY, Double.NEGATIVE_INFINITY, false, true);
        calculateLine(picture, length, width, height, interval);


        realMinX = realMaxX;
        t = -1;
        double realMinY = (t * t + 1) / (t + 2);
        interval = calculateInterval(-2, -1, Double.POSITIVE_INFINITY, realMinX, Double.POSITIVE_INFINITY, realMinY, true, true);
        calculateLine(picture, length, width, height, interval);

        t = -1;
        realMaxY = (t * t + 1) / (t + 2);
        t = 0;
        realMinY = (t * t + 1) / (t + 2);
        interval = calculateInterval(-1, 0, 0, Double.NEGATIVE_INFINITY, realMaxY, realMinY, true, false);
        calculateLine(picture, length, width, height, interval);

        t = 0;
        realMaxX = (t * t) / (t * t - 1);
        realMaxY = (t * t + 1) / (t + 2);
        t = -2 + Math.sqrt(5);
        realMinX = (t * t) / (t * t - 1);
        realMinY = (t * t + 1) / (t + 2);
        interval = calculateInterval(0, -2 + Math.sqrt(5), realMaxX, realMinX, realMaxY, realMinY, false, false);
        calculateLine(picture, length, width, height, interval);


        t = -2 + Math.sqrt(5);
        realMaxX = (t * t) / (t * t - 1);
        realMinY = (t * t + 1) / (t + 2);
        t = 1;
        realMaxY = (t * t + 1) / (t + 2);
        interval = calculateInterval(-2 + Math.sqrt(5), 1, realMaxX, Double.NEGATIVE_INFINITY, realMaxY, realMinY, false, true);
        calculateLine(picture, length, width, height, interval);

        interval = calculateInterval(1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1, Double.POSITIVE_INFINITY, realMinY, true, true);
        calculateLine(picture, length, width, height, interval);
    }

    private Double[] calculateInterval(double leftLimit, double rightLimit, double maxXT, double minXT, double maxYT, double minYT, boolean strictLeft, boolean strictRight)
    {
        Double[] limits = new Double[2];

        double realMinX = (minX > minXT) ? minX : minXT;
        double realMaxX = (maxX > maxXT) ? maxXT : maxX;
        double realMinY = (minY > minYT) ? minY : minYT;
        double realMaxY = (maxY > maxYT) ? maxYT : maxY;

        if ((realMaxX < realMinX) || (realMaxY < realMinY))
        {
            return null;
        }

        ArrayList<Double> minValuesT = new ArrayList<Double>();
        ArrayList<Double> maxValuesT = new ArrayList<Double>();

        solveXEquation(minValuesT, realMinX, leftLimit, rightLimit, strictLeft, strictRight);
        solveXEquation(maxValuesT, realMaxX, leftLimit, rightLimit, strictLeft, strictRight);
        solveYEquation(minValuesT, realMinY, leftLimit, rightLimit, strictLeft, strictRight);
        solveYEquation(maxValuesT, realMaxY, leftLimit, rightLimit, strictLeft, strictRight);

        if (minValuesT.size() == 0 && maxValuesT.size() == 0)
        {
            return null;
        }
        if (minValuesT.size() != 0 && maxValuesT.size() != 0)
        {
            double min1 = getSupr(minValuesT, false);
            double min2 = getSupr(maxValuesT, false);
            double max1 = getSupr(minValuesT, true);
            double max2 = getSupr(maxValuesT, true);
            limits[0] = (min1 < min2) ? min1 : min2;
            limits[1] = (max1 > max2) ? max1 : max2;
        }


        if (minValuesT.size() == 0)
        {
            int i = 1;
            limits[0] = maxValuesT.get(0);
            limits[1] = maxValuesT.get(0);
            while (i < maxValuesT.size())
            {
                if (maxValuesT.get(i) < limits[0])
                {
                    limits[0] = maxValuesT.get(i);
                }
                if (maxValuesT.get(i) > limits[1])
                {
                    limits[1] = maxValuesT.get(i);
                }
                ++i;
            }
        }

        if (maxValuesT.size() == 0)
        {
            int i = 1;
            limits[0] = minValuesT.get(0);
            limits[1] = minValuesT.get(0);
            while (i < minValuesT.size())
            {
                if (minValuesT.get(i) < limits[0])
                {
                    limits[0] = minValuesT.get(i);
                }
                if (minValuesT.get(i) > limits[1])
                {
                    limits[1] = minValuesT.get(i);
                }
                ++i;
            }
        }


        return limits;
    }

    private double getSupr(ArrayList<Double> array, boolean max)
    {
        int i = 1;
        double surp = array.get(0);

        if (max)
        {
            while (i < array.size())
            {
                if ((array.get(i) > surp))
                {
                    surp = array.get(i);
                }
                ++i;
            }
        }
        else
        {
            while (i < array.size())
            {
                if (array.get(i) < surp)
                {
                    surp = array.get(i);
                }
                ++i;
            }
        }
        return surp;
    }

    private void solveXEquation(ArrayList<Double> roots, double value, double leftLimit, double rightLimit, boolean strictLeft, boolean strictRight)
    {
        if (value == 1)
        {
            return;
        }
        double root = Math.sqrt(value / (value - 1));
        System.out.println("roo" + -root);

        if ((strictLeft && (root > leftLimit)) || (!strictLeft && (root >= leftLimit)))
        {
            if ((strictRight && (root < rightLimit)) || (!strictRight && (root <= rightLimit)))
            {
                System.out.println("roo" + -root);
                roots.add(new Double(root));
            }
        }
        if ((strictLeft && (-root > leftLimit)) || (!strictLeft && (-root >= leftLimit)))
        {
            if ((strictRight && (-root < rightLimit)) || (!strictRight && (-root <= rightLimit)))
            {
                System.out.println("roo" + -root);
                roots.add(new Double(-root));
            }
        }
    }

    private void solveYEquation(ArrayList<Double> roots, double value, double leftLimit, double rightLimit, boolean strictLeft, boolean strictRight)
    {
        double D = value * value + 8 * value - 4;
        if (D < 0)
        {
            return;
        }
        if (D == 0)
        {
            double root = value / 2;
            System.out.println("roo" + -root);

            if ((strictLeft && (root > leftLimit)) || (!strictLeft && (root >= leftLimit)))
            {
                if ((strictRight && (root < rightLimit)) || (!strictRight && (root <= rightLimit)))
                {
                    roots.add(new Double(root));
                }
            }
            return;
        }
        double root = (value + Math.sqrt(D)) / 2;
        System.out.println("roo" + -root);

        if ((strictLeft && (root > leftLimit)) || (!strictLeft && (root >= leftLimit)))
        {
            if ((strictRight && (root < rightLimit)) || (!strictRight && (root <= rightLimit)))
            {
                roots.add(new Double(root));
            }
        }
        root = (value - Math.sqrt(D)) / 2;
        if ((strictLeft && (root > leftLimit)) || (!strictLeft && (root >= leftLimit)))
        {
            if ((strictRight && (root < rightLimit)) || (!strictRight && (root <= rightLimit)))
            {
                roots.add(new Double(root));
            }
        }

    }


    private double derivativeX(double t)
    {
        return (-2 * t)/(t * t - 1)/(t * t - 1);
    }

    private double derivativeY(double t)
    {
        return (t * t + 4 * t - 1)/(t + 2)/(t + 2);
    }

    private void calculateLine(Drawable picture, int length, int width, int height, Double[] interval)
    {
        if (interval == null)
        {
            return;
        }

        double mX = (Math.abs(derivativeX(interval[0])) > Math.abs(derivativeX(interval[1]))) ? Math.abs(derivativeX(interval[0])) : Math.abs(derivativeX(interval[1]));
        double mY = (Math.abs(derivativeY(interval[0])) > Math.abs(derivativeY(interval[1]))) ? Math.abs(derivativeY(interval[0])) : Math.abs(derivativeY(interval[1]));

        double M = (mX > mY) ? mX : mY;

        double s = (maxY - minY) / height;
        double deltaT = s / M;

        double rightLimit = (interval[0] > interval[1]) ? interval[0] : interval[1];
        double leftLimit = (interval[0] > interval[1]) ? interval[1] : interval[0];

        double t = leftLimit;
        while (t < rightLimit)
        {
            double x = (t * t) / (t * t - 1);
            double y = (t * t + 1)/(t + 2);
            int coordinateX = (int) Math.round((x - minX) * length);
            int coordinateY = (int) Math.round((maxY - y) * length);
            if ((coordinateX > 0) && (coordinateX < width) && (coordinateY > 0) && (coordinateY < height))
            {
                picture.setPixel(coordinateX, coordinateY);
            }
            t += deltaT;
        }
    }

}
