package Lab3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class PaintDemo {
    public static void main(String[] args) {
        SmpWindow wnd = new SmpWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setVisible(true);
        wnd.setBounds(70, 70, 600, 400);
        wnd.setTitle("Vector graphics program");
    }
}

class DrawWndPane extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    JButton button;
    JRadioButton lineButton, rectButton, circleButton;
    ButtonGroup shapeGroup;
    
    int shapeCaught = -1;
    int pointCaught;
    String currentShape = "Line";
    
    ArrayList<Shape> shapes;
    
    DrawWndPane() {
        super();
        setLayout(null);
        setBackground(Color.white);
        
        button = new JButton("Reset");
        button.setBounds(10, 10, 70, 30);
        add(button);
        
        lineButton = new JRadioButton("Line", true);
        rectButton = new JRadioButton("Rectangle");
        circleButton = new JRadioButton("Circle");
        
        lineButton.setBounds(100, 10, 80, 30);
        rectButton.setBounds(200, 10, 100, 30);
        circleButton.setBounds(320, 10, 80, 30);
        
        add(lineButton);
        add(rectButton);
        add(circleButton);
        
        shapeGroup = new ButtonGroup();
        shapeGroup.add(lineButton);
        shapeGroup.add(rectButton);
        shapeGroup.add(circleButton);
        
        shapes = new ArrayList<>();
        
        button.addActionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        lineButton.addActionListener(e -> currentShape = "Line");
        rectButton.addActionListener(e -> currentShape = "Rectangle");
        circleButton.addActionListener(e -> currentShape = "Circle");
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        
        g2d.setColor( Color.black );       
        g2d.setXORMode( Color.red );  

        for (Shape s : shapes) {
            g2d.draw(s);
        }

        g2d.setPaintMode();
    }

    private boolean CatchClosePoint( double  x, double y )
   {
       for ( int i = 0; i < shapes.size(); i++ )
       {
            if(shapes.get( i ) instanceof Line2D.Double){
                Line2D.Double line;
                line = (Line2D.Double)shapes.get(i);

                if ( (Math.abs( line.x1 - x) < 10) && (Math.abs( line.y1 - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }    	   
                if ( (Math.abs( line.x2 - x) < 10) && (Math.abs( line.y2 - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }   
                double xc = 0.5 * (line.x1 + line.x2);
                double yc = 0.5 * (line.y1 + line.y2);    	   
                if ( (Math.abs( xc - x) < 10) && (Math.abs( yc - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 2;
                    return true;
                }   
            } 
            else if (shapes.get( i ) instanceof Rectangle2D.Double){
                Rectangle2D.Double rect = (Rectangle2D.Double)shapes.get(i);

                // top left
                if ( (Math.abs( rect.x - x) < 10) && (Math.abs( rect.y - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }   
                // right bottom 	   
                if ( (Math.abs( rect.x + rect.width - x) < 10) && (Math.abs( rect.y + rect.height - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }   
                // middle
                double xc = rect.x + rect.width/2;
                double yc = rect.y + rect.height/2;    	   
                if ( (Math.abs( xc - x) < 10) && (Math.abs( yc - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 2;
                    return true;
                }   
            } 
            else if(shapes.get( i ) instanceof Ellipse2D.Double){
                Ellipse2D.Double circ = (Ellipse2D.Double)shapes.get(i);

                double xc = circ.x + circ.width/2;
                double yc = circ.y + circ.height/2;    

                // middle 	   
                if ( (Math.abs( xc - x) < 10) && (Math.abs( yc - y) < 10) )
                {
                    shapeCaught = i;
                    pointCaught = 0;
                    return true;
                }    	
                // perimeter   
                if ( Math.abs(Math.sqrt(  Math.abs(xc- x) * Math.abs(xc - x) + Math.abs(yc - y) * Math.abs(yc - y)) - circ.height/2 )< 10 )
                {
                    shapeCaught = i;
                    pointCaught = 1;
                    return true;
                }   
            } 
       }
	   return false;
   }
    
    private void UpdateLinePosition( int x, int y )
    {
        if ( shapeCaught >= 0 )
        {	   
           Graphics g = getGraphics();
           Graphics2D  g2d = (Graphics2D)g;   	 
        
           //g2d.setXORMode( getBackground() );
           g2d.setXORMode( new Color( 255,255,255) );  
           g2d.setXORMode( Color.red );           
 
            Line2D.Double  line;
            line = (Line2D.Double) shapes.get( shapeCaught );
           
           // Delete at previous position          
           g2d.draw( line );  
 
            // Update line end position
            switch (pointCaught) {
                case 0:
                    line.x1 = x;
                    line.y1 = y;
                    break;
                case 1:
                    line.x2 = x;
                    line.y2 = y;
                    break;
                case 2:
                    double xc = x - 0.5 * (line.x1 + line.x2);
                    double yc = y - 0.5 * (line.y1 + line.y2);           	  
                    line.x1 += xc;
                    line.y1 += yc;
                    line.x2 += xc;
                    line.y2 += yc;
                    break;  
                default:
                    break;
            }
           
           // Draw line at new position          
           g2d.draw( line );
           g2d.setPaintMode();                   
        }   
    }

    private void UpdateRectPosition( int x, int y )
    {
        if ( shapeCaught >= 0 )
        {	   
            Graphics g = getGraphics();
            Graphics2D  g2d = (Graphics2D)g;   	 
            
            //g2d.setXORMode( getBackground() );
            g2d.setXORMode( new Color( 255,255,255) );  
            g2d.setXORMode( Color.red );           

            Rectangle2D.Double  rect;
            rect = (Rectangle2D.Double) shapes.get( shapeCaught );
            
            // Delete at previous position          
            g2d.draw( rect );  
 
            // Update line end position
            switch (pointCaught) {
                case 0: //left top
                    rect.width += (rect.x - x);
                    rect.height += (rect.y - y);
                    rect.x = x;
                    rect.y = y;
                    break;
                case 1: // right bottom
                    rect.width = Math.abs(rect.x - x);
                    rect.height = Math.abs(rect.y - y);
                    rect.x = Math.min (rect.x, x);
                    rect.y = Math.min (rect.y, y);
                    break;
                case 2:
                    Point2D center = getShapeCenter(rect);
                    double xc = x - center.getX();
                    double yc = y - center.getY();           	  
                    rect.x += xc;
                    rect.y += yc;
                    break;  
                default:
                    break;
            }
           
           // Draw line at new position          
           g2d.draw( rect );
           g2d.setPaintMode();                   
        }   
    }

    
    private void UpdateCriclePosition( int x, int y )
    {
        if ( shapeCaught >= 0 )
        {	   
            Graphics g = getGraphics();
            Graphics2D  g2d = (Graphics2D)g;   	 
            
            //g2d.setXORMode( getBackground() );
            g2d.setXORMode( new Color( 255,255,255) );  
            g2d.setXORMode( Color.red );           

            Ellipse2D.Double  circ;
            circ = (Ellipse2D.Double) shapes.get( shapeCaught );
            
            // Delete at previous position          
            g2d.draw( circ );
 
            Point2D center = getShapeCenter(circ);
            // Update line end position
            switch (pointCaught) {
                case 0: // middle
                    circ.x += (x-center.getX());
                    circ.y += (y - center.getY());
                    break;
                case 1: // on the perimeter 
                    double radious = center.distance(x, y);

                    circ.width = 2*radious;
                    circ.height = 2*radious;
                    circ.x = center.getX() - radious;
                    circ.y = center.getY() - radious;
                    break; 
                default:
                    break;
            }
           
           // Draw line at new position          
           g2d.draw( circ );
           g2d.setPaintMode();                   
        }   
    }

    

    public void actionPerformed(ActionEvent event) {
        shapes.clear();
        repaint();
    }
    
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {

            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                Point2D center = getShapeCenter(shape);
                
                if (center.distance(e.getX(), e.getY()) < 10) { 
                    shapes.remove(i);
                    repaint();
                    return;
                }
            }
        } else {
            if (CatchClosePoint(e.getX(),e.getY()))
            {
            switch(currentShape){
                    case "Line" -> UpdateLinePosition(e.getX(),e.getY());
                    case "Rectangle" -> UpdateRectPosition(e.getX(),e.getY());
                    case "Circle" -> UpdateCriclePosition(e.getX(),e.getY());
                    default -> {
                    }
                }
            }
                
            else{
                switch (currentShape) {
                    case "Line" -> shapes.add(new Line2D.Double(e.getX(), e.getY(), e.getX(), e.getY()));
                    case "Rectangle" -> shapes.add(new Rectangle2D.Double(e.getX(), e.getY(), 0, 0));
                    case "Circle" -> shapes.add(new Ellipse2D.Double(e.getX(), e.getY(), 0, 0));
                    default -> {
                    }
                }
            shapeCaught = shapes.size() - 1;
            pointCaught = 1;
            }
        }
    }

    private Point2D getShapeCenter(Shape shape) {
        if (shape instanceof Line2D) {
            Line2D line = (Line2D) shape;
            return new Point2D.Double((line.getX1() + line.getX2()) / 2, (line.getY1() + line.getY2()) / 2);
        } else if (shape instanceof Rectangle2D) {
            Rectangle2D rect = (Rectangle2D) shape;
            return new Point2D.Double(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        } else if (shape instanceof Ellipse2D) {
            Ellipse2D circle = (Ellipse2D) shape;
            return new Point2D.Double(circle.getX() + circle.getWidth() / 2, circle.getY() + circle.getHeight() / 2);
        }
        return new Point2D.Double(0, 0);
    }
    
    
    public void mouseDragged(MouseEvent e) {
        if (shapeCaught >= 0) {
            Shape shape = shapes.get(shapeCaught);
            if (shape instanceof Line2D line2D) {
                UpdateLinePosition(e.getX(), e.getY());
            } else if (shape instanceof Rectangle2D rect) {
                //rect.setFrameFromDiagonal(rect.getX(), rect.getY(), e.getX(), e.getY());
                UpdateRectPosition(e.getX(), e.getY());
            } else if (shape instanceof Ellipse2D ellipse) {
                UpdateCriclePosition(e.getX(), e.getY());
            }
            repaint();
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        shapeCaught = -1;
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}

class SmpWindow extends JFrame {
    public SmpWindow() {
        Container contents = getContentPane();
        contents.add(new DrawWndPane());
    }
}



