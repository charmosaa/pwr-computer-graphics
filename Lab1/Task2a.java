package Lab1;

import java.awt.image.*;

public class Task2a
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;

        //color
        int black = Utils.int2RGB(0, 0, 0);

        // Fixed ring width
        final int w = 30;

        image = Utils.loadImage(args[0]);

        x_res = image.getWidth();
        y_res = image.getHeight();

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

         Utils.saveImage(image, args[1]);
        
    }

}
