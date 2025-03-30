package Lab3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
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
    JButton button, saveVector, loadVector, saveRaster;
    JRadioButton lineButton, rectButton, circleButton;
    ButtonGroup shapeGroup;

    JTextField redField, greenField, blueField;
    
    int shapeCaught = -1;
    int pointCaught;
    String currentShape = "Line";
    
    ArrayList<Shape> shapes;
    ArrayList<Color> shapeColors = new ArrayList<>();
    
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
        

        lineButton.setBounds(240, 320 , 100, 30);
        rectButton.setBounds(360, 320, 100, 30);
        circleButton.setBounds(480, 320, 100, 30);
        
        add(lineButton);
        add(rectButton);
        add(circleButton);
        
        shapeGroup = new ButtonGroup();
        shapeGroup.add(lineButton);
        shapeGroup.add(rectButton);
        shapeGroup.add(circleButton);

        redField = new JTextField("0");
        greenField = new JTextField("0");
        blueField = new JTextField("0");

        redField.setBounds(420, 10, 40, 30);
        greenField.setBounds(470, 10, 40, 30);
        blueField.setBounds(520, 10, 40, 30);

        saveVector = new JButton("Save txt");
        loadVector = new JButton("Load");
        saveRaster = new JButton("Save raster");

        saveVector.setBounds(10, 50, 100, 30);
        loadVector.setBounds(10, 90, 100, 30);
        saveRaster.setBounds(10, 130, 100, 30);

        saveVector.addActionListener(e -> saveVectorToFile());
        saveRaster.addActionListener(e-> saveAsImage());
        loadVector.addActionListener(e -> loadVectorFromFile());

        add(redField);
        add(greenField);
        add(blueField);
        add(saveVector);
        add(saveRaster);
        add(loadVector);

        //applyColorButton.addActionListener(e -> updateShapeColor());
                
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
          
        g2d.setXORMode( Color.white );  

        for (int i =0; i< shapes.size();i++)
        {
            g2d.setColor(shapeColors.get(i));    
            g2d.draw(shapes.get(i)); 
        }

        g2d.setPaintMode();
    }

    private void loadVectorFromFile() {
        shapes.clear();
        shapeColors.clear();
        
        try (Scanner scanner = new Scanner(new File("Lab3/Assets/vector_image.txt"))) {
            while (scanner.hasNext()) {
                String type = scanner.next();
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                double w = scanner.nextDouble();
                double h = scanner.nextDouble();
                int r = scanner.nextInt();
                int g = scanner.nextInt();
                int b = scanner.nextInt();
                Color color = new Color(r, g, b);
    
                if (type.equals("Line")) {
                    shapes.add(new Line2D.Double(x, y, w, h));
                } else if (type.equals("Rectangle")) {
                    shapes.add(new Rectangle2D.Double(x, y, w, h));
                } else if (type.equals("Circle")) {
                    shapes.add(new Ellipse2D.Double(x, y, w, h));
                }
                shapeColors.add(color);
            }
            repaint();
            JOptionPane.showMessageDialog(null, "Vector image loaded!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } 
    }

    private void saveVectorToFile() {
        try (PrintWriter writer = new PrintWriter(new File("Lab3/Assets/vector_image.txt"))) {
            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                Color color = shapeColors.get(i);
    
                if (shape instanceof Line2D.Double line) {
                    writer.printf("Line %.2f %.2f %.2f %.2f %d %d %d%n",
                            line.x1, line.y1, line.x2, line.y2,
                            color.getRed(), color.getGreen(), color.getBlue());
                } else if (shape instanceof Rectangle2D.Double rect) {
                    writer.printf("Rectangle %.2f %.2f %.2f %.2f %d %d %d%n",
                            rect.x, rect.y, rect.width, rect.height,
                            color.getRed(), color.getGreen(), color.getBlue());
                } else if (shape instanceof Ellipse2D.Double circ) {
                    writer.printf("Circle %.2f %.2f %.2f %.2f %d %d %d%n",
                            circ.x, circ.y, circ.width, circ.height,
                            color.getRed(), color.getGreen(), color.getBlue());
                }
            }
            JOptionPane.showMessageDialog(null, "Vector image saved!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void saveAsImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        paint(g2d);
        g2d.dispose();
    
        try {
            File file = new File("Lab3/Assets/image.png");
            ImageIO.write(image, "PNG", file);
            JOptionPane.showMessageDialog(null, "Image saved!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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
 
    private void updateShapePosition(int x, int y) {
        if (shapeCaught >= 0) {
            Graphics g = getGraphics();
            Graphics2D g2d = (Graphics2D) g;
    
            g2d.setXORMode(Color.white);
            g2d.setColor(shapeColors.get(shapeCaught));
    
            Shape shape = shapes.get(shapeCaught);
            g2d.draw(shape); // remove old shape
    
            // new shape position
            if (shape instanceof Line2D.Double line) {
                updateLine(line, x, y);
            } else if (shape instanceof Rectangle2D.Double rect) {
                updateRectangle(rect, x, y);
            } else if (shape instanceof Ellipse2D.Double circ) {
                updateCircle(circ, x, y);
            }
            
            g2d.setColor(getCurrentColor());
            shapeColors.set(shapeCaught, getCurrentColor());
            g2d.draw(shape); 
            g2d.setPaintMode();
        }
    }

    private void updateLine(Line2D.Double line, int x, int y) {
        switch (pointCaught) {
            case 0 -> { line.x1 = x; line.y1 = y; }
            case 1 -> { line.x2 = x; line.y2 = y; }
            case 2 -> { // center - move 
                double xc = x - (line.x1 + line.x2) / 2;
                double yc = y - (line.y1 + line.y2) / 2;
                line.x1 += xc;
                line.y1 += yc;
                line.x2 += xc;
                line.y2 += yc;
            }
        }
    }
    private void updateRectangle(Rectangle2D.Double rect, int x, int y) {
        switch (pointCaught) {
            case 0 -> { // top-left corner
                rect.width += (rect.x - x);
                rect.height += (rect.y - y);
                rect.x = x;
                rect.y = y;
            }
            case 1 -> { // bottom-right corner
                rect.width = Math.abs(rect.x - x);
                rect.height = Math.abs(rect.y - y);
                rect.x = Math.min(rect.x, x);
                rect.y = Math.min(rect.y, y);
            }
            case 2 -> { // center - moving 
                Point2D center = getShapeCenter(rect);
                rect.x += (x - center.getX());
                rect.y += (y - center.getY());
            }
        }
    }
    
    private void updateCircle(Ellipse2D.Double circ, int x, int y) {
        Point2D center = getShapeCenter(circ);
        switch (pointCaught) {
            case 0 -> { // center - moving
                circ.x += (x - center.getX());
                circ.y += (y - center.getY());
            }
            case 1 -> { // on the perimeter - resize 
                double radius = center.distance(x, y);
                circ.width = circ.height = 2 * radius;
                circ.x = center.getX() - radius;
                circ.y = center.getY() - radius;
            }
        }
    }
        
    

    private Color getCurrentColor() {
        try {
            int r = Integer.parseInt(redField.getText());
            int g = Integer.parseInt(greenField.getText());
            int b = Integer.parseInt(blueField.getText());

            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            return new Color(r,g,b);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid RGB values (0-255)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Color.BLACK; //black as default
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

    public void removeShape()
    {
        Graphics g = getGraphics();
        Graphics2D  g2d = (Graphics2D)g;   	 
            
        g2d.setXORMode( new Color( 255,255,255) );       
        g2d.setColor(shapeColors.get(shapeCaught));
        Shape shape = shapes.get( shapeCaught );     
        g2d.draw( shape );
        shapeCaught = -1;
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
                    shapeCaught = i;
                    removeShape();
                    shapes.remove(i);
                    shapeColors.remove(i);
                    return;
                }
            }
        } else {
            if (CatchClosePoint(e.getX(),e.getY()))
                updateShapePosition(e.getX(), e.getY());
                
            else{
                switch (currentShape) {
                    case "Line" -> shapes.add(new Line2D.Double(e.getX(), e.getY(), e.getX(), e.getY()));
                    case "Rectangle" -> shapes.add(new Rectangle2D.Double(e.getX(), e.getY(), 0, 0));
                    case "Circle" -> shapes.add(new Ellipse2D.Double(e.getX(), e.getY(), 0, 0));
                    default -> {
                    }
                }
                shapeCaught = shapes.size() - 1;
                shapeColors.add(getCurrentColor()); 
                pointCaught = 1;
            }
        }
    }
    
    
    public void mouseDragged(MouseEvent e) {
        if (shapeCaught >= 0) {
            updateShapePosition(e.getX(), e.getY());
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








