package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractMatrixFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrderedDitheringMatrixFilter extends AbstractMatrixFilter
{
    private int[] redColors;
    private int[] greenColors;
    private int[] blueColors;
    private int redBits;
    private int greenBits;
    private int blueBits;
    private int redCoefficient;
    private int greenCoefficient;
    private int blueCoefficient;

    public OrderedDitheringMatrixFilter(int[] bits)
    {
        this.redBits = bits[0];
        this.greenBits = bits[1];
        this.blueBits = bits[2];

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
    protected void generateMatrix()
    {
        N = 8;
        matrix = new double[N][N];
        matrix[0][0] = 1;
        matrix[0][1] = 49;
        matrix[0][2] = 13;
        matrix[0][3] = 61;
        matrix[0][4] = 4;
        matrix[0][5] = 52;
        matrix[0][6] = 16;
        matrix[0][7] = 64;

        matrix[1][0] = 33;
        matrix[1][1] = 17;
        matrix[1][2] = 45;
        matrix[1][3] = 29;
        matrix[1][4] = 36;
        matrix[1][5] = 20;
        matrix[1][6] = 48;
        matrix[1][7] = 32;

        matrix[2][0] = 9;
        matrix[2][1] = 57;
        matrix[2][2] = 5;
        matrix[2][3] = 53;
        matrix[2][4] = 12;
        matrix[2][5] = 60;
        matrix[2][6] = 8;
        matrix[2][7] = 56;

        matrix[3][0] = 41;
        matrix[3][1] = 25;
        matrix[3][2] = 37;
        matrix[3][3] = 21;
        matrix[3][4] = 44;
        matrix[3][5] = 28;
        matrix[3][6] = 40;
        matrix[3][7] = 24;

        matrix[4][0] = 3;
        matrix[4][1] = 51;
        matrix[4][2] = 15;
        matrix[4][3] = 63;
        matrix[4][4] = 2;
        matrix[4][5] = 50;
        matrix[4][6] = 14;
        matrix[4][7] = 62;

        matrix[5][0] = 35;
        matrix[5][1] = 19;
        matrix[5][2] = 47;
        matrix[5][3] = 31;
        matrix[5][4] = 34;
        matrix[5][5] = 18;
        matrix[5][6] = 46;
        matrix[5][7] = 30;

        matrix[6][0] = 11;
        matrix[6][1] = 59;
        matrix[6][2] = 7;
        matrix[6][3] = 55;
        matrix[6][4] = 10;
        matrix[6][5] = 58;
        matrix[6][6] = 6;
        matrix[6][7] = 54;

        matrix[7][0] = 43;
        matrix[7][1] = 27;
        matrix[7][2] = 39;
        matrix[7][3] = 23;
        matrix[7][4] = 42;
        matrix[7][5] = 26;
        matrix[7][6] = 38;
        matrix[7][7] = 22;
    }

    @Override
    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth)
    {
        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++i)
        {
            for (int j = 0; j < width; ++ j)
            {
                int r = findColor(redColors, imageMatrix[i][j].getRed() + (int )matrix[i % N][j % N] * redCoefficient / 16);
                int g = findColor(greenColors, imageMatrix[i][j].getGreen() + (int )matrix[i % N][j % N] * greenCoefficient / 16);
                int b = findColor(blueColors, imageMatrix[i][j].getBlue() + (int )matrix[i % N][j % N] * blueCoefficient / 16);
                int rgb = r * 256 * 256 + g * 256 + b;
                newImage[i][j] = new Color(rgb);
                image.setRGB(i, j, rgb);
            }
        }

        return  imageMatrix;

    }
}
