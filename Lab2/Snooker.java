package Lab2;
 
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Ball{
    int radius;
    int number;
    Color color;
    double speed, angle, torque;
    double position_x, position_y;

    public Ball(int number, Color color)
    {
        this.number = number;
        this.color = color;
        this.radius = 10;
        this.speed = Math.random() * 5;
        this.angle = Math.random() * 2 * Math.PI;
        this.torque = 0.002;
        this.position_x = Math.random() * (Snooker.WIDTH - 4*radius) + radius;
        this.position_y = Math.random() * (Snooker.HEIGHT - 4*radius) + radius;
    }

    public void updatePosition()
    {
        position_x = position_x + Math.cos(angle) * speed;
        position_y = position_y + Math.sin(angle) * speed;
        speed = Math.max(0, speed - torque*speed);
    }

    
    public double distanceBetweenCenters(Ball other)
    {
        double dx = Math.abs(position_x - other.position_x);
        double dy = Math.abs(position_y - other.position_y);
        return Math.sqrt(dx*dx + dy*dy);
    }
    
}


public class Snooker {
    static int HEIGHT = 400, WIDTH = 700;

    public static void main(String[] args) {

        SnookerWindow wnd = new SnookerWindow();
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(70, 70, WIDTH, HEIGHT);
        wnd.setVisible(true);
        wnd.setResizable(false);
        
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

class SnookerPane extends JPanel {
    ArrayList<Ball> balls = new ArrayList<>();
    
    SnookerPane() {
        setBackground(new Color(10, 105, 10));

        // add snooker balls
        balls.add(new Ball(1, Color.YELLOW));   // Yellow ball
        balls.add(new Ball(2, Color.BLUE));    // Blue ball
        balls.add(new Ball(3, Color.RED));     // Red ball
        balls.add(new Ball(4, Color.PINK));    // Pink (Purple substitute)
        balls.add(new Ball(5, Color.ORANGE));  // Orange (substituting for brown)
        balls.add(new Ball(6, Color.GREEN));   // Green ball
        balls.add(new Ball(7, Color.BLACK));   // Black ball
        balls.add(new Ball(8, Color.WHITE));   // White cue ball
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        for(Ball ball : balls){
            g2d.setColor(ball.color);
            g.fillOval((int)ball.position_x,(int) ball.position_y, 2*ball.radius, 2*ball.radius);
            ball.updatePosition();

            //check for collisions
            wallCollision(ball);
            ballCollision(ball);
        }
    }

    public void wallCollision(Ball ball) {
        Dimension size = getSize();
        int MAX_WIDTH = size.width;
        int MAX_HEIGHT = size.height;
        
        // for left and right
        if (ball.position_x <= 0 || ball.position_x + 2 * ball.radius >= MAX_WIDTH) {
            ball.angle = Math.PI - ball.angle;                                                      // reverse x axis direction
            ball.position_x = Math.max(0, Math.min(ball.position_x, MAX_WIDTH - 2 * ball.radius));     // go back inside the bounds
        }
    
        // for up and down
        if (ball.position_y <= 0 || ball.position_y + 2 * ball.radius >= MAX_HEIGHT) {
            ball.angle = -ball.angle;                                                               // reverse the y axis direction
            ball.position_y = Math.max(0, Math.min(ball.position_y, MAX_HEIGHT - 2 * ball.radius));    // go back inside the bounds
        }
    }
    
    public void ballCollision(Ball ball)
    {
        for(Ball other : balls){
            if(ball.number != other.number && ball.distanceBetweenCenters(other) <= 2*ball.radius)
                System.out.println("COLLISION");

        }

    }
}

class SnookerWindow extends JFrame {
    public SnookerWindow() {
        setContentPane(new SnookerPane());
        setTitle("Snooker");
    }
}

