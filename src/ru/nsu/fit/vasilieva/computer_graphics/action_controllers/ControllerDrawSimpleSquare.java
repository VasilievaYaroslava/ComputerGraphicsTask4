package ru.nsu.fit.vasilieva.computer_graphics.action_controllers;
import java.awt.Color;


public class ControllerDrawSimpleSquare implements ControllerGraphicAction
{
    /**Paints cyan square of 100 * 100 pixels
     * at a distance of 100 pixels in width and height
     * from the upper left angle of drawing field.
     *
     * @param picture - the  {@link Drawable}, which is field for draw.
     */
    public void paintSimpleSquare(Drawable picture)
    {
        picture.setColor(Color.CYAN);
        for(int i = 100; i <= 200; ++i)
        {
            for(int j = 100; j <= 200; ++j)
            {
                picture.setPixel(i, j);
            }
        }
    }
}
