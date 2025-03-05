
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Lab1c
{
    public static void main(String[] args)
    {
        System.out.println("Grid pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Loop variables - indices of the current row and column
        int i, j;

        int tail_size;
        int color_first;
        int color_second;

        // Get defined chessboard parameters
        tail_size = Integer.parseInt(args[0].trim()); 

        
        color_first = int2RGB(Integer.parseInt(args[1].trim()),Integer.parseInt(args[2].trim()),Integer.parseInt(args[3].trim()));
        color_second = int2RGB(Integer.parseInt(args[4].trim()),Integer.parseInt(args[5].trim()),Integer.parseInt(args[6].trim()));

        //Calculate the size of the board
        x_res = y_res = 8 * tail_size;

        // Initialize an empty image, use pixel format
        // with RGB packed in the integer data type
        image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                int tail_index_x = j / tail_size;
                int tail_index_y = i / tail_size;

                // Make decision on the pixel color
                if (tail_index_x % 2 == tail_index_y % 2)
                    image.setRGB(j, i, color_first);
                else
                    image.setRGB(j, i, color_second);
            }

        // Save the created image in a graphics file
        try
        {
            ImageIO.write(image, "bmp", new File(args[7]));
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

