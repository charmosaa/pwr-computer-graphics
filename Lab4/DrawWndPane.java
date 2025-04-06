package Lab4;

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
import javax.swing.filechooser.FileNameExtensionFilter;

public class DrawWndPane extends JPanel implements MouseListener, MouseMotionListener {
    private String currentShape = "Line";
    private ArrayList<Color> shapeColors = new ArrayList<>();
    private int itemCaught = -1, pointCaught;
    private ControlPanel controlPanel;

    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<DrawableItem> items = new ArrayList<>();

    public void dropImage(BufferedImage img, int x, int y, int width, int height) {
        items.add(new ImageItem(img, x, y, width, height));
        itemCaught = items.size()-1;
        repaint();
    }

    public void dropShape(Shape shape) {
        items.add(new ShapeItem(shape, getCurrentColor()));
        itemCaught = items.size()-1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        

        for (DrawableItem d : items) {
            d.draw(g2d);
        }
    }

    public DrawWndPane() {
        setBackground(Color.white);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    
    public void setControlPanel(ControlPanel controlPanel){
        this.controlPanel = controlPanel;
    }
    
    public void setCurrentShape(String shape) {
        this.currentShape = shape;
    }

    public void loadVectorFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a txt file to load");

         // filter to allow only .txt files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(null);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            shapes.clear();
            shapeColors.clear();
            
            try (Scanner scanner = new Scanner(selectedFile)) {
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
                JOptionPane.showMessageDialog(null, "Vector image loaded successfully!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void saveVectorToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Vector Image");
    
        // default file name
        fileChooser.setSelectedFile(new File("vector_image.txt"));
    
        int userSelection = fileChooser.showSaveDialog(null);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Ensure file has .txt extension
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
    
            try (PrintWriter writer = new PrintWriter(selectedFile)) {
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
                JOptionPane.showMessageDialog(null, "Vector image saved as: " + selectedFile.getName());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
     
    public void saveAsImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Image As");

        // default file name
        fileChooser.setSelectedFile(new File("image.png"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // file as .png 
            if (!selectedFile.getName().toLowerCase().endsWith(".png")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".png");
            }

            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "PNG", selectedFile);
                JOptionPane.showMessageDialog(null, "Image saved as: " + selectedFile.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error saving image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }  

    private boolean CatchClosePoint(int  x, int y)
   {
       for ( int i = 0; i < items.size(); i++ )
       {
            if (items.get(i).contains(new Point(x,y))){
                itemCaught = i;
                pointCaught = 0;
                return true;
            } 
            
       }
	   return false;
   }

   public void moveItem(String direction){
        if(itemCaught >= 0){
            switch(direction){
                case("Left") -> System.out.print(itemCaught+" left");
                case("Up") -> System.out.print(itemCaught+" up");
                case("Right") -> System.out.print(itemCaught+" right");
                case("Down") -> System.out.print(itemCaught+" down");
            }
        }
   }
    private Color getCurrentColor() {
        try {
            int r = Integer.parseInt(controlPanel.redField.getText());
            int g = Integer.parseInt(controlPanel.greenField.getText());
            int b = Integer.parseInt(controlPanel.blueField.getText());

            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

            return new Color(r,g,b);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid RGB values (0-255)", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Color.BLACK; //black as default
    }

    public void removeAction(ActionEvent event) {
        items.clear();
        repaint();
    }
    
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).contains(e.getPoint())) {
                    items.remove(i);
                    itemCaught = -1;
                    repaint();
                    return;
                }
            }
        } 
        else {
           CatchClosePoint(e.getX(), e.getY());
        }
    }
    
    
    public void mouseDragged(MouseEvent e) {
        
    }
    
    public void mouseReleased(MouseEvent e) {
        //itemCaught = -1;
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

}
