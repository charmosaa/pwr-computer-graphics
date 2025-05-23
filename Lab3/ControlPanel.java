package Lab3;

import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
    JButton button, saveVector, loadVector, saveRaster;
    JRadioButton lineButton, rectButton, circleButton;
    ButtonGroup shapeGroup;
    JTextField redField, greenField, blueField;

    public ControlPanel(DrawWndPane drawPane) {
        setLayout(new FlowLayout());

        button = new JButton("Reset");
        saveVector = new JButton("Save txt");
        loadVector = new JButton("Load");
        saveRaster = new JButton("Save raster");

        lineButton = new JRadioButton("Line", true);
        rectButton = new JRadioButton("Rectangle");
        circleButton = new JRadioButton("Circle");
        shapeGroup = new ButtonGroup();
        shapeGroup.add(lineButton);
        shapeGroup.add(rectButton);
        shapeGroup.add(circleButton);

        redField = new JTextField("0", 3);
        greenField = new JTextField("0", 3);
        blueField = new JTextField("0", 3);

        redField.setBackground(Color.red);
        greenField.setBackground(Color.green);
        blueField.setBackground(new Color(100,100, 255));

        add(button);
        add(saveVector);
        add(loadVector);
        add(saveRaster);
        add(lineButton);
        add(rectButton);
        add(circleButton);
        add(new JLabel("R:"));
        add(redField);
        add(new JLabel("G:"));
        add(greenField);
        add(new JLabel("B:"));
        add(blueField);

        // Add actions
        button.addActionListener(e -> drawPane.actionPerformed(e));
        saveVector.addActionListener(e -> drawPane.saveVectorToFile());
        loadVector.addActionListener(e -> drawPane.loadVectorFromFile());
        saveRaster.addActionListener(e -> drawPane.saveAsImage());

        lineButton.addActionListener(e -> drawPane.setCurrentShape("Line"));
        rectButton.addActionListener(e -> drawPane.setCurrentShape("Rectangle"));
        circleButton.addActionListener(e -> drawPane.setCurrentShape("Circle"));
    }
}
