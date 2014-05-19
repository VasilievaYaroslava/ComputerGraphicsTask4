package ru.nsu.fit.vasilieva.computer_graphics.view;

import ru.nsu.fit.vasilieva.computer_graphics.task4specific.MenuController;
import ru.nsu.fit.vasilieva.computer_graphics.view.AbstractBarsCreator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GraphEditorWindow extends JFrame implements ChangeListener
{
    private BufferedImage sourceImage;
    private BufferedImage workspaceImage;
    private BufferedImage resultImage;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JSlider [] sliders;
    private MenuController controller;

    private static int BORDER_SIZE = 5;
    private static int PICTURE_SIZE = 256;

    public GraphEditorWindow(String title, AbstractBarsCreator creator, MenuController menuController)
    {
        super(title);
        controller = menuController;
        createEditorWindow(title, creator);
        setMouseAdapter(menuController);
    }

    private void createEditorWindow(String title, AbstractBarsCreator creator)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        panel1 = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g)
            {
                g.drawImage(sourceImage, 0, 0, null);
            }


        };

        panel2 = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g)
            {
                g.drawImage(workspaceImage, 0, 0, null);
            }

        };

        panel3 = new JPanel()
        {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g)
            {
                g.drawImage(resultImage, 0, 0, null);
            }

        };

        Dimension size = new Dimension(PICTURE_SIZE + 2 * BORDER_SIZE, PICTURE_SIZE + 2 * BORDER_SIZE);

        panel1.setPreferredSize(size);
        panel2.setPreferredSize(size);
        panel3.setPreferredSize(size);

        setVisible(true);
        setLayout(new BorderLayout());
        add(creator.createToolBar(), BorderLayout.BEFORE_FIRST_LINE);
        setJMenuBar(creator.createMenuBar());

        JPanel p = new JPanel();
        p.setBackground(Color.DARK_GRAY);
        Border b4 = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.darkGray, BORDER_SIZE),
                BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel1.setBorder(b4);
        panel2.setBorder(b4);
        panel3.setBorder(b4);

        getContentPane().add(panel3, BorderLayout.EAST);
        getContentPane().add(panel2, BorderLayout.CENTER);
        getContentPane().add(panel1, BorderLayout.WEST);

        setResizable(false);
        pack();
    }

    public void setImages(BufferedImage sourceImage, BufferedImage workspaceImage, BufferedImage resultImage)
    {
        this.sourceImage = sourceImage;
        this.workspaceImage = workspaceImage;
        this.resultImage = resultImage;
        repaint();
    }

    public void setSourceImage(BufferedImage sourceImage)
    {
        this.sourceImage = sourceImage;
        repaint();
    }

    /**
     * Views text message in individual window.
     *
     * @param title - the {@link String}, which is title of new window.
     * @param text - the {@link String}, which is text of message.
     */
    public static void viewStaticMessage(String title, String text)
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

    public int[] getParameters(int count, String[] descriptions, String title, int sliderType)
    {
        sliders = new JSlider [count];
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < count; ++i)
        {
            sliders[i] = createSlider(sliderType, descriptions[i]);
            panel.add(sliders[i]);
        }

        int [] results = new int[sliders.length];
        for (int i = 0; i < sliders.length; ++i)
        {
            results[i] = sliders[i].getValue();
        }
        controller.setParameters(results);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION)
        {
            controller.undo();
        }

        return null;
    }

    private JSlider createSlider(int type, String title)
    {
        JSlider slider = null;
        if (type == 1)
        {
            slider = new JSlider(1, 8, 8);
            slider.setMajorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setSnapToTicks(true);
            slider.setBorder(BorderFactory.createTitledBorder(title));
            slider.addChangeListener(this);
        }

        if (type == 2)
        {
            slider = new JSlider(0, 255, 15);
            slider.setMajorTickSpacing(64);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setSnapToTicks(false);
            slider.setBorder(BorderFactory.createTitledBorder(title));
            slider.addChangeListener(this);
        }

        return slider;
    }

    private void setMouseAdapter(final MenuController menuController)
    {
        MouseAdapter adapter = new MouseAdapter()
        {

            @Override
            public void mousePressed(MouseEvent e)
            {
                menuController.selectArea(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                menuController.selectArea(e.getX(), e.getY());
            }
        };
        panel1.addMouseListener(adapter);
        panel1.addMouseMotionListener(adapter);

    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        int [] results = new int[sliders.length];
        for (int i = 0; i < sliders.length; ++i)
        {
            results[i] = sliders[i].getValue();
        }
        controller.setParameters(results);
    }
}
