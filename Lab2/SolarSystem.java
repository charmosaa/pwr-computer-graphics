package Lab2;
 
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Planet {
    String name;
    double distance_from_sun; // distance from sun in AU
    double radius; // radius as a fraction of the Sun's radius
    Color color;
    double angle; // orbital angle 
    double orbital_period;


    public Planet(String name, double distance_from_sun, double radius, Color color) {
        this.name = name;
        this.distance_from_sun = distance_from_sun;
        this.radius = radius;
        this.color = color;
        angle = Math.random() * 2 * Math.PI; // initial position - random
        orbital_period = Math.sqrt(Math.pow(distance_from_sun, 3));
    }

    // simylate the motion
    public void updatePosition() {
        this.angle += 0.01 / orbital_period; // formula for speed for each planet
    }
}

public class SolarSystem {
    public static void main(String[] args) {

        SolarWindow wnd = new SolarWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(100, 0, 900, 900);
        wnd.setVisible(true);
        
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Program interrupted");
            }
            wnd.repaint();
        }
    }
}

class SolarPane extends JPanel {

    int center_x, center_y; // center of the sun - center of the solar system
    static int r_sun = 40; // sun radius
    static int AU = 150; // distance from the sun to the Earth
    ArrayList<Planet> planets = new ArrayList<>();
    
    SolarPane() {
        
        setBackground(new Color(0, 0, 30));

        // Create planets
        planets.add(new Planet("Mercury", 0.39, 0.2, Color.GRAY));
        planets.add(new Planet("Venus", 0.72, 0.4, new Color(220, 220,100)));
        planets.add(new Planet("Earth", 1.0, 0.5, Color.BLUE));
        planets.add(new Planet("Mars", 1.27, 0.3, Color.RED));
        planets.add(new Planet("Jupiter", 1.75, 1.0, Color.ORANGE));
        planets.add(new Planet("Saturn", 2.18, 0.8, new Color(220, 220,150)));
        planets.add(new Planet("Uranus", 2.48, 0.65, Color.CYAN));
        planets.add(new Planet("Neptune", 2.72, 0.55, new Color(90,100,255)));

    }

    public void DrawPlanetTrail(Graphics g, int distance_from_sun) 
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.drawOval(center_x - distance_from_sun, center_y - distance_from_sun, 2 * distance_from_sun, 2 * distance_from_sun);
    }
    
    public void DrawPlanet(Graphics g, Planet planet) 
    {
        // draw orbit
        DrawPlanetTrail(g, (int) (planet.distance_from_sun * AU));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(planet.color);

        // calculate the distance and radius in pixels
        int radius  = (int) (planet.radius * r_sun);
        int distance_from_sun = (int)(planet.distance_from_sun * AU);

        int planet_x = center_x + (int) ((distance_from_sun* Math.cos(planet.angle)) - radius);
        int planet_y = center_y + (int) ((distance_from_sun * Math.sin(planet.angle)) - radius);
    
        g2d.fillOval(planet_x, planet_y, 2 * radius, 2 *radius);
    }
    

    public void DrawSun(Graphics g) 
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g.fillOval(center_x - r_sun, center_y - r_sun, 2*r_sun, 2*r_sun);
    }

    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Dimension size = getSize();
        center_x = size.width / 2;
        center_y = (size.height) / 2;

        // draw sun
        DrawSun(g);

        //draw planets
        for(Planet planet : planets) {
            DrawPlanet(g, planet);
            planet.updatePosition();
        }

    }
}

class SolarWindow extends JFrame {
    public SolarWindow() {
        setContentPane(new SolarPane());
        setTitle("Solar System");
    }
}
