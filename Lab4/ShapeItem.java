package Lab4;


import java.awt.*;
import java.awt.geom.*;

class ShapeItem implements DrawableItem {
    Shape shape;
    Color color;

    ShapeItem(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        if (shape instanceof Rectangle2D.Double) {
            Rectangle2D.Double r = (Rectangle2D.Double) shape;
            g2d.fillRect((int) r.x, (int) r.y, (int) r.width, (int) r.height);
        } else if (shape instanceof Ellipse2D.Double) {
            Ellipse2D.Double o = (Ellipse2D.Double) shape;
            g2d.fillOval((int) o.x, (int) o.y, (int) o.width, (int) o.height);
        }
    }

    @Override
    public boolean contains(Point p) {
        return shape.contains(p);
    }
}
