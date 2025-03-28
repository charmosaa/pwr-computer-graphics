package Lab1;

 import java.awt.image.*;
 
 public class Task3c
 {
     public static void main(String[] args)
     {
         System.out.println("Ring pattern synthesis");
 
         BufferedImage image;
 
         // Image resolution
         int x_res, y_res;
 
         // Ring center coordinates
         int x_c, y_c;
 
         // Predefined black and white RGB representations
         // packed as integers
         int black, white;
 
         // Loop variables - indices of the current row and column
         int i, j;
 
         // Get required image resolution from command line arguments
         x_res = Integer.parseInt(args[0].trim());
         y_res = Integer.parseInt(args[1].trim());
 
         // Initialize an empty image, use pixel format
         // with RGB packed in the integer data type
         image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
 
         // Create packed RGB representation of black and white colors
         black = Utils.int2RGB(0, 0, 0);
         white = Utils.int2RGB(255, 255, 255);
 
         // Find coordinates of the image center
         x_c = x_res / 2;
         y_c = y_res / 2;
 
         // Process the image, pixel by pixel
         for (i = 0; i < y_res; i++)
             for (j = 0; j < x_res; j++)
             {
                 double d;

                 // Initial ring index
                 int r = 0;

                 // Initial width of the ring
                 int w = 1;
 
                 // Calculate distance to the image center
                 d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));
 
                 // Find the ring index adding 1px of width for each ring
                 while(d > 0)
                 {
                    d -= w;
                    r++;
                    if (r % 2 == 0)
                        w += 1;
                 }
 
                 // Make decision on the pixel color
                 // based on the ring index
                 if (r % 2 == 0)
                     // Even ring - set black color
                     image.setRGB(j, i, black);
                 else
                     // Odd ring - set white color
                     image.setRGB(j, i, white);
             }
 
         Utils.saveImage(image, args[2]);
     }
 }
 