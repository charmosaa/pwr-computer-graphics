package Lab1;

import java.awt.image.*;

public class Task4b
{
    public static void main(String[] args)
    {
        System.out.println("Grid pattern synthesis");

        BufferedImage image1, image2;

        // Image resolution
        int x_res, y_res;

        // center coordinates
        int x_c, y_c;

        // Loop variables - indices of the current row and column
        int i, j;

        // Grid parameters
        int grid_width;
        int grid_x_distance;
        int grid_y_distance;

        image1 = Utils.loadImage(args[0]);
        image2 = Utils.loadImage(args[1]);

        x_res = image1.getWidth();
        y_res = image1.getHeight();

        if(x_res != image2.getWidth() || y_res != image2.getHeight())
        {
            System.out.println("Images are not the same size");
            return;
        }

        // Get defined grid parameters from command line arguments
        grid_width = Integer.parseInt(args[2].trim());
        grid_x_distance = Integer.parseInt(args[3].trim()); 
        grid_y_distance = Integer.parseInt(args[4].trim()); 

        // Find coordinates of the image center
        x_c = x_res / 2;
        y_c = y_res / 2;

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                // Distance to the image center in x and y axis
                double dx, dy;

                // Calculate distance to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                // Make decision on the pixel color based on the ring index
                if (dx % (grid_x_distance + grid_width) > grid_x_distance/2 && dx % (grid_x_distance + grid_width) <= grid_x_distance/2+grid_width)
                    image1.setRGB(j, i, image2.getRGB(j, i));

                else if (dy % (grid_y_distance + grid_width) > grid_y_distance/2 && dy % (grid_y_distance + grid_width) <= grid_y_distance/2+grid_width)
                    image1.setRGB(j, i, image2.getRGB(j, i));
            }

        Utils.saveImage(image1, args[5]);
    }

}
