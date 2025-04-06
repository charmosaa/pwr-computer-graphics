package Lab4;

import java.awt.*;
import java.awt.geom.*;

abstract class DrawableItem {
    protected AffineTransform transform;

    public abstract void draw(Graphics2D g2d);

    public abstract Shape getOriginalShape(); 

    public boolean contains(Point p) {
        try {
            AffineTransform inverse = transform.createInverse();
            Point2D localPoint = inverse.transform(p, null);
            return getOriginalShape().contains(localPoint);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void translate(double dx, double dy) {
        transform.translate(dx, dy);
    }

    public void rotate(double angleDegrees) {
        Rectangle2D bounds = getOriginalShape().getBounds2D();
        double centerX = bounds.getCenterX();
        double centerY = bounds.getCenterY();
        transform.rotate(Math.toRadians(angleDegrees), centerX, centerY);
    }

    public double getCenterX() {
        Rectangle2D bounds = transform.createTransformedShape(getOriginalShape()).getBounds2D();
        return bounds.getCenterX();
    }

    public double getCenterY() {
        Rectangle2D bounds = transform.createTransformedShape(getOriginalShape()).getBounds2D();
        return bounds.getCenterY();
    }
}
