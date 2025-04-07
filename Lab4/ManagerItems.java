package Lab4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class ManagerItems {
    public static ShapeItem deserialize(String data) {
        String[] parts = data.split(",");
        if (parts.length < 14) return null; // Minimum required fields
        
        try {
            // Parse shape type and dimensions
            String type = parts[0];
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double w = Double.parseDouble(parts[3]);
            double h = Double.parseDouble(parts[4]);
            
            // Create the appropriate shape
            Shape shape;
            boolean isCirc;
            if (type.equals("CIRC")) {
                shape = new Ellipse2D.Double(x, y, w, h);
                isCirc = true;
            } else {
                shape = new Rectangle2D.Double(x, y, w, h);
                isCirc = false;
            }
            
            // Create transform from matrix values
            double[] matrix = {
                Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6]),
                Double.parseDouble(parts[7]),
                Double.parseDouble(parts[8]),
                Double.parseDouble(parts[9]),
                Double.parseDouble(parts[10])
            };
            AffineTransform transform = new AffineTransform(matrix);
            
            // Parse color
            Color color = new Color(
                Integer.parseInt(parts[11]),
                Integer.parseInt(parts[12]),
                Integer.parseInt(parts[13])
            );
            
            // Create and return the item
            ShapeItem item = new ShapeItem(shape, color, isCirc);
            item.setTransform(transform);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageItem deserializeImage(String data, String baseDir) {
        String[] parts = data.split(",");
        if (parts.length < 11) return null; // Minimum required fields
        
        try {
            // Parse bounds
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double width = Double.parseDouble(parts[3]);
            double height = Double.parseDouble(parts[4]);
            
            // Parse transform matrix
            double[] matrix = {
                Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6]),
                Double.parseDouble(parts[7]),
                Double.parseDouble(parts[8]),
                Double.parseDouble(parts[9]),
                Double.parseDouble(parts[10])
            };
            AffineTransform transform = new AffineTransform(matrix);
            
            // Parse image path (remaining parts joined in case path contains commas)
            String imagePath = String.join(",", Arrays.copyOfRange(parts, 11, parts.length));
            
            // Handle relative paths
            if (!new File(imagePath).isAbsolute()) {
                imagePath = new File(baseDir, imagePath).getPath();
            }
            
            // Load the image
            BufferedImage image;
            try {
                image = ImageIO.read(new File(imagePath));
                if (image == null) {
                    throw new IOException("Failed to load image");
                }
            } catch (IOException e) {
                // Try to load from resources if file not found
                imagePath = "Assets/" + new File(imagePath).getName();
                image = ImageIO.read(new File(imagePath));
            }
            
            // Create the ImageItem
            ImageItem item = new ImageItem(image, (int)x, (int)y, (int)width, (int)height, imagePath);
            item.transform = transform;
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
