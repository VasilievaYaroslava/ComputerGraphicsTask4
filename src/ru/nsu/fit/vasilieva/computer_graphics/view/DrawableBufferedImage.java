package ru.nsu.fit.vasilieva.computer_graphics.view;

import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;


public class DrawableBufferedImage implements Drawable
{
    private BufferedImage image;
    private int rgb = 0;
    private int width;
    private int height;

    /**
     * Constructs a {@link DrawableBufferedImage} of one of the {@link Drawable} types.
     * It used {@link BufferedImage} for storing image.
     *
     * @param width - the width of created image.
     * @param height - the height of created image
     */
    public DrawableBufferedImage(int width, int height)
    {
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    @Override
    public void setPixel(int x, int y)
    {
        image.setRGB(x, y, rgb);
    }

    @Override
    public void setColor(Color color)
    {
        rgb = color.getRGB();
    }

    /**
     * Gets {@link BufferedImage}, which contains image created by this {@link DrawableBufferedImage}.
     *
     * @return {@link BufferedImage}, which contains image created by this {@link DrawableBufferedImage}.
     */
    public BufferedImage getBufferedImage()
    {
        image.flush();
        return image;
    }

    @Override
    public void setText(String text, Color color, int x, int y)
    {
        Graphics g = image.getGraphics();
        Color c = g.getColor();
        g.setColor(color);
        g.drawString(text, x, y + 10);
        g.setColor(c);
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

}
