package Lab1;

import java.awt.image.*;

public class Task4c
{
    public static void main(String[] args)
    {
        System.out.println("Pattern synthesis");

        BufferedImage image1, image2;

        // Image resolution
        int x_res, y_res;

        // Loop variables - indices of the current row and column
        int i, j;

        // size of each part of the chessboard
        int tail_size;

        image1 = Utils.loadImage(args[0]);
        image2 = Utils.loadImage(args[1]);

        //Calculate the size of the board
        x_res = image1.getWidth();
        y_res = image1.getHeight();

        // Crop the image to a square
        if(x_res < y_res)
            y_res = x_res;
        else
            x_res = y_res;

        image1 = image1.getSubimage(0, 0, x_res, y_res);

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
                    image1.setRGB(j, i, image2.getRGB(j, i));
            }

        Utils.saveImage(image1, args[2]);
    }
}

