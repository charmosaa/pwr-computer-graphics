package Lab4;


import java.awt.*;
import java.awt.geom.*;

class ShapeItem extends DrawableItem {
    Shape shape;
    Color color;

    ShapeItem(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
        transform = new AffineTransform();
        Rectangle2D bounds = shape.getBounds2D();
        transform.translate(bounds.getX(), bounds.getY());
        this.shape = AffineTransform.getTranslateInstance(-bounds.getX(), -bounds.getY()).createTransformedShape(shape);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        Shape transformedShape = transform.createTransformedShape(shape);
        g2d.fill(transformedShape);
    }

    @Override
    public Shape getOriginalShape() {
        return shape;
    }
}
