package Lab4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class ImageItem extends DrawableItem {
    private BufferedImage image;
    private Rectangle2D originalBounds;
    private String imagePath;

    public ImageItem(BufferedImage image, int x, int y, int width, int height, String imagePath) {
        this.image = image;
        this.originalBounds = new Rectangle2D.Double(0, 0, width, height);
        this.transform = new AffineTransform();
        this.transform.translate(x, y); 
        this.imagePath = imagePath;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform def_transform = g2d.getTransform();
        g2d.transform(transform);
        g2d.drawImage(image, 
                     (int)originalBounds.getX(), 
                     (int)originalBounds.getY(), 
                     (int)originalBounds.getWidth(), 
                     (int)originalBounds.getHeight(), 
                     null);
        g2d.setTransform(def_transform);
    }

    @Override
    public Shape getOriginalShape() {
        return originalBounds;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("IMAGE,");
        
        // Original bounds
        sb.append(originalBounds.getX()).append(",");
        sb.append(originalBounds.getY()).append(",");
        sb.append(originalBounds.getWidth()).append(",");
        sb.append(originalBounds.getHeight()).append(",");
        
        // Full transform matrix (6 values)
        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(matrix[i]);
        }
        
        sb.append(",").append(imagePath);
        return sb.toString();
    }

    public void setPath(String imagePath){
        this.imagePath = imagePath;
    }

}