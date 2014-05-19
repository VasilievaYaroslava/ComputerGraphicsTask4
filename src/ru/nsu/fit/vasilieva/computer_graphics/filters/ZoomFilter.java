package ru.nsu.fit.vasilieva.computer_graphics.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoomFilter implements AbstractFilter
{
    protected BufferedImage image;
    protected int N = 2;

    @Override
    public Color[][] useFilter(Color[][] imageMatrix, int width, int heigth)
    {


        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; i += 2)
        {
            for (int j = 0; j < width; ++j)
            {
                int rgb = 0;
                if (j % 2 == 0)
                {
                    newImage[i][j] = new Color(imageMatrix[i / N + heigth / N / N][i / N + width / N / N].getRGB());
                    image.setRGB(i, j, imageMatrix[i / N + heigth / N / N][j / N + width / N / N].getRGB());
                }
                else
                {

                    if ((j + 1) / N >= width)
                    {
                        newImage[i][j] = new Color(imageMatrix[i / N + heigth / N / N][i / N + width / N / N].getRGB());
                        image.setRGB(i, j, imageMatrix[i / N + heigth / N / N][j / N + width / N / N].getRGB());
                    }
                    else
                    {
                        Color color1 = imageMatrix[i / N + heigth / N / N][j / N + width / N / N];
                        Color color2 = imageMatrix[i / N + heigth / N / N][j / N + width / N / N + 1];

                        rgb = average(color1.getRed(), color2.getRed(), color1.getGreen(), color2.getGreen(), color1.getBlue(), color2.getBlue());
                        newImage[i][j] = new Color(rgb);

                        image.setRGB(i, j, rgb);
                    }
                }
            }
        }

        for (int i = 1; i < heigth; i += 2)
        {
            if (i == heigth - 1)
            {
                for(int j = 0; j < width; ++j)
                {
                    newImage[i][j] = newImage[i - 1][j];
                    image.setRGB(i, j, newImage[i - 1][j].getRGB());
                }
            }
            else
            {
                for(int j = width - 1; j >= 0; --j)
                {
                    if (j % 2 == 0)
                    {
                        newImage[i][j] = newImage[i][j + 1];
                        image.setRGB(i, j, newImage[i][j + 1].getRGB());
                        continue;
                    }

                    Color color1 = newImage[i - 1][j];
                    Color color2 = newImage[i + 1][j];

                    int rgb = average(color1.getRed(), color2.getRed(), color1.getGreen(), color2.getGreen(), color1.getBlue(), color2.getBlue());
                    newImage[i][j] = new Color(rgb);
                    image.setRGB(i, j, rgb);
                }
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

    private int average(int r1, int r2, int g1, int g2, int b1, int b2)
    {
        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;
        return r * 256 * 256 + g * 256 + b;
    }

    private int average(int r1, int r2, int g1, int g2, int b1, int b2, boolean lll)
    {
        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;
        return r * 256 * 256 + g * 256 + b;
    }
}
