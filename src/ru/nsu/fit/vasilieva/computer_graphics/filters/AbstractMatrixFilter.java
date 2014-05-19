package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractMatrixFilter implements AbstractFilter
{
    protected double[][] matrix;
    protected BufferedImage image;
    protected double norm;
    protected int N;

    public AbstractMatrixFilter()
    {
        generateMatrix();

    }

    protected void generateMatrix()
    {
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth)
    {
        double Sum = 0;
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                Sum += matrix[i][j];
            }
        }
        norm = Sum / N / N;

        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++ i)
        {
            for (int j = 0; j < width; ++j)
            {
                double r = 0, g = 0, b = 0;
                double sum = 0;
                int y1 = i - N / 2;
                int y2 = i + N / 2;
                int x1 = j - N / 2;
                int x2 = j + N / 2;

                for (int k1 = y1; k1 <= y2; ++k1) //h
                {
                    if ((k1 < 0) || (k1 >= heigth))
                    {
                        continue;
                    }
                    for (int k2 = x1; k2 <= x2; ++k2) //w
                    {
                        if ((k2 < 0) || (k2 >= width))
                        {
                            continue;
                        }

                        sum += matrix[k1 - y1][k2 - x1];
                        r += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getRed();
                        g += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getGreen();
                        b += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getBlue();

                    }
                }
                r = Math.round(r / sum);
                g = Math.round(g / sum);
                b = Math.round(b / sum);
                r = correct(r);
                g = correct(g);
                b = correct(b);

                newImage[i][j] = new Color((int)(r * 256 * 256 + g * 256 + b));
                image.setRGB(i, j, (int) (r * 256 * 256 + g * 256 + b));
            }
        }

        image.flush();
        return newImage;
    }

    protected double correct(double color)
    {
        if (color < 0)
        {
            return  0;
        }
        if (color > 255)
        {
            return  255;
        }
        return color;
    }
}
