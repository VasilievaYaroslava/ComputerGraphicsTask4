package ru.nsu.fit.vasilieva.computer_graphics.filters;

import ru.nsu.fit.vasilieva.computer_graphics.filters.AbstractFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractFormulaFilter implements AbstractFilter
{
    protected BufferedImage image;
    protected int N;

    public BufferedImage getImage()
    {
        return image;
    }

    public Color[][] useFilter(Color [][] imageMatrix, int width, int heigth)
    {
        Color[][] newImage = new Color[heigth][width];
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < heigth; ++ i)
        {
            for (int j = 0; j < width; ++j)
            {
                int rgb = useFormula(imageMatrix[i][j].getRed(), imageMatrix[i][j].getGreen(), imageMatrix[i][j].getBlue());
                newImage[i][j] = new Color(rgb);
                image.setRGB(i, j, rgb);
            }
        }

        image.flush();
        return newImage;
    }


    protected int useFormula(int r, int g, int b)
    {
        return 0;
    }
}
