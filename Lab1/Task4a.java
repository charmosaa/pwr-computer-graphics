package Lab1;

import java.awt.image.*;
public class Task4a
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image1, image2;

        // Image resolution
        int x_res, y_res;

        // center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;
        
        // Fixed ring width
        final int w = 20;

        image1 = Utils.loadImage(args[0]);
        image2 = Utils.loadImage(args[1]);

        x_res = image1.getWidth();
        y_res = image1.getHeight();

        if(x_res != image2.getWidth() || y_res != image2.getHeight())
        {
            System.out.println("Images are not the same size");
            return;
        }

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
               if (r % 2 == 0)
                   image1.setRGB(j, i, image2.getRGB(j, i));
           }

        Utils.saveImage(image1, args[2]);
    }

}
