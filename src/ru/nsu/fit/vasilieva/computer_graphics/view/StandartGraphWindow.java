package ru.nsu.fit.vasilieva.computer_graphics.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import ru.nsu.fit.vasilieva.computer_graphics.enums.ZoomType;
import ru.nsu.fit.vasilieva.computer_graphics.graphs_specific.MenuController;

public class StandartGraphWindow extends JFrame
{
    private JPanel panel;
    private BufferedImage bufferedImage;
    private Object forSettings;
    private MenuController controller;
    private MouseAdapter adapter;

    private boolean dragndrop = true;
    private boolean mouseZoom = true;

    private SpinnerModel scaledSpinnerModel;
    private SpinnerModel movingSpinnerModel;
    private JCheckBox scaledCheckBox;
    private JCheckBox movingCheckBox;

    private int mouseCoordinateX;
    private int mouseCoordinateY;

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@link StandartGraphWindow}.
     *
     * @param title - the {@link String}, which is the name of this window.
     * @param creator  - {@link AbstractBarsCreator} which created toolbar and menubar for this window.
     */
    public StandartGraphWindow(String title, AbstractBarsCreator creator)
    {
        super(title);
        createStandartGraphWindow(title, creator);
    }

    /**
     * Constructs a {@link StandartGraphWindow}.
     *
     * @param title - the {@link String}, which is the name of this window.
     * @param creator   - {@link AbstractBarsCreator} which created toolbar and menubar for this window.
     * @param startScaledValue - start size of step of image scaling
     * @param startMovingValue - start size of step of moving picture
     * @param controller - the {@link MenuController} for feedback from active elements.
     */
    public StandartGraphWindow(String title, AbstractBarsCreator creator, int startScaledValue, int startMovingValue, MenuController controller)
    {
        super(title);
        this.controller = controller;
        createStandartGraphWindow(title, creator);

        createSetting(new Integer(startScaledValue), new Integer(startMovingValue));

        setMouseAdapter();
    }

    /**
     * Views text message about this program.
     */
    public void viewAbout()
    {
        viewMessage("О программе",
                "Данное приложение разработано в качестве лабораторной работы\n" +
                        "по курсу \"Компьютерная графика\" студенткой третьего курса\n" +
                        "Факультета Информационных Технологий\n" +
                        "Новосибирского Государственного университета\n" +
                        "Васильевой Ярославой Олеговной.");
    }

    /**
     * Gets a maximal size of image, which possibly to be set to this window.
     *
     * @return the {@link Dimension}, which contains size.
     */
    public Dimension getImageSize()
    {
        return panel.getSize();
    }

    /**
     * Sets picture to this window.
     *
     * @param picture - the {@link DrawableBufferedImage}, which enables to get image for setting.
     */
    public void setDrawable(DrawableBufferedImage picture)
    {
        bufferedImage = picture.getBufferedImage();
        repaint();
    }

    /**
     * Views text message about fail of program initialisation.
     */
    public static void initialisationFail()
    {
        viewStaticMessage("Ошибка", "Произошла ошибка при инициализации программы. Повреждены вспомогательные файлы приложения.");
    }

    /**
     * View dialog window for change settings by user.
     */
    public void viewSettings()
    {
        int result = JOptionPane.showConfirmDialog(null, forSettings, "Настройки", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            controller.settingsWasChanged((Integer) scaledSpinnerModel.getValue(), (Integer) movingSpinnerModel.getValue());


            if (!scaledCheckBox.isSelected())
            {
                if (mouseZoom)
                {
                    this.removeMouseWheelListener(adapter);
                }
            }
            else
            {
                if (!mouseZoom)
                {
                    this.addMouseWheelListener(adapter);
                }
            }
            mouseZoom = scaledCheckBox.isSelected();


            if (!movingCheckBox.isSelected())
            {
                if (dragndrop)
                {
                    panel.removeMouseListener(adapter);
                }
            }
            else
            {
                if (!dragndrop)
                {
                    panel.addMouseListener(adapter);
                }
            }
            dragndrop = movingCheckBox.isSelected();
        }
        if (result == JOptionPane.CANCEL_OPTION)
        {
            scaledCheckBox.setSelected(mouseZoom);
            movingCheckBox.setSelected(dragndrop);
            scaledSpinnerModel.setValue(controller.getScaleStep());
            movingSpinnerModel.setValue(controller.getMovingStep());
        }
    }

    /**
     * Views text message in individual window.
     *
     * @param title - the {@link String}, which is title of new window.
     * @param text - the {@link String}, which is text of message.
     */
    private static void viewStaticMessage(String title, String text)
    {
        JOptionPane.showMessageDialog(new JFrame(), text, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Views text message in individual window.
     *
     * @param title - the {@link String}, which is title of new window.
     * @param text - the {@link String}, which is text of message.
     */
    public void viewMessage(String title, String text)
    {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Sets value of step of scaling image.
     *
     * @param scaleStep - the value for setting.
     */
    public void setScaleStep(Integer scaleStep)
    {
        scaledSpinnerModel.setValue(scaleStep);
    }

    /**
     * Sets value of step of moving image.
     *
     * @param scaleStep - the value for setting.
     */
    public void setMovingStep(Integer movingStep)
    {
        movingSpinnerModel.setValue(movingStep);
    }

    private void createSetting(Integer startScaledValue, Integer startMovingValue)
    {
        JPanel scaled = new JPanel();
        JPanel moving = new JPanel();

        JPanel[] tmp = new JPanel[2];
        tmp[0] = scaled;
        tmp[1] = moving;
        forSettings = tmp;

        scaled.setLayout(new BorderLayout());
        scaledSpinnerModel = new SpinnerNumberModel();
        JSpinner scaledSpinner = new JSpinner(scaledSpinnerModel);
        scaledSpinner.setValue(startScaledValue);
        scaled.add(scaledSpinner, BorderLayout.EAST);
        scaled.add(new JLabel("Шаг"));
        scaledCheckBox = new JCheckBox("Включить прокрутку колесиком");
        scaled.add(scaledCheckBox, BorderLayout.SOUTH);
        scaled.setBorder(BorderFactory.createTitledBorder("Масштаб"));

        moving.setLayout(new BorderLayout());
        movingSpinnerModel = new SpinnerNumberModel();
        JSpinner movingSpinner = new JSpinner(movingSpinnerModel);
        movingSpinner.setValue(startMovingValue);
        moving.add(movingSpinner, BorderLayout.EAST);
        moving.add(new JLabel("Шаг"));
        movingCheckBox = new JCheckBox("Drag'n'drop");
        moving.add(movingCheckBox, BorderLayout.SOUTH);
        moving.setBorder(BorderFactory.createTitledBorder("Движение"));

        scaledCheckBox.setSelected(mouseZoom);
        movingCheckBox.setSelected(dragndrop);
    }

    private void createStandartGraphWindow(String title, AbstractBarsCreator creator)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension size = new Dimension(800, 600);
        setPreferredSize(size);
        setMinimumSize(size);
        setVisible(true);

        panel = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g)
            {
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };


        setLayout(new BorderLayout());
        add(creator.createToolBar(), BorderLayout.BEFORE_FIRST_LINE);
        setJMenuBar(creator.createMenuBar());
        add(panel, BorderLayout.CENTER);

        pack();
    }

    private void setMouseAdapter()
    {
        adapter = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mouseCoordinateX = e.getX();
                mouseCoordinateY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                controller.movePosition(mouseCoordinateX - e.getX(), mouseCoordinateY - e.getY());
                mouseCoordinateX = e.getX();
                mouseCoordinateY = e.getY();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0)
            {
                int count = arg0.getWheelRotation();
                int realX = arg0.getX();
                int realY = arg0.getY();
                controller.movePosition(-(panel.getSize().width / 2 - realX), -(panel.getSize().height / 2 - realY));

                if (count > 0)
                {
                    controller.zoom(ZoomType.OUT);
                }
                else
                {
                    controller.zoom(ZoomType.IN);
                }

                controller.movePosition((panel.getSize().width / 2 - realX), (panel.getSize().height / 2 - realY));

            }
        };
        panel.addMouseWheelListener(adapter);
        panel.addMouseListener(adapter);
        panel.addMouseMotionListener(adapter);

    }

}
