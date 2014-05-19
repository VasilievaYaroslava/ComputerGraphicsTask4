package ru.nsu.fit.vasilieva.computer_graphics.graphs_specific;

import java.awt.Dimension;

import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.ControllerDrawParametricСurve;
import ru.nsu.fit.vasilieva.computer_graphics.action_controllers.Drawable;
import ru.nsu.fit.vasilieva.computer_graphics.enums.Direction;
import ru.nsu.fit.vasilieva.computer_graphics.enums.ZoomType;
import ru.nsu.fit.vasilieva.computer_graphics.view.DrawableBufferedImage;
import ru.nsu.fit.vasilieva.computer_graphics.view.StandartGraphWindow;

public class MenuController
{
    protected static int START_SCALED_VALUE = 5;
    protected static int START_MOVING_VALUE = 50;
    protected static int MAX_SCALED_VALUE = 20;
    protected static int MAX_MOVING_VALUE = 500;
    protected static int MIN_SCALED_VALUE = 0;
    protected static int MIN_MOVING_VALUE = 0;
    protected static int DEFAULT_LENGTH = 6;
    protected static int DEFAUTL_TENSION = 5;

    protected StandartGraphWindow window;
    private ControllerDrawParametricСurve contr;

    protected int scaleStep = START_SCALED_VALUE;
    protected int movingStep = START_MOVING_VALUE;

    protected int length = DEFAULT_LENGTH;
    protected Dimension position;

    protected MenuController()
    {
        try
        {
            window = new StandartGraphWindow("Лабораторная работа №2", new BarsCreator(this), START_SCALED_VALUE, START_MOVING_VALUE, this);
        }
        catch (Exception e)
        {
            StandartGraphWindow.initialisationFail();
            e.printStackTrace();
        }


        window.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updatePicture();
            }
        });

        position = new Dimension(0, 0);

        contr = new ControllerDrawParametricСurve();

        updatePicture();
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
     * Changes the {@link Dimension} which contains coordinates of the upper left corner of display on the coordinate plane.
     *
     * @param direction - the direction of point's shift.
     */
    public void movePosition(Direction direction)
    {
        switch (direction)
        {
            case DOWN:
                position = new Dimension(position.width, position.height + movingStep);
                break;
            case LEFT:
                position = new Dimension(position.width - movingStep, position.height);
                break;
            case RIGHT:
                position = new Dimension(position.width + movingStep, position.height);
                break;
            case UP:
                position = new Dimension(position.width, position.height - movingStep);
                break;
        }
        updatePicture();
    }

    /**
     * Changes the {@link Dimension} which contains coordinates of the upper left corner of display on the coordinate plane.
     *
     * @param width - the change X coordinate of the upper left corner of display.
     * @param height - the change Y coordinate of the upper left corner of display.
     */
    public void movePosition(int width, int height)
    {
        position = new Dimension(position.width + width, position.height + height);
        updatePicture();
    }

    /**
     * Zooms in or zooms out picture.
     *
     * @param type - the type of zoom picture.
     */
    public void zoom(ZoomType type)
    {
        if (type == ZoomType.IN)
        {
            if (length * scaleStep * DEFAUTL_TENSION < 0)
            {
                return;
            }
            length = length * scaleStep;

            position = new Dimension(position.width * scaleStep, position.height * scaleStep);
        }
        else
        {
            if (length / scaleStep == 0)
            {
                return;
            }
            position = new Dimension(position.width / scaleStep, position.height / scaleStep);
            length = length / scaleStep;
        }
        updatePicture();
    }

    /**
     * View dialog window for change settings by user.
     *
     */
    public void changeSettings()
    {
        window.viewSettings();
    }

    /**
     * Saves change settings of size of scale step and moving step.
     *
     * @param scaleStep - the new value of step of image scaling.
     * @param movingStep - the new value of step of moving picture.
     */
    public void settingsWasChanged(int scaleStep, int movingStep)
    {
        if (scaleStep != this.scaleStep)
        {
            if ((scaleStep > MAX_SCALED_VALUE) || (scaleStep <  MIN_SCALED_VALUE))
            {
                window.viewMessage("Ошибка", "Выбрано некорректное значение для шага изменения масштаба.");
                window.setScaleStep(this.scaleStep);
            }
            this.scaleStep = scaleStep;
        }
        if (movingStep != this.movingStep)
        {
            if ((movingStep > MAX_MOVING_VALUE) || (movingStep <  MIN_MOVING_VALUE))
            {
                window.viewMessage("Ошибка", "Выбрано некорректное значение для шага передвижения по графику.");
                window.setMovingStep(movingStep);
            }
            this.movingStep = movingStep;
        }
    }

    /**
     * Gets size of step of image scaling.
     *
     * @return the size of step of image scaling.
     */
    public int getScaleStep()
    {
        return scaleStep;
    }

    /**
     * Gets size of step of moving the picture.
     *
     * @return the size of step of moving the picture.
     */
    public int getMovingStep()
    {
        return movingStep;
    }


    /**
     * Draws parametric curve on main window of this program.
     */

    private void drawParametricCurve()
    {
        Dimension size = window.getImageSize();
        int width = size.width;
        int height = size.height;
        Drawable picture = new DrawableBufferedImage(width, height);
        ((ControllerDrawParametricСurve)contr).drawCurve(picture, DEFAUTL_TENSION * length, position);
        window.setDrawable((DrawableBufferedImage) picture);
    }


    /**
     * Updates picture in main window of this program.
     */
    public void updatePicture()
    {
        drawParametricCurve();
    }


    /**
     * Changes the length of the unit interval to default length. Changes position of upper left corner of display to default.
     */
    public void resetChanges()
    {
        position = new Dimension(0, 0);
        length = DEFAULT_LENGTH;
        updatePicture();
    }
}
