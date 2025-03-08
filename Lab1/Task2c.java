package Lab1;

import java.awt.image.*;

public class Task2c
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image;

        // Image resolution
        int x_res, y_res;

        // Loop variables - indices of the current row and column
        int i, j;

        // size of each part of the chessboard
        int tail_size;

        // color
        int black = Utils.int2RGB(0,0,0);

        image = Utils.loadImage(args[0]);

        //Calculate the size of the board
        x_res = image.getWidth();
        y_res = image.getHeight();

        // Crop the image to a square
        if(x_res < y_res)
            y_res = x_res;
        else
            x_res = y_res;

        image = image.getSubimage(0, 0, x_res, y_res);

        // Calculate the size of each part of the chessboard
        tail_size = x_res / 8;

        // Process the image, pixel by pixel
        for (i = 0; i < y_res; i++)
            for (j = 0; j < x_res; j++)
            {
                int tail_index_x = j / tail_size;
                int tail_index_y = i / tail_size;

                // Make decision on the pixel color
                if (tail_index_x % 2 == tail_index_y % 2)
                    image.setRGB(j, i, black);
            }

        Utils.saveImage(image, args[1]);
    }
}

