package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractMatrixFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StampMatrixFilter extends AbstractMatrixFilter
{
    private static int STAMP_OFFSET = 128;

    @Override
    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth) {
        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++i) {
            for (int j = 0; j < width; ++j) {
                double r = 0, g = 0, b = 0;
                int y1 = i - N / 2;
                int y2 = i + N / 2;
                int x1 = j - N / 2;
                int x2 = j + N / 2;

                for (int k1 = y1; k1 <= y2; ++k1) //h
                {
                    if ((k1 < 0) || (k1 >= heigth)) {
                        continue;
                    }
                    for (int k2 = x1; k2 <= x2; ++k2) //w
                    {
                        if ((k2 < 0) || (k2 >= width)) {
                            continue;
                        }

                        r += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getRed();
                        g += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getGreen();
                        b += matrix[k1 - y1][k2 - x1] * imageMatrix[k1][k2].getBlue();

                    }
                }

                r = getReal(r);
                g = getReal(g);
                b = getReal(b);

                newImage[i][j] = new Color((int) (r * 256 * 256 + g * 256 + b));
                image.setRGB(i, j, (int) (r * 256 * 256 + g * 256 + b));
            }
        }

        image.flush();
        return newImage;
    }

    @Override
    protected void generateMatrix()
    {
        N = 3;
        matrix = new double[N][N];

        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 0;
        matrix[1][0] = -1;
        matrix[1][1] = 0;
        matrix[1][2] = 1;
        matrix[2][0] = 0;
        matrix[2][1] = -1;
        matrix[2][2] = 0;
    }

    private int getReal(double comp)
    {
        int real = (int) comp + STAMP_OFFSET;
        if (real > 255)
        {
            real = 255;
        }
        if (real < 0)
        {
            real = 0;
        }
        return real;
    }
}

