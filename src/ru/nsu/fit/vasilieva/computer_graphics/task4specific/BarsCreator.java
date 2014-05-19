package ru.nsu.fit.vasilieva.computer_graphics.task4specific;


import ru.nsu.fit.vasilieva.computer_graphics.view.AbstractBarsCreator;
import ru.nsu.fit.vasilieva.computer_graphics.view.GraphEditorWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BarsCreator implements AbstractBarsCreator
{
    private JMenuBar menubar;
    private JToolBar toolbar;

    /**
     * Constructs a {@link BarsCreator}.
     *
     * @param controller - the {@link ru.nsu.fit.vasilieva.computer_graphics.task1specific.MenuController}. Its methods will be called after user's actions.
     */
    public BarsCreator(final ru.nsu.fit.vasilieva.computer_graphics.task4specific.MenuController controller)
    {
        toolbar = new JToolBar("");
        toolbar.setFloatable(false);
        menubar = new JMenuBar();
        toolbar.setBorderPainted(true);
        menubar.setBorderPainted(true);

        ToolTipManager.sharedInstance().setInitialDelay(0);

        JMenu file = new JMenu("Файл");
        addItem(file, "Открыть", "icons/OpenFile.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileopen = new JFileChooser();
                fileopen.setCurrentDirectory(new File("res"));
                int ret = fileopen.showDialog(null, "Выбрать картинку");
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        controller.openFile(fileopen.getSelectedFile());
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                        GraphEditorWindow.viewStaticMessage("Ошибочка вышла!", "Произошли проблемы с открытием файла. Может, был выбран неправильный формат?");
                    }
                }
            }
        });
        addItem(file, "Сохранить", "icons/SaveFile.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.saveFile();

            }
        });
        addItem(file, "Выход", "icons/Close.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.closeProgram();

            }
        });

        JMenu filters = new JMenu("Фильтры");
        addItem(filters, "Акварель", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterWatercolor();
            }
        }, "Акварель");
        addItem(filters, "Черно-белое", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterBlackAndWhite();
            }
        }, "Черно-белое");
        addItem(filters, "Увеличение", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterZoom();
            }
        }, "Увеличение");
        addItem(filters, "Флод-Стейнберг", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterFloydSteinberg();
            }
        }, "Флод-Стейнберг");
        addItem(filters, "Размытие по Гауссу", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterGaussian();
            }
        }, "Размытие по Гауссу");
        addItem(filters, "Оттенки серого", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterGrayscale();
            }
        }, "Оттенки серого");
        addItem(filters, "Негатив", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterNegative();
            }
        }, "Негатив");
        addItem(filters, "Ordered dithering", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterOrderedDithering();
            }
        }, "Ordered dithering");
        addItem(filters, "Робертс", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterRoberts();
            }
        }, "Робертс");
        addItem(filters, "Собель", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterSobel();
            }
        }, "Собель");
        addItem(filters, "Штамп", "icons/Filter.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.filterStamp();
            }
        }, "Штамп");

        JMenu actions = new JMenu("Действия");
        addItem(actions, "Выделить область", "icons/AboutProgram.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setSelectionMode();

            }
        });
        addItem(actions, "Сохранить результат", "icons/AboutProgram.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveChanges();

            }
        });


        JMenu about = new JMenu("Справка");
        addItem(about, "О программе", "icons/AboutProgram.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.aboutProgram();

            }
        });

        menubar.add(file);
        menubar.add(filters);
        menubar.add(actions);
        menubar.add(about);


    }

    @Override
    public JToolBar createToolBar()
    {
        return toolbar;
    }

    @Override
    public JMenuBar createMenuBar()
    {
        return menubar;
    }


    private void  addItem(JMenu parent, String name, String picture, ActionListener l)
    {
        JMenuItem newItem = new JMenuItem(name);
        JButton button = new JButton();
        button.addActionListener(l);
        ImageIcon image = new ImageIcon(getClass().getResource(picture));
        button.setIcon(new ImageIcon(image.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        toolbar.add(button);
        newItem.addActionListener(l);
        parent.add(newItem);
    }

    private void  addItem(JMenu parent, String name, String picture, ActionListener l, String prompt)
    {
        JMenuItem newItem = new JMenuItem(name);
        JButton button = new JButton();
        button.addActionListener(l);
        ImageIcon image = new ImageIcon(getClass().getResource(picture));
        button.setIcon(new ImageIcon(image.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
        button.setToolTipText(prompt);
        toolbar.add(button);
        newItem.addActionListener(l);
        parent.add(newItem);
    }

}
