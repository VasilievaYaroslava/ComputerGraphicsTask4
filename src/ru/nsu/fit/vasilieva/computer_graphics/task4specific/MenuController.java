package ru.nsu.fit.vasilieva.computer_graphics.task4specific;

import ru.nsu.fit.vasilieva.computer_graphics.filters.*;
import ru.nsu.fit.vasilieva.computer_graphics.utils.BMP24FileLoader;
import ru.nsu.fit.vasilieva.computer_graphics.view.GraphEditorWindow;
import ru.nsu.fit.vasilieva.computer_graphics.view.StandartGraphWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MenuController
{
    private GraphEditorWindow window;
    private BufferedImage image;
    private Color[][] imageMatrix;
    private BufferedImage workspaceImage;
    private Color[][] workspaceImageMatrix;
    private BufferedImage oldResultImage;
    private Color[][] oldResultImageMatrix;
    private BufferedImage resultImage;
    private Color[][] resultImageMatrix;
    private boolean selectionMode = false;
    private double step;
    private int width;
    private int height;
    private double k;

    private Constructor filterConstructor;

    private static int PICTURE_SIZE = 256;
    private static int BORDER_COLOR = 11906220;
    private static int BORDER_SIZE = 5;

    private MenuController()
    {
        try
        {
            workspaceImage = new BufferedImage(PICTURE_SIZE + BORDER_SIZE, PICTURE_SIZE + BORDER_SIZE, BufferedImage.TYPE_3BYTE_BGR);
            workspaceImageMatrix = new Color[PICTURE_SIZE][PICTURE_SIZE];
            window = new GraphEditorWindow("Лабораторная работа №4", new ru.nsu.fit.vasilieva.computer_graphics.task4specific.BarsCreator(this), this);
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

    public void openFile(File file) throws IOException
    {
        BMP24FileLoader fileLoader;
        fileLoader = new BMP24FileLoader(file);
        image = fileLoader.getPicture();
        imageMatrix = fileLoader.getPictureMatrix();

        width = image.getWidth();
        height = image.getHeight();
        int scaledWidth;
        int scaledHeight;


        if ((height > PICTURE_SIZE) || (width > PICTURE_SIZE))
        {
            if (height > width)
            {
                scaledHeight = PICTURE_SIZE;
                scaledWidth = (int) Math.round(width * (double) scaledHeight / height);
                step = (double) PICTURE_SIZE / height;
            }
            else
            {
                scaledWidth = PICTURE_SIZE;
                scaledHeight = (int) Math.round(height * (double) scaledWidth / width);
                step = (double) PICTURE_SIZE / width;
            }
            k = (double) scaledWidth / width;
            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2dScaledImage = scaledImage.createGraphics();
            g2dScaledImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2dScaledImage.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
            image = scaledImage;
        }
        else
        {
            k = 1;
            step = 1;
        }

        image = getViewImage(image);
        window.setImages(image, null, null);
    }

    public void saveFile()
    {
        if (workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Не выбран фрагмент, с которым идет работа");
            return;
        }
        String filename = (String) JOptionPane.showInputDialog(window, "Выберите имя файла для сохранения", "Сохранить файл", JOptionPane.PLAIN_MESSAGE);
        if ((filename != null) && (filename.length() > 0))
        {
            try
            {
                BMP24FileLoader.saveFile(workspaceImageMatrix, PICTURE_SIZE, filename);
            }
            catch (IOException e)
            {
                window.viewMessage("Ошибочка вышла", "Произошла ошибка при сохранении файла!");
            }
        }
    }

    public void setSelectionMode()
    {
        selectionMode = true;
    }

    public void selectArea(double x, double y)
    {
        if (image == null)
        {
            window.viewMessage("Ошибочка вышла", "Не выбран файл, с которым идет работа");
            return;
        }
        if (!selectionMode)
        {
            return;
        }
        int realX = (int) Math.floor((x - BORDER_SIZE) / step);
        int realY = (int) Math.floor((y - BORDER_SIZE) / step);

        if (realX < PICTURE_SIZE / 2)
        {
            realX = PICTURE_SIZE / 2;
        }
        if (realY < PICTURE_SIZE / 2)
        {
            realY = PICTURE_SIZE / 2;
        }
        if (realX > width - PICTURE_SIZE / 2 + 1 + BORDER_SIZE)
        {
            realX = width - PICTURE_SIZE / 2;
        }
        if (realY > height - PICTURE_SIZE / 2 + 1 + BORDER_SIZE)
        {
            realY = height - PICTURE_SIZE / 2;
        }
        int realX1 = realX - PICTURE_SIZE / 2;
        int realX2 = realX + PICTURE_SIZE / 2 - 1;
        int realY1 = realY - PICTURE_SIZE / 2;
        int realY2 = realY + PICTURE_SIZE / 2 - 1;

        int x1 = (int) Math.floor(realX1 * k);
        int x2 = (int) Math.floor(realX2 * k);
        int y1 = (int) Math.floor(realY1 * k);
        int y2 = (int) Math.floor(realY2 * k);

        BufferedImage imageSelectedArea = copyImage(image);
        for(int i = x1; i <= x2; ++i)
        {
            imageSelectedArea.setRGB(i + BORDER_SIZE, y1 + BORDER_SIZE, BORDER_COLOR);
            imageSelectedArea.setRGB(i + BORDER_SIZE, y2 + BORDER_SIZE, BORDER_COLOR);
        }
        for(int i = y1; i <= y2; ++i)
        {
            imageSelectedArea.setRGB(x1 + BORDER_SIZE, i + BORDER_SIZE,  BORDER_COLOR);
            imageSelectedArea.setRGB(x2 + BORDER_SIZE, i + BORDER_SIZE, BORDER_COLOR);
        }

        for (int i = 0; i < PICTURE_SIZE; ++i)
        {
            for (int j = 0; j < PICTURE_SIZE; ++j)
            {
                workspaceImageMatrix[i][j] = imageMatrix[realY1 + j][realX1 + i];
                workspaceImage.setRGB(i + BORDER_SIZE, j + BORDER_SIZE, imageMatrix[realY1 + j][realX1 + i].getRGB());
                //workspaceImage.setRGB(i, j, imageMatrix[realY1 + j][realX1 + i].getRGB());
            }
        }

        workspaceImage.flush();
        window.setImages(imageSelectedArea, workspaceImage, null);
    }

    public void filterGaussian()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }
        AbstractMatrixFilter filter = new GaussianMatrixFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void filterGrayscale()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }
        AbstractFormulaFilter filter = new GrayscaleFormulaFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void filterNegative()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }
        AbstractFormulaFilter filter = new NegativeFormuleFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }


    public void filterZoom()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }
        AbstractFilter filter = new ZoomFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void filterStamp()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }
        AbstractFormulaFilter filter = new GrayscaleFormulaFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        AbstractMatrixFilter filter2 = new StampMatrixFilter();
        resultImageMatrix = filter2.useFilter(resultImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter2.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void filterOrderedDithering()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        AbstractMatrixFilter filter = new OrderedDitheringMatrixFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void filterFloydSteinberg()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        oldResultImage = copyImage(resultImage);
        oldResultImageMatrix = resultImageMatrix;

        int[] parameters = new int[1];
        try
        {
            filterConstructor = FloydSteinbergFilter.class.getConstructor(parameters.getClass());
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        String [] titles = new String[3];
        titles[0] = "Красный";
        titles[1] = "Зеленый";
        titles[2] = "Синий";
        window.getParameters(3, titles, "Установите желаемое количество бит на цвет", 1);
    }

    public void filterBlackAndWhite()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        oldResultImage = copyImage(workspaceImage);
        oldResultImageMatrix = resultImageMatrix;

        int[] parameters = new int[1];
        try
        {
            filterConstructor = BlackAndWhiteFormulaFilter.class.getConstructor(parameters.getClass());
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        String [] titles = new String[1];
        titles[0] = "";
        window.getParameters(1, titles, "Установите коэффициент", 2);
    }

    public void filterSobel()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        oldResultImage = copyImage(workspaceImage);
        oldResultImageMatrix = workspaceImageMatrix;

        int[] parameters = new int[1];
        try
        {
            filterConstructor = SobellMatrixFilter.class.getConstructor(parameters.getClass());
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        String [] titles = new String[1];
        titles[0] = "";
        window.getParameters(1, titles, "Установите коэффициент", 2);
    }

    public void filterRoberts()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        oldResultImage = copyImage(workspaceImage);
        oldResultImageMatrix = workspaceImageMatrix;

        int[] parameters = new int[1];
        try
        {
            filterConstructor = RobertsMatrixFilter.class.getConstructor(parameters.getClass());
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        String [] titles = new String[1];
        titles[0] = "";
        window.getParameters(1, titles, "Установите коэффициент", 2);
    }

    public void filterWatercolor()
    {
        if (image == null || workspaceImageMatrix[0][0] == null)
        {
            window.viewMessage("Ошибочка вышла", "Фильтр можно применять, только выбрав изображение!");
            return;
        }

        AbstractFilter filter = new MedianFilter();
        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        AbstractMatrixFilter filter2 = new SharpnessMatrixFilter();
        resultImageMatrix = filter2.useFilter(resultImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter2.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    public void saveChanges()
    {
        if (resultImage == null)
        {
            return;
        }
        workspaceImage = resultImage;
        workspaceImageMatrix = resultImageMatrix;
        window.setImages(image, resultImage, resultImage);
    }

    public void undo()
    {
        resultImage = oldResultImage;
        resultImageMatrix = oldResultImageMatrix;
        window.setImages(image, workspaceImage, resultImage);
    }

    public void setParameters(int[] parameters)
    {
        AbstractFilter filter = null;
        try
        {
            filter = (AbstractFilter) filterConstructor.newInstance(parameters);
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        resultImageMatrix = filter.useFilter(workspaceImageMatrix, PICTURE_SIZE, PICTURE_SIZE);
        resultImage = getViewImage(filter.getImage());
        window.setImages(image, workspaceImage, resultImage);
    }

    private static BufferedImage copyImage(BufferedImage source)
    {
        if (source == null)
        {
            return null;
        }
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    private static BufferedImage getViewImage(BufferedImage source)
    {
        if (source == null)
        {
            return  null;
        }
        BufferedImage newImage = new BufferedImage(source.getWidth() + BORDER_SIZE, source.getHeight() + BORDER_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < source.getWidth(); ++i)
        {
            for (int j = 0; j < source.getHeight(); ++j)
            {
                newImage.setRGB(i + BORDER_SIZE, j + BORDER_SIZE, source.getRGB(i, j));
            }
        }
        newImage.flush();
        return newImage;
    }
}
