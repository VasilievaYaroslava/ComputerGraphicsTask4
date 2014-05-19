package ru.nsu.fit.vasilieva.computer_graphics.graphs_specific;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import ru.nsu.fit.vasilieva.computer_graphics.enums.Direction;
import ru.nsu.fit.vasilieva.computer_graphics.enums.ZoomType;
import ru.nsu.fit.vasilieva.computer_graphics.view.AbstractBarsCreator;

public class BarsCreator implements AbstractBarsCreator
{
    private JMenuBar menubar;
    private JToolBar toolbar;

    /**
     * Constructs a {@link BarsCreator}.
     *
     * @param controller - the {@link MenuController}. Its methods will be called after user's actions.
     */
    public BarsCreator(final MenuController controller)
    {
        toolbar = new JToolBar("");
        toolbar.setFloatable(false);
        menubar = new JMenuBar();
        toolbar.setBorderPainted(true);
        menubar.setBorderPainted(true);

        JMenu file = new JMenu("Файл");
        addItem(file, "Выход", "icons/Close.png", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.closeProgram();

            }
        });


        JMenu moving = new JMenu("Перемещение");
        addItem(moving, "Вверх", "icons/Up.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.movePosition(Direction.UP);
            }
        });
        addItem(moving, "Вниз", "icons/Down.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.movePosition(Direction.DOWN);
            }
        });
        addItem(moving, "Влево", "icons/Left.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.movePosition(Direction.LEFT);
            }
        });
        addItem(moving, "Вправо", "icons/Right.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.movePosition(Direction.RIGHT);
            }
        });

        JMenu zoom = new JMenu("Масштаб");
        addItem(zoom, "Увеличить", "icons/ZoomIn.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.zoom(ZoomType.IN);
            }
        });
        addItem(zoom, "Уменьшить", "icons/ZoomOut.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.zoom(ZoomType.OUT);
            }
        });

        JMenu actions = new JMenu("Действия");
        addItem(actions, "Сбросить изменения", "icons/Reset.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.resetChanges();
            }
        });

        JMenu settings = new JMenu("Настройки");
        addItem(settings, "Изменить", "icons/Settings.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeSettings();
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
        menubar.add(moving);
        menubar.add(zoom);
        menubar.add(settings);
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
        button.setIcon(new ImageIcon(image.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));;
        toolbar.add(button);
        newItem.addActionListener(l);
        parent.add(newItem);
    }

}
