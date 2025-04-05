package Lab4;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class DragGlassPane extends JPanel {
    private BufferedImage draggedImage;
    private Shape draggedShape;
    private Point location;

    public DragGlassPane() {
        setOpaque(false);
        location = new Point(0, 0);
    }

    public void setImage(BufferedImage image, Point loc) {
        draggedImage = image;
        location = loc;
        repaint();
    }

    public void setShape(Shape shape, Point loc)
    {
        draggedShape = shape;
        location = loc;
        repaint();
    }

    public void clearImage() {
        draggedImage = null;
        repaint();
    }

    public void clearShape() {
        draggedShape = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 0.7f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (draggedImage != null && location != null) {
            g2d.drawImage(draggedImage, location.x - 50, location.y - 50, 100, 100, this);
        }
        else if (draggedShape != null) {
            g2d.setColor(Color.BLACK);
            Shape shapeToDraw;
            if (draggedShape instanceof Rectangle2D.Double rect) {
                shapeToDraw = new Rectangle2D.Double(location.x - 50, location.y - 50, rect.width, rect.height);
            } else if (draggedShape instanceof Ellipse2D.Double circ) {
                shapeToDraw = new Ellipse2D.Double(location.x - 50, location.y - 50, circ.width, circ.height);
            } else {
                shapeToDraw = draggedShape;
            }
            g2d.fill(shapeToDraw);
        }
    }
}

