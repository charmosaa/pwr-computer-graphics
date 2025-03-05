
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Lab1b
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

        int grid_width;
        int grid_x_distance;
        int grid_y_distance;

        int color_grid;
        int color_background;


        // Get required image resolution from command line arguments
        x_res = Integer.parseInt(args[0].trim());
        y_res = Integer.parseInt(args[1].trim());

        // Get defined grid parameters
        grid_width = Integer.parseInt(args[2].trim());
        grid_x_distance = Integer.parseInt(args[3].trim()); 
        grid_y_distance = Integer.parseInt(args[4].trim()); 
        color_grid = int2RGB(Integer.parseInt(args[5].trim()),Integer.parseInt(args[6].trim()),Integer.parseInt(args[7].trim()));
        color_background = int2RGB(Integer.parseInt(args[8].trim()),Integer.parseInt(args[9].trim()),Integer.parseInt(args[10].trim()));

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
                double dx;
                double dy;

                // Calculate distance to the image center
                dx = Math.abs(j - x_c);
                dy = Math.abs(i - y_c);

                // Make decision on the pixel color
                // based on the ring index
                if (dx % (grid_x_distance + grid_width) > grid_x_distance/2 && dx % (grid_x_distance + grid_width) <= grid_x_distance/2+grid_width)
                    // Even ring - set black color
                    image.setRGB(j, i, color_grid);
                else if (dy % (grid_y_distance + grid_width) > grid_y_distance/2 && dy % (grid_y_distance + grid_width) <= grid_y_distance/2+grid_width)
                    // Even ring - set black color
                    image.setRGB(j, i, color_grid);
                else
                    // Odd ring - set white color
                    image.setRGB(j, i, color_background);
            }

        // Save the created image in a graphics file
        try
        {
            ImageIO.write(image, "bmp", new File(args[11]));
            System.out.println("Grid image created successfully");
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
