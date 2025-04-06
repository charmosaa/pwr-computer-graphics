package Lab4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class ImageItem extends DrawableItem {
    private BufferedImage image;
    private Rectangle2D originalBounds;

    public ImageItem(BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.originalBounds = new Rectangle2D.Double(0, 0, width, height);
        this.transform = new AffineTransform();
        this.transform.translate(x, y); 
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
        return new Rectangle2D.Double(
            originalBounds.getX(),
            originalBounds.getY(),
            originalBounds.getWidth(),
            originalBounds.getHeight()
        );
    }
}