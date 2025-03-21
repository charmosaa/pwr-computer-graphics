package Lab1;

import java.awt.image.*;

public class Task3d
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

        //ring width
        int w;

        //colors
        int color_first, color_second;

        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());


        // Get defined parameters
        r = Integer.parseInt(args[2].trim()); 
        w = Integer.parseInt(args[3].trim());
        
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

                //grid center distances for x and y
                int gx,gy;

                //distance from grid center
                double dg;

                //ring index
                int ring_index;

                // Calculate distance x and y to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                gx = dx % (2*r);
                gy = dy % (2*r);

                //for the case when the distance is greater than the radius it belongs to another circle
                if (gx > r)
                    gx = 2*r-gx;
                if (gy > r)   
                    gy = 2*r-gy;

                //calculate the distance from the center of the grid
                dg = Math.sqrt(gx*gx + gy*gy);
                
                //find the ring index 
                ring_index = (int)dg / w;

                // Make decision on the pixel color
                if (ring_index % 2 == 0)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        Utils.saveImage(image, args[4]);
    }
}

