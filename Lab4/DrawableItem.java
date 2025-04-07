package Lab4;

import java.awt.*;
import java.awt.geom.*;

abstract class DrawableItem {
    protected AffineTransform transform;

    public abstract void draw(Graphics2D g2d);

    public abstract Shape getOriginalShape(); 

    public void translate(double dx, double dy) {
        AffineTransform currentTransform = getTransform();
                            
        AffineTransform moveTransform = new AffineTransform();
        moveTransform.translate(dx, dy);

        moveTransform.concatenate(currentTransform);
        setTransform(moveTransform);
    }

    public void rotate(double angleDegrees) {

        double centerX = getCenterX();
        double centerY = getCenterY();

        AffineTransform currentTransform = getTransform();
                            
        AffineTransform rotateTransform = new AffineTransform();
        rotateTransform.translate(centerX, centerY);
        rotateTransform.rotate(Math.toRadians(angleDegrees));
        rotateTransform.translate(-centerX, -centerY);

        rotateTransform.concatenate(currentTransform);
        setTransform(rotateTransform);
    }

    public void scale(double sx, double sy, double centerX, double centerY){
        AffineTransform currentTransform = getTransform();
                    
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.translate(centerX, centerY);
        scaleTransform.scale(sx, sy);
        scaleTransform.translate(-centerX, -centerY);
        
        scaleTransform.concatenate(currentTransform);
        setTransform(scaleTransform);
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

    
    public void resetTransform() {
        transform = new AffineTransform();
    }

    public AffineTransform getTransform() {
        return new AffineTransform(transform);
    }

    public void setTransform(AffineTransform newTransform) {
        transform = new AffineTransform(newTransform);
    }

}
