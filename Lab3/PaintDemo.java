package Lab3;

import java.awt.*;
import javax.swing.*;

public class PaintDemo {
    public static void main(String[] args) {
        SmpWindow wnd = new SmpWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setVisible(true);
        wnd.setBounds(70, 70, 800, 400);
        wnd.setTitle("Vector graphics program");
    }
}

class SmpWindow extends JFrame {
    public SmpWindow() {
        setLayout(new BorderLayout());

        DrawWndPane drawPane = new DrawWndPane();
        ControlPanel controlPanel = new ControlPanel(drawPane); 
        drawPane.setControlPanel(controlPanel);

        add(controlPanel, BorderLayout.SOUTH);
        add(drawPane, BorderLayout.CENTER);
    }
}