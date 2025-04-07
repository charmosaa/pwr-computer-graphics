package Lab4;

import java.awt.*;
import java.awt.geom.*;

class ShapeItem extends DrawableItem {
    Shape shape;
    Color color;
    boolean isCirc = false;
    Rectangle2D originalBounds; // Store original bounds for serialization

    ShapeItem(Shape shape, Color color, boolean isCirc) {
        this.shape = shape;
        this.color = color;
        this.isCirc = isCirc;
        transform = new AffineTransform();
        originalBounds = shape.getBounds2D(); // Store original bounds
        transform.translate(originalBounds.getX(), originalBounds.getY());
        this.shape = AffineTransform.getTranslateInstance(-originalBounds.getX(), -originalBounds.getY()).createTransformedShape(shape);
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

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        
        // Serialize shape type and original bounds (before any transforms)
        if (isCirc) {
            sb.append("CIRC,");
        } else {
            sb.append("RECT,");
        }
        sb.append(originalBounds.getX()).append(",");
        sb.append(originalBounds.getY()).append(",");
        sb.append(originalBounds.getWidth()).append(",");
        sb.append(originalBounds.getHeight()).append(",");

        // Serialize transform (6 elements of the transformation matrix)
        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(matrix[i]);
        }

        // Serialize color
        sb.append(",").append(color.getRed());
        sb.append(",").append(color.getGreen());
        sb.append(",").append(color.getBlue());
        
        return sb.toString();
    }
}