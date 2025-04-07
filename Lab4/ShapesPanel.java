package Lab4;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class ShapesPanel extends JPanel implements MouseListener, MouseMotionListener{
    Rectangle2D.Double rect;
    Ellipse2D.Double circ;
    Shape draggedShape;
    DrawWndPane drawPane;

    public ShapesPanel()
    {
        rect = new Rectangle2D.Double(50,50,100,100);
        circ = new Ellipse2D.Double(50,200 , 100, 100);
        setBackground(Color.yellow);
        setPreferredSize(new Dimension(200, 350));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setDrawPane(DrawWndPane drawWndPane)
    {
        drawPane = drawWndPane;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect((int)rect.x,(int) rect.y,(int)rect.width, (int)rect.height);
        g2d.fillOval((int)circ.x,(int) circ.y, (int)circ.width, (int)circ.height);
    }

    
    public void mousePressed(MouseEvent e) {
        if (rect.contains(e.getPoint())) 
            draggedShape = rect;

        else if(circ.contains(e.getPoint()))
            draggedShape = circ;
    }
    
    
    public void mouseDragged(MouseEvent e) {
        if (draggedShape != null && drawPane != null) {
            Point screenPoint = SwingUtilities.convertPoint(this, e.getPoint(), getRootPane());
            ((DragGlassPane) getRootPane().getGlassPane()).setShape(draggedShape, screenPoint);
        }
    }
    
    
    public void mouseReleased(MouseEvent e) {
        if (draggedShape != null && drawPane != null) {
            Point dropPoint = SwingUtilities.convertPoint(this, e.getPoint(), drawPane);
    
            if (draggedShape instanceof Rectangle2D.Double) {
                Rectangle2D.Double rect = (Rectangle2D.Double) draggedShape;
                drawPane.dropShape(new Rectangle2D.Double(dropPoint.getX() - rect.width/2, dropPoint.getY() - rect.height/2, rect.width, rect.height),false);
            } else if (draggedShape instanceof Ellipse2D.Double) {
                Ellipse2D.Double circ = (Ellipse2D.Double) draggedShape;
                drawPane.dropShape(new Ellipse2D.Double(dropPoint.getX() - circ.width/2, dropPoint.getY() - circ.height/2, circ.width, circ.height),true);
            }
        }
    
        // clear the temp view 
        ((DragGlassPane) getRootPane().getGlassPane()).clearShape();
        draggedShape = null;
    }

      
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}
