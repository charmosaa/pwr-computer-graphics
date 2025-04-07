package Lab4;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private ArrayList<Integer> imageY = new ArrayList<>();
    private BufferedImage draggedImage = null;
    private String draggedPath;
    private DrawWndPane drawPane; // Set externally
    private String DIR_PATH = "C:\\Users\\marty\\projects\\grafika\\Lab4\\Assets";
    int IMAGE_SIZE = 100;
    int IMAGE_X = 50;
    File[] files;



    public ImagePanel() {
        File folder = new File(DIR_PATH);
        files = folder.listFiles((dir, name) -> {
            return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
        });

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    images.add(img);
                } catch (Exception e) {
                    System.out.println("Could not load: " + file.getName());
                }
            }
        }

        setPreferredSize(new Dimension(200, 370));
        setBackground(Color.YELLOW);

        addMouseListener(this);
        addMouseMotionListener(this);

    }

    public void setDrawPane(DrawWndPane drawWndPane){
        this.drawPane = drawWndPane;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        imageY.clear();
        int x = IMAGE_X, y = 25;
        int spacing = 10;

        for (BufferedImage img : images) {
            if (img != null) {
                g.drawImage(img, x, y, IMAGE_SIZE, IMAGE_SIZE, this);
                imageY.add(y);
                y += IMAGE_SIZE + spacing;
            }
        }

    }
    

    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < imageY.size(); i++) {
            int x =IMAGE_X;
            int y = imageY.get(i);

            if (e.getX() > x && e.getX() < x+IMAGE_SIZE && e.getY() > y && e.getY() < y+IMAGE_SIZE) {
                draggedImage = images.get(i);
                draggedPath = files[i].getAbsolutePath();
                repaint();
                break;
            }
        }
    }
    
    
    public void mouseDragged(MouseEvent e) {
        if (draggedImage != null && drawPane != null) {
            Point screenPoint = SwingUtilities.convertPoint(this, e.getPoint(), getRootPane());
            ((DragGlassPane) getRootPane().getGlassPane()).setImage(draggedImage, screenPoint);
        }
    }
    
    
    public void mouseReleased(MouseEvent e) {
        if (draggedImage != null && drawPane != null) {
            Point dropPoint = SwingUtilities.convertPoint(this, e.getPoint(), drawPane);
            drawPane.dropImage(draggedImage, dropPoint.x - IMAGE_SIZE/2, dropPoint.y - IMAGE_SIZE/2, IMAGE_SIZE, IMAGE_SIZE, draggedPath);
        }
    
        // clear the temp view 
        ((DragGlassPane) getRootPane().getGlassPane()).clearImage();
        draggedImage = null;
        draggedPath = "";
    }
    
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}
