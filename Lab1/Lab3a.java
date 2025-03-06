package Lab1;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Lab3a
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        //center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;
        
        //circle radious 
        int r;

        //distance between circle edges
        int d;

        //colors
        int color_first, color_second;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());


        // Get defined parameters
        r = Integer.parseInt(args[2].trim()); 
        d = Integer.parseInt(args[3].trim());
        

        color_first = int2RGB(0,0,0);
        color_second = int2RGB(255,255,255);

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);

         // Find coordinates of the image center
         x_c = x_res / 2;
         y_c = y_res / 2;

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                int dx,dy;
                //grid indexes
                int ix,iy;

                //distance from grid center
                double dg;

                // Calculate distance to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                ix = dx % (2*r+d);
                iy = dy % (2*r+d);

                if (ix > r+d)
                    ix = 2*r+d-ix;
                if (iy > r+d)   
                    iy = 2*r+d-iy;

                dg = Math.sqrt(ix*ix + iy*iy);

                // Make decision on the pixel color
                if (dg <= r)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        // Save the created image in a graphics file
        try
        {
            ImageIO.write(image, "bmp", new File(args[4]));
            System.out.println("Image created successfully");
        }
        catch (IOException e)
        {
            System.out.println("The image cannot be stored");
        }
    }

    // This method assembles RGB color intensities into single
    // packed integer. Arguments must be in <0..255> range
    static int int2RGB(int red, int green, int blue)
    {
        // Make sure that color intensities are in 0..255 range
        red = red & 0x000000FF;
        green = green & 0x000000FF;
        blue = blue & 0x000000FF;

        // Assemble packed RGB using bit shift operations
        return (red << 16) + (green << 8) + blue;
    }
}

