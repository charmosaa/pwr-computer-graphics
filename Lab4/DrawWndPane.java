package Lab4;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawWndPane extends JPanel implements MouseListener, MouseMotionListener {
    private int itemCaught = -1, pointCaught, lastMouseX,lastMouseY;
    private ArrayList<DrawableItem> items = new ArrayList<>();
    boolean isRotateMode = true;
    
    private ControlPanel controlPanel;
    private Graphics2D g2d;

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
        g2d = (Graphics2D) g;
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

    void setRotateMode(boolean rotate){
        isRotateMode = rotate;
    }
    

    public void loadVectorFromFile() {
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

    private boolean CatchClosePoint(int  x, int y)
    {
        for ( int i = 0; i < items.size(); i++ )
        {
            DrawableItem item = items.get(i);
            Point center = new Point((int)item.getCenterX(), (int)item.getCenterY());
            Point currentPoint = new Point(x,y);

            if(center.distance(currentPoint) < 20)
            {
                items.add(item);
                items.remove(i);
                itemCaught = items.size()-1;
                pointCaught = 0;
                return true;
            }
            if(item.getClosestCorner(currentPoint, 10) >= 0){
                items.add(item);
                items.remove(i);
                itemCaught = items.size()-1;
                pointCaught = 1;
                return true;
            }
            
                
        }
        return false;
    }

    public void moveItem(String direction){
        if(itemCaught >= 0){
            double pixels = 5; 
            switch(direction){
                case "Left" -> items.get(itemCaught).translate(-pixels, 0);
                case "Right" -> items.get(itemCaught).translate(pixels, 0);
                case "Up" -> items.get(itemCaught).translate(0, -pixels);
                case "Down" -> items.get(itemCaught).translate(0, pixels);
            }
            repaint();
        }
    }

    public void rotItem(boolean clockwise){
        if (itemCaught >= 0) {
            double angle = clockwise ? 5 : -5;
            items.get(itemCaught).rotate(angle);
            repaint();
        }
    }

    public void removeAction(ActionEvent event) {
        items.clear();
        repaint();
    }
    
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (CatchClosePoint(e.getX(), e.getY())){
                if(itemCaught >= 0 && pointCaught == 0)
                {
                    items.remove(itemCaught);
                    itemCaught = -1;
                    pointCaught = -1;
                    repaint();
                    return;
                }   
            }
        }
        else {
            if (CatchClosePoint(e.getX(), e.getY())) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }
        }
    }
    
    
    public void mouseDragged(MouseEvent e) {
        if (itemCaught >= 0) {
            DrawableItem item = items.get(itemCaught);
            if (pointCaught == 0) { // Center drag - move
                double dx = e.getX() - lastMouseX;
                double dy = e.getY() - lastMouseY;
                item.translate(dx, dy);
            }
            else if (pointCaught == 1 ) { // Corner drag - rotate
                if(isRotateMode)
                {  
                    double centerX = item.getCenterX();
                    double centerY = item.getCenterY();
                    
                    // calculate angle change
                    double prevAngle = Math.atan2(lastMouseY - centerY, lastMouseX - centerX);
                    double currAngle = Math.atan2(e.getY() - centerY, e.getX() - centerX);
                    double angleDiff = Math.toDegrees(currAngle - prevAngle);

                    item.rotate(angleDiff);                    
                }
                else{
                    System.out.println("resize mode");
                    
                    double centerX = item.getCenterX();
                    double centerY = item.getCenterY();

                    // ratio of the new size and the previous
                    double sx = (e.getX()-centerX)/(double)(lastMouseX - centerX) ;
                    double sy =(e.getY()-centerY)/(double)(lastMouseY - centerY) ;

                    item.scale(sx, sy, centerX, centerY);
                }
                
            }
            lastMouseX = e.getX();
            lastMouseY = e.getY();
    
            repaint();
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        pointCaught = -1;
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

}
