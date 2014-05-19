package ru.nsu.fit.vasilieva.computer_graphics.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class BMP24FileLoader
{
    private int width;
    private int height;
    private long fileSize;
    private BufferedImage picture;
    private Color[][] pictureMatrix;
    private static int fileTitleLength = 54;

    public BMP24FileLoader(File file) throws IOException
    {
        InputStream inputStream = new FileInputStream(file);
        int offset = 0;
        byte[] bytes = new byte[fileTitleLength];
        offset = inputStream.read(bytes, offset, fileTitleLength);
        {
            System.out.println(fromBytes(bytes[2], bytes[3], bytes[4], bytes[5]));
            System.out.println(file.length());
            System.out.println(fromBytes(bytes[5], bytes[4], bytes[3], bytes[2]));
        }

        if (((char)bytes[0] != 'B') || ((char)bytes[1] != 'M') || (fromBytes(bytes[5], bytes[4], bytes[3], bytes[2]) != file.length()))
        {
            throw new IllegalArgumentException();
        }

        if ((fromBytes(bytes[7], bytes[6]) != 0) || (fromBytes(bytes[9], bytes[8]) != 0))
        {
            throw new IllegalArgumentException();
        }

        offset = fromBytes(bytes[13], bytes[12], bytes[11], bytes[10]);
        if (offset != fileTitleLength)
        {
            throw new IllegalArgumentException();
        }

        if (fromBytes(bytes[17], bytes[16], bytes[15], bytes[14]) != 40)
        {
            throw new IllegalArgumentException();
        }

        width = fromBytes(bytes[21], bytes[20], bytes[19], bytes[18]);
        height = fromBytes(bytes[25], bytes[24], bytes[23], bytes[22]);

        if ((fromBytes(bytes[27], bytes[26]) != 1) || (fromBytes(bytes[29], bytes[28]) != 24) || (fromBytes(bytes[33], bytes[32], bytes[31], bytes[30]) != 0))
        {
            throw new IllegalArgumentException();
        }

        picture = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        pictureMatrix = new Color[height][width];

        int lineLength;

        if ((width * 3 % 4) == 0)
        {
            lineLength = width * 3;
        }
        else
        {
            lineLength = width * 3 + 4 - (width * 3 % 4);
        }

        for (int i = height - 1; i >= 0; --i)
        {
            byte[] bytesLine = new byte[lineLength];
            int countRead;
            countRead = inputStream.read(bytesLine, 0, lineLength);
            if (countRead != lineLength)
            {
                throw new IllegalArgumentException();
            }

            offset += lineLength;
            int start = 0;
            for (int j = 0; j < width; ++j)
            {
                int rgb = fromBytes(bytesLine[start + 2], bytesLine[start + 1], bytesLine[start]);
                start += 3;
                pictureMatrix[i][j] = new Color(rgb);
                picture.setRGB(j, i, rgb);
            }
        }
        picture.flush();
    }

    public Color[][] getPictureMatrix()
    {
        return pictureMatrix;
    }

    public BufferedImage getPicture()
    {
        return picture;
    }

    static public void saveFile(Color[][] matrix, int pictureSize, String filename) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream("res/" + filename);

        byte[] title = new byte[54];
        title[0] = (byte) 'B';
        title[1] = (byte) 'M';

        System.out.println("S " + (pictureSize * pictureSize * 3 + 54));

        toBytes(pictureSize * pictureSize * 3 + 54, title, 2, 4);
        toBytes(0, title, 6, 2);
        toBytes(0, title, 8, 2);
        toBytes(54, title, 10, 4);

        toBytes(40, title, 14, 4);
        toBytes(pictureSize, title, 18, 4);
        toBytes(pictureSize, title, 22, 4);
        toBytes(1, title, 26, 2);
        toBytes(24, title, 28, 2);
        toBytes(0, title, 30, 4);
        toBytes(0, title, 6, 2);

        outputStream.write(title);

        for (int i = pictureSize - 1; i >= 0; --i)
        {
            byte [] bytes = new byte[pictureSize * 3];
            int count = 0;
            for (int j = 0; j < pictureSize; ++j)
            {
                toBytes(matrix[j][i].getRGB(), bytes, count, 3);
                count += 3;
            }
            outputStream.write(bytes);
        }

        outputStream.flush();
    }

    private int fromBytes(byte b1, byte b2, byte b3, byte b4)
    {
        return  ((b1 & 0xFF) << 24) + ((b2 & 0xFF) << 16) + ((b3 & 0xFF) << 8) + (b4 & 0xFF);
    }

    private int fromBytes(byte b1, byte b2, byte b3)
    {
        return  ((b1 & 0xFF) << 16) + ((b2 & 0xFF) << 8) + (b3 & 0xFF);
    }

    private int fromBytes(byte b1, byte b2)
    {
        return  ((b1 & 0xFF) << 8) + (b2 & 0xFF);
    }

    private static void toBytes(int n, byte[] bytes, int startNumber, int count)
    {

        for (int i = 0; i < count; ++i)
        {
            bytes[startNumber + count - 1 - i] = (byte) ((n >> ((count - 1 - i) * 8)));
        }
    }
}
