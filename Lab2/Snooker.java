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
        this.speed = Math.random() * 8;
        this.angle = Math.random() * 2 * Math.PI;
        this.torque = 0.005;
        this.position_x = Math.random() * (Snooker.WIDTH - 4*radius) + radius;
        this.position_y = Math.random() * (Snooker.HEIGHT - 4*radius) + radius;
    }

    public void updatePosition()
    {
        position_x = position_x + Math.cos(angle) * speed;
        position_y = position_y + Math.sin(angle) * speed;
        speed = speed - torque*speed;
        if(speed <= 0.15)
            speed = 0;
    }

    
    public double distanceBetweenCenters(Ball other)
    {
        double dx = position_x - other.position_x;
        double dy = position_y - other.position_y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
}


public class Snooker {
    static int HEIGHT = 400, WIDTH = 800;

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
        balls.add(new Ball(0, Color.YELLOW));   
        balls.add(new Ball(1, Color.BLUE));    
        balls.add(new Ball(2, Color.PINK));    
        balls.add(new Ball(3, Color.ORANGE)); 
        balls.add(new Ball(4, Color.GREEN));   
        balls.add(new Ball(5, Color.BLACK));   
        balls.add(new Ball(6, Color.WHITE));  
        
        for(int i=0;i<15;i++)
            balls.add(new Ball(i+7, Color.RED));  
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
            ball.angle = Math.PI - ball.angle;                                                           // reverse x axis direction
            ball.position_x = Math.max(0, Math.min(ball.position_x, MAX_WIDTH - 2 * ball.radius));     // go back inside the bounds
        }
    
        // for up and down
        if (ball.position_y <= 0 || ball.position_y + 2 * ball.radius >= MAX_HEIGHT) {
            ball.angle = -ball.angle;                                                                    // reverse the y axis direction
            ball.position_y = Math.max(0, Math.min(ball.position_y, MAX_HEIGHT - 2 * ball.radius));    // go back inside the bounds
        }
    }
    
    public void ballCollision(Ball ball)
    {
        for(Ball other : balls){
            if(ball.number != other.number && ball.distanceBetweenCenters(other) <= 2*ball.radius)
            {
                // resolving overlap problem
                double overlap = 2 * ball.radius - ball.distanceBetweenCenters(other);
                double cos_overlap = (ball.position_x - other.position_x) / ball.distanceBetweenCenters(other);
                double sin_overlap = (ball.position_y - other.position_y) / ball.distanceBetweenCenters(other);

                // moving each ball apart
                ball.position_x += cos_overlap * (overlap / 2);
                ball.position_y += sin_overlap * (overlap / 2);
                other.position_x -= cos_overlap * (overlap / 2);
                other.position_y -= sin_overlap * (overlap / 2);

                // calculating the positions and angles of the collision
                double dx = ball.position_x - other.position_x;
                double dy = ball.position_y - other.position_y;
                double theta = Math.atan2(dy, dx);

                // getting the x and y speeds
                double vx_ball = Math.cos(ball.angle) * ball.speed;
                double vy_ball = Math.sin(ball.angle) * ball.speed;
                double vx_other = Math.cos(other.angle) * other.speed;
                double vy_other = Math.sin(other.angle) * other.speed;

                // rotation to horizontal
                double[] horizontal_ball = rotateVector(vx_ball, vy_ball, theta);
                double[] horizontal_other = rotateVector(vx_other, vy_other, theta);

                // from elastic collision v2f = v1i and v1f = v2i
                // just taking the x
                double v1f = horizontal_other[0];
                double v2f = horizontal_ball[0];

                // now we unrotate to get back 
                double[] final_pos_ball = unrotateVector(v1f, horizontal_ball[1], theta);
                double[] final_pos_other = unrotateVector(v2f, horizontal_other[1], theta);

                // now new angle recalculation
                ball.angle = Math.atan2(final_pos_ball[1], final_pos_ball[0]);
                other.angle = Math.atan2(final_pos_other[1], final_pos_other[0]);

                // speed calculation
                ball.speed = Math.sqrt(final_pos_ball[0]*final_pos_ball[0] + final_pos_ball[1]*final_pos_ball[1]);
                other.speed = Math.sqrt(final_pos_other[0]*final_pos_other[0] + final_pos_other[1]*final_pos_other[1]);

            }

        }

    }

    // rotating vectors so the balls are horizontal
    public static double[] rotateVector(double vx, double vy, double theta) {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double vx1 = cosTheta * vx + sinTheta * vy;
        double vy1 = (-sinTheta) * vx + cosTheta * vy;

        return new double[]{vx1, vy1};
    }

    public static double[] unrotateVector(double vx, double vy, double theta) {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double vx1 = cosTheta * vx + (-sinTheta) * vy;
        double vy1 = sinTheta * vx + cosTheta * vy;

        return new double[]{vx1, vy1};
    }
}

class SnookerWindow extends JFrame {
    public SnookerWindow() {
        setContentPane(new SnookerPane());
        setTitle("Snooker");
    }
}

