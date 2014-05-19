package ru.nsu.fit.vasilieva.computer_graphics.view;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;


public interface AbstractBarsCreator
{
    /**
     * Creates the {@link JToolBar}, which contains all buttons for current task, for insert in to window.
     *
     * @return the {@link JToolBar}.
     */
    public JToolBar createToolBar();

    /**
     * Creates the {@link JMenuBar}, which is suitable for current task.
     *
     * @return the {@link JMenuBar}.
     */
    public JMenuBar createMenuBar();

}
