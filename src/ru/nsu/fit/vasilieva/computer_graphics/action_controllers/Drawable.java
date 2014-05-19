package ru.nsu.fit.vasilieva.computer_graphics.action_controllers;
import java.awt.Color;


public interface Drawable
{
    /**
     * Sets a pixel in this {@link Drawable}
     *
     * @param x - the X coordinate of the pixel to set
     * @param y - the Y coordinate of the pixel to set
     */
    public void setPixel(int x, int y);

    /**
     * Sets a color, which will be used for setting pixel on this {@link Drawable}.
     *
     * @param color - the {@link Color} for setting it as default color.
     */
    public void setColor(Color color);

    /**
     * Sets the text of selected position of this {@link Drawable}.
     *
     * @param text - the {@link String} to be set.
     * @param color - the color for drawing text.
     * @param x -  the X coordinate of the text to set
     * @param y -  the Y coordinate of the text to set
     */
    public void setText(String text, Color color, int x, int y);

    /**
     * Returns the width of this {@link Drawable}.
     * @return the width of this {@link Drawable}
     */
    public int getWidth();

    /**
     * Returns the height of this {@link Drawable}.
     * @return the height of this {@link Drawable}
     */
    public int getHeight();
}