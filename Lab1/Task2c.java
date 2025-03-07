package Lab1;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Task2c
{
    public static void main(String[] args)
    {
        System.out.println("Grid pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Loop variables - indices of the current row and column
        int i, j;

        int tail_size;
        int black;

        try {
            image = ImageIO.read(new File(args[0]));  
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            return;
        }

        black = Task1a.int2RGB(0,0,0);

        //Calculate the size of the board
        x_res = image.getWidth();
        y_res = image.getHeight();

        if(x_res < y_res)
            y_res = x_res;
        else
            x_res = y_res;

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = image.getSubimage(0, 0, x_res, y_res);

        tail_size = x_res / 8;

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                int tail_index_x = j / tail_size;
                int tail_index_y = i / tail_size;

                // Make decision on the pixel color
                if (tail_index_x % 2 == tail_index_y % 2)
                    image.setRGB(j, i, black);
            }

        // Save the created image in a graphics file
        try
        {
            ImageIO.write(image, "bmp", new File(args[1]));
            System.out.println("Grid image created successfully");
        }
        catch (IOException e)
        {
            System.out.println("The image cannot be stored");
        }
    }
}

