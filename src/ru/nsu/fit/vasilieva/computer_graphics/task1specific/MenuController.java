package ru.nsu.fit.vasilieva.computer_graphics.task1specific;

import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.ControllerDrawSimpleSquare;
import ru.nsu.fit.vasilieva.computer_graphics.view.DrawableBufferedImage;
import ru.nsu.fit.vasilieva.computer_graphics.view.StandartGraphWindow;

import java.awt.*;

public class MenuController
{
    private StandartGraphWindow window;

    public MenuController()
    {
        try
        {
            window = new StandartGraphWindow("Лабораторная работа №1", new BarsCreator(this));
        }
        catch (Exception e)
        {
            StandartGraphWindow.initialisationFail();
            e.printStackTrace();
        }
    }

    /**
     * Start function.
     *
     * @param args - the array of {@link String}, which are the programs arguments.
     */
    public static void main (String args[])
    {
        MenuController controller = new MenuController();
    }

    /**
     * Views text message about this program.
     */
    public void aboutProgram()
    {
        window.viewAbout();
    }

    /**
     * Closes this program.
     */
    public void closeProgram()
    {
        System.exit(0);
    }

    /**
     * Draws square on main window of this program.
     */
    public void drawSimpleSquare()
    {
        ControllerDrawSimpleSquare contr = new ControllerDrawSimpleSquare();
        Dimension d = window.getSize();

        DrawableBufferedImage image = new DrawableBufferedImage(d.width, d.height);
        contr.paintSimpleSquare(image);
        window.setDrawable(image);
    }
}
