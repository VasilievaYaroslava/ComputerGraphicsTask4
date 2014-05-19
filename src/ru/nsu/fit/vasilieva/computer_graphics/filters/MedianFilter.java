package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFilter implements AbstractFilter
{
    protected BufferedImage image;
    protected int N = 5;

    @Override
    public Color[][] useFilter(Color[][] imageMatrix, int width, int heigth)
    {
        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++ i)
        {
            for (int j = 0; j < width; ++j)
            {
                int redColors[] = new int[N * N];
                int greenColors[] = new int[N * N];
                int blueColors[] = new int[N * N];
                int counter = 0;

                int y1 = i - N / 2;
                int y2 = i + N / 2;
                int x1 = j - N / 2;
                int x2 = j + N / 2;

                for (int k1 = y1; k1 <= y2; ++k1) //h
                {
                    if ((k1 < 0) || (k1 >= heigth))
                    {
                        for (int k2 = 0; k2 < N; ++k2)
                        {
                            redColors[(k1 - y1) * N + k2] = 0;
                            greenColors[(k1 - y1) * N + k2] = 0;
                            blueColors[(k1 - y1) * N + k2] = 0;
                        }
                        continue;
                    }
                    for (int k2 = x1; k2 <= x2; ++k2) //w
                    {
                        if ((k2 < 0) || (k2 >= width))
                        {
                            redColors[(k1 - y1) * N + (k2 - x1)] = 0;
                            greenColors[(k1 - y1) * N + (k2 - x1)] = 0;
                            blueColors[(k1 - y1) * N + (k2 - x1)] = 0;
                            continue;
                        }

                        redColors[(k1 - y1) * N + (k2 - x1)] = imageMatrix[k1][k2].getRed();
                        greenColors[(k1 - y1) * N + (k2 - x1)] = imageMatrix[k1][k2].getGreen();
                        blueColors[(k1 - y1) * N + (k2 - x1)] = imageMatrix[k1][k2].getBlue();
                        ++counter;
                    }
                }
                Arrays.sort(redColors);
                Arrays.sort(greenColors);
                Arrays.sort(blueColors);

                int r;
                int g;
                int b;

                if (counter % 2 == 1)
                {
                    r = redColors[(counter - 1) / 2];
                    g = greenColors[(counter - 1) / 2];
                    b = blueColors[(counter - 1) / 2];
                }
                else
                {
                    r = redColors[counter / 2 - 1];
                    g = greenColors[counter / 2 - 1];
                    b = blueColors[counter / 2 - 1];
                }

                int rgb = r * 256 * 256 + g * 256 + b;
                newImage[i][j] = new Color(rgb);
                image.setRGB(i, j, rgb);
            }
        }

        image.flush();
        return newImage;
    }

    @Override
    public BufferedImage getImage()
    {
        return image;
    }
}
