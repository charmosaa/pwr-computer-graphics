package Lab1;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Lab3e
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
        int parts;

        //angle
        double angle;

        //colors
        int color_first, color_second;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());

        parts = Integer.parseInt(args[2].trim());
        

        color_first = int2RGB(0,0,0);
        color_second = int2RGB(255,255,255);

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);

         // Find coordinates of the image center
         x_c = x_res / 2;
         y_c = y_res / 2;

        angle = 360 / (double)parts;

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                // distance to the center  
                int dx,dy;

                // Calculate distance to the image center
                dx = j - x_c;
                dy = i - y_c;

                double theta = Math.atan2(dx, dy);
                if (theta < 0) {
                    theta += 2 * Math.PI; 
                }
                double degrees = Math.toDegrees(theta);

                int part_index = (int)(degrees / angle);

                // Make decision on the pixel color
                if (part_index % 2== 0)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        // Save the created image in a graphics file
        try
        {
            ImageIO.write(image, "bmp", new File(args[3]));
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

