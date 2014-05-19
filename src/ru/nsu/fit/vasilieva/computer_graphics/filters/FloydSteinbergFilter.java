package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloydSteinbergFilter implements AbstractFilter
{
    protected BufferedImage image;
    private int[] redColors;
    private int[] greenColors;
    private int[] blueColors;
    private int redBits;
    private int greenBits;
    private int blueBits;
    private int redCoefficient;
    private int greenCoefficient;
    private int blueCoefficient;
    private int n;

    public FloydSteinbergFilter(int[] bits)
    {
        this.redBits = bits[0];
        this.greenBits = bits[1];
        this.blueBits = bits[2];

        int n = (redBits + 1) * (greenBits + 1) * (blueBits + 1);

        redCoefficient = 255 / (redBits + 1);
        greenCoefficient = 255 / (greenBits + 1);
        blueCoefficient = 255 / (blueBits + 1);

        redColors = new int[(int) Math.pow(2, redBits)];
        int step = (int) Math.pow(2, (8 - redBits));
        int current = 0;
        for (int i = 0; i < (int) Math.pow(2, redBits); ++i)
        {
            redColors[i] = current;
            current += step;
        }

        greenColors = new int[(int) Math.pow(2, greenBits)];
        step = (int) Math.pow(2, (8 - greenBits));
        current = 0;
        for (int i = 0; i < (int) Math.pow(2, greenBits); ++i)
        {
            greenColors[i] = current;
            current += step;
        }


        blueColors = new int[(int) Math.pow(2, blueBits)];
        step = (int) Math.pow(2, (8 - blueBits));
        current = 0;
        for (int i = 0; i < (int) Math.pow(2, blueBits); ++i)
        {
            blueColors[i] = current;
            current += step;
        }
    }

    private int findColor(int[] palette, int color)
    {
        if (color < 0)
        {
            return 0;
        }

        if (color > 255)
        {
            return 255;
        }

        for (int i = 0; i < palette.length; ++i)
        {
            if (palette[i] == color)
            {
                return color;
            }
            if (palette[i] > color)
            {
                return palette[i - 1];
            }
        }
        return 0;
    }

    @Override
    public Color[][] useFilter(Color[][] imageMatrix, int width, int heigth)
    {
        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++i)
        {
            for (int j = 0; j < width; ++j)
            {
                newImage[i][j] = new Color(imageMatrix[i][j].getRGB());
            }
        }

        int redGap = 256/redBits;
        int greenGap = 256/greenBits;
        int blueGap = 256/blueBits;

        for(int i = 1; i < heigth; ++i)
        {
            for(int j = 1; j < width; ++j)
            {
                int errorR = newImage[i][j].getRed() % redGap;
                int errorG = newImage[i][j].getGreen() % greenGap;
                int errorB = newImage[i][j].getBlue() % blueGap;

                int r = newImage[i][j].getRed() - errorR;
                int g = newImage[i][j].getGreen() - errorG;
                int b = newImage[i][j].getBlue()  - errorB;

                r = correct(r);
                g = correct(g);
                b = correct(b);

                newImage[i][j] = new Color(r, g, b);
                image.setRGB(i, j, newImage[i][j].getRGB());

                int newR = 0;
                int newG = 0;
                int newB = 0;

                if (j != width - 1)
                {
                    newR = correct(newImage[i][j + 1].getRed() + (int) (errorR * 7.0 / 16));
                    newG = correct(newImage[i][j + 1].getGreen() + (int) (errorG * 7.0 / 16));
                    newB = correct(newImage[i][j + 1].getBlue() + (int) (errorB * 7.0 / 16));
                    newImage[i][j + 1] = new Color(newR, newG, newB);
                }

                if (i != heigth - 1 && j != 0)
                {
                    newR = correct(newImage[i + 1][j - 1].getRed() + (int) (errorR * 3.0 / 16));
                    newG = correct(newImage[i + 1][j - 1].getGreen() + (int) (errorG * 3.0 / 16));
                    newB = correct(newImage[i + 1][j - 1].getBlue() + (int) (errorB * 3.0 / 16));
                    newImage[i + 1][j - 1] = new Color(newR, newG, newB);
                }

                if (i != heigth - 1)
                {
                    newR = correct(newImage[i + 1][j].getRed() + (int) (errorR * 5.0 / 16));
                    newG = correct(newImage[i + 1][j].getGreen() + (int) (errorG * 5.0 / 16));
                    newB = correct(newImage[i + 1][j].getBlue() + (int) (errorB * 5.0 / 16));
                    newImage[i + 1][j] = new Color(newR, newG, newB);
                }

                if (i != heigth - 1 && j != width - 1)
                {
                    newR = correct(newImage[i + 1][j + 1].getRed() + (int) (errorR * 1.0 / 16));
                    newG = correct(newImage[i + 1][j + 1].getGreen() + (int) (errorG * 1.0 / 16));
                    newB = correct(newImage[i + 1][j + 1].getBlue() + (int) (errorB * 1.0 / 16));
                    newImage[i + 1][j + 1] = new Color(newR, newG, newB);
                }
            }
        }
        image.flush();;
        return newImage;
    }

    @Override
    public BufferedImage getImage()
    {
        return image;
    }

    private int correct(int coeff)
    {
        if (coeff < 0)
        {
            return 0;
        }
        if (coeff > 255)
        {
            return 255;
        }
        return coeff;
    }
}