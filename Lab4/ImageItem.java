package Lab4;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageItem implements DrawableItem {
    BufferedImage image;
    int x, y, width, height;

    ImageItem(BufferedImage image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, width, height, null);
    }

    @Override
    public boolean contains(Point p) {
        return new Rectangle(x, y, width, height).contains(p);
    }
}