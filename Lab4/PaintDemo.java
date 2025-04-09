package Lab4;

import java.awt.BorderLayout;
import javax.swing.*;

public class PaintDemo {
    public static void main(String[] args) {
        SmpWindow wnd = new SmpWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setVisible(true);
        wnd.setBounds(70, 70, 800, 800);
        wnd.setTitle("Plakat maker");
    }
}

class SmpWindow extends JFrame {
    public SmpWindow() {
        setLayout(new BorderLayout());

        // all the panels
        ImagePanel imagePanel = new ImagePanel();
        ShapesPanel shapesPanel = new ShapesPanel();
        DrawWndPane drawPane = new DrawWndPane();
        ControlPanel controlPanel = new ControlPanel(drawPane);
        drawPane.setControlPanel(controlPanel);
        imagePanel.setDrawPane(drawPane);
        shapesPanel.setDrawPane(drawPane);

        // left side - assets
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(imagePanel, BorderLayout.NORTH);
        leftPanel.add(shapesPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(drawPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // glass layer to drag droping images
        DragGlassPane glassPane = new DragGlassPane();
        setGlassPane(glassPane);
        glassPane.setVisible(true);


        imagePanel.setGlassPane(glassPane);
        shapesPanel.setGlassPane(glassPane);
        setVisible(true);
    }
}
