package Lab1;
/*
 * Computer graphics courses at Wroclaw University of Technology
 * (C) Wroclaw University of Technology, 2010
 *
 * Description:
 * This demo shows basic raster operations on raster image
 * represented by BufferedImage object. Image is created
 * on pixel-by-pixel basis and then stored in a file.
 */

 import java.awt.image.*;
 
 public class Task1a
 {
     public static void main(String[] args)
     {
         System.out.println("Ring pattern synthesis");
 
         BufferedImage image;
 
         // Image resolution
         int x_res, y_res;
 
         // Ring center coordinates
         int x_c, y_c;
        
         //colors
         int gray;
         int gray_intensity;
 
         // Loop variables - indices of the current row and column
         int i, j;
 
         // Fixed ring width
         final int w = 10;
 
         // Get required image resolution from command line arguments
         x_res = Integer.parseInt(args[0].trim());
         y_res = Integer.parseInt(args[1].trim());
 
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
                double d;
 
                 // Calculate distance to the image center
                d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

                //calculate the intensity of the pixel according to the distance from the center
                gray_intensity = (int) (128 * (Math.sin(Math.PI * d / w) + 1));
                if (gray_intensity < 1) gray_intensity = 0;
                if (gray_intensity > 254) gray_intensity = 255;

                gray = Utils.int2RGB(gray_intensity, gray_intensity, gray_intensity); 

                image.setRGB(j, i, gray);
             }
 
         Utils.saveImage(image, args[2]);
     }
 }
 