package Lab1;

import java.awt.image.*;

public class Task1c
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

        // Get defined chessboard parameters from command line arguments
        tail_size = Integer.parseInt(args[0].trim()); 
        color_first = Utils.int2RGB(Integer.parseInt(args[1].trim()),Integer.parseInt(args[2].trim()),Integer.parseInt(args[3].trim()));
        color_second = Utils.int2RGB(Integer.parseInt(args[4].trim()),Integer.parseInt(args[5].trim()),Integer.parseInt(args[6].trim()));

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

        Utils.saveImage(image, args[7]);
    }
}

