package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractMatrixFilter;
import ru.nsu.fit.vasilieva.computer_graphics.filters.BlackAndWhiteFormulaFilter;
import ru.nsu.fit.vasilieva.computer_graphics.filters.GrayscaleFormulaFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobellMatrixFilter extends AbstractMatrixFilter
{
    private int[][] matrix1;
    private int[][] matrix2;
    private int[] threshold;

    public SobellMatrixFilter (int[] parameters)
    {
        threshold = parameters;
        generateMatrix();
    }

    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth)
    {
        Color[][] newImage = new Color[heigth][width];

        for (int i = 0; i < heigth; ++ i)
        {
            for (int j = 0; j < width; ++j)
            {
                int r1 = 0, g1 = 0, b1 = 0;
                int r2 = 0, g2 = 0, b2 = 0;

                for(int k = 0; k < N; ++k)
                {
                    for(int k2 = 0; k2 < N; ++k2)
                    {
                        int i2 = i + k - 1;
                        int j2 = j + k2 - 1;
                        if (0 <= i2  && i2 < width && 0 <= j2 && j2 < heigth) 
                        {
                            r1 += imageMatrix[i2][j2].getRed() * matrix1[k][k2];
                            g1 += imageMatrix[i2][j2].getGreen() * matrix1[k][k2];
                            b1 += imageMatrix[i2][j2].getBlue() * matrix1[k][k2];
                            r2 += imageMatrix[i2][j2].getRed() * matrix2[k][k2];
                            g2 += imageMatrix[i2][j2].getGreen() * matrix2[k][k2];
                            b2 += imageMatrix[i2][j2].getBlue() * matrix2[k][k2];
                        }
                    }
                }

                int r = (int) Math.sqrt(r1 * r1 + r2 * r2);
                int g = (int) Math.sqrt(g1 * g1 + g2 * g2);
                int b = (int) Math.sqrt(b1 * b1 + b2 * b2);

                r = (int) correct(r);
                g = (int) correct(g);
                b = (int) correct(b);

                newImage[i][j] = new Color(r, g, b);
            }


        }
        GrayscaleFormulaFilter grey = new GrayscaleFormulaFilter();
        newImage = grey.useFilter(newImage, width, heigth);
        BlackAndWhiteFormulaFilter blackAndWhite = new BlackAndWhiteFormulaFilter(threshold);
        newImage = blackAndWhite.useFilter(newImage, width, heigth);
        image = blackAndWhite.getImage();

        return newImage;
    }

    @Override
    public BufferedImage getImage()
    {
        return image;
    }

    protected void generateMatrix()
    {
        N = 3;
        matrix1 = new int[N][N];
        matrix1[0][0] = -1;
        matrix1[0][1] = 0;
        matrix1[0][2] = 1;
        matrix1[1][0] = -2;
        matrix1[1][1] = 0;
        matrix1[1][2] = 2;
        matrix1[2][0] = -1;
        matrix1[2][1] = 0;
        matrix1[2][2] = 1;

        matrix2 = new int[N][N];
        matrix2[0][0] = 1;
        matrix2[0][1] = 2;
        matrix2[0][2] = 1;
        matrix2[1][0] = 0;
        matrix2[1][1] = 0;
        matrix2[1][2] = 0;
        matrix2[2][0] = -1;
        matrix2[2][1] = -2;
        matrix2[2][2] = -1;
    }
}
