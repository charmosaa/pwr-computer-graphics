package Lab4;

import java.awt.*;

interface DrawableItem {
    void draw(Graphics2D g2d);
    boolean contains(Point p);
}
