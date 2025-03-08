package Lab1;

import java.awt.image.*;

public class Task3a
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;
        
        // Circle radious 
        int r;

        // Distance between circle edges
        int d;

        // Colors
        int color_first, color_second;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());

        // Get radious and gap between circles from command line arguments
        r = Integer.parseInt(args[2].trim()); 
        d = Integer.parseInt(args[3].trim());
        
        // Set colors to black and white in this case
        color_first = Utils.int2RGB(0,0,0);
        color_second = Utils.int2RGB(255,255,255);

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
                // Distance from the center for x and y
                int dx,dy;

                // Distance from grid center for x and y
                int gx,gy;

                // Distance from grid center
                double dg;

                // Calculate distance to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                gx = dx % (2*r+d);
                gy = dy % (2*r+d);

                // if the distance is greater than the radius and the gap between circles it belongs to the next circle
                if (gx > r+d)
                    gx = 2*r+d-gx;
                if (gy > r+d)   
                    gy = 2*r+d-gy;

                // Calculate the distance from the center of the grid
                dg = Math.sqrt(gx*gx + gy*gy);

                // Make decision on the pixel color
                if (dg <= r)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        Utils.saveImage(image, args[4]);
    }
}

