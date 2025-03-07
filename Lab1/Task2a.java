package Lab1;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Task2a
{
    public static void main(String[] args)
    {
        System.out.println("Grid pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;

        //color
        int black;

        // Fixed ring width
        final int w = 20;

        try {
            image = ImageIO.read(new File(args[0]));  
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            return;
        }

        x_res = image.getWidth();
        y_res = image.getHeight();

       // Create packed RGB representation of black and white colors
       black = Task1a.int2RGB(0, 0, 0);

       // Find coordinates of the image center
       x_c = x_res / 2;
       y_c = y_res / 2;

       // Process the image, pixel by pixel
       for (i = 0; i < y_res; i++)
           for (j = 0; j < x_res; j++)
           {
               double d;
               int r;

               // Calculate distance to the image center
               d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

               // Find the ring index
               r = (int) d / w;

               // Make decision on the pixel color
               // based on the ring index
               if (r % 2 == 0)
                   // Even ring - set black color
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
