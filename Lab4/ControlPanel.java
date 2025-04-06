package Lab4;

import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
    JButton button, saveVector, loadVector, saveRaster, moveLeft, moveRight, moveUp, moveDown;
    JTextField redField, greenField, blueField;
    DrawWndPane drawPane;

    public ControlPanel(DrawWndPane drawPane) {
        this.drawPane = drawPane;

        button = new JButton("Reset");
        saveVector = new JButton("Save txt");
        loadVector = new JButton("Load");
        saveRaster = new JButton("Save img");

        moveLeft = new JButton("Left");
        moveUp = new JButton("Up");
        moveRight = new JButton("Right");
        moveDown = new JButton("Down");

        redField = new JTextField("0", 3);
        greenField = new JTextField("0", 3);
        blueField = new JTextField("0", 3);

        redField.setBackground(Color.red);
        greenField.setBackground(Color.green);
        blueField.setBackground(new Color(100,100, 255));

        setLayout(new GridLayout(2, 1)); // two rows

        // First row panel
        JPanel row1 = new JPanel(new FlowLayout());
        row1.add(button);
        row1.add(saveVector);
        row1.add(loadVector);
        row1.add(saveRaster);
        row1.add(new JLabel("Move:"));
        row1.add(moveLeft);
        row1.add(moveUp);
        row1.add(moveRight);
        row1.add(moveDown);

        // Second row panel
        JPanel row2 = new JPanel(new FlowLayout());
        row2.add(new JLabel("R:"));
        row2.add(redField);
        row2.add(new JLabel("G:"));
        row2.add(greenField);
        row2.add(new JLabel("B:"));
        row2.add(blueField);

        add(row1);
        add(row2);

        // Add actions
        button.addActionListener(e -> drawPane.removeAction(e));
        saveVector.addActionListener(e -> drawPane.saveVectorToFile());
        loadVector.addActionListener(e -> drawPane.loadVectorFromFile());
        saveRaster.addActionListener(e -> drawPane.saveAsImage());

        moveLeft.addActionListener(e -> drawPane.moveItem("Left"));
        moveUp.addActionListener(e -> drawPane.moveItem("Up"));
        moveRight.addActionListener(e -> drawPane.moveItem("Right"));
        moveDown.addActionListener(e -> drawPane.moveItem("Down"));
    }
}
