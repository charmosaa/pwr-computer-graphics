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

    public Point2D[] getCorners() {
        Rectangle2D bounds = transform.createTransformedShape(getOriginalShape()).getBounds2D();
        return new Point2D[] {
            new Point2D.Double(bounds.getMinX(), bounds.getMinY()), // Top-left
            new Point2D.Double(bounds.getMaxX(), bounds.getMinY()), // Top-right
            new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()), // Bottom-right
            new Point2D.Double(bounds.getMinX(), bounds.getMaxY())  // Bottom-left
        };
    }


    public int getClosestCorner(Point2D point, double tolerance) {
        Point2D[] corners = getCorners();
        for (int i = 0; i < corners.length; i++) {
            if (corners[i].distance(point) <= tolerance) {
                return i; 
            }
        }
        return -1; 
    }

        // In DrawableItem class
    public void resetTransform() {
        transform = new AffineTransform();
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public void setTransform(AffineTransform newTransform) {
        transform = new AffineTransform(newTransform);
    }

    
    public void scale(double sx, double sy, double pivotX, double pivotY) {
        // Translate pivot to origin, scale, then translate back
        transform.translate(pivotX, pivotY);
        transform.scale(sx, sy);
        transform.translate(-pivotX, -pivotY);
    }   
}
