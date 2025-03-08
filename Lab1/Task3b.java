package Lab1;

import java.awt.image.*;

public class Task3b
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

        //diagonal of the square
        int d;

        //colors
        int color_first, color_second;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());

        // Get diagonal of the square from command line arguments
        d = Integer.parseInt(args[2].trim());
        
        //set colors to black and white in this case
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
                // distance to the center for x and y
                int dx,dy;

                //distance to the center of the grid for x and y
                int gx,gy;

                // Calculate distance to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                gx = dx % d;
                gy = dy % d;

                //if it's bigger than half the diagonal, we take the other half as it belongs to the next square
                if(gx > d/2)
                    gx = d-gx;
                if(gy > d/2)    
                    gy = d-gy;
                    
                // Make decision on the pixel color
                if (gy > gx)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        Utils.saveImage(image, args[3]);
    }
}

