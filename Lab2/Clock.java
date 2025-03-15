package Lab2;
 
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;


public class Clock {
    public static void main(String[] args) {
        int pendulum_angle = Integer.parseInt(args[0].trim());
        int pendulum_period =Integer.parseInt(args[1].trim());

        int pendulum_length = (int)(Math.pow(pendulum_period/(2*Math.PI),2) * 9.8);

        ClockWindow wnd = new ClockWindow(pendulum_angle, pendulum_period, pendulum_length);
        wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wnd.setBounds(70, 70, 300, 300);
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

class ClockPane extends JPanel {
    final int TICK_LEN = 10;
    int center_x, center_y;
    int r_outer, r_inner;
    GregorianCalendar calendar;
    int pendulum_angle, pendulum_period, pendulum_length;
    int ball_radius;
    int start_time;
    
    ClockPane(int pendulum_angle, int pendulum_period, int pendulum_length) {
        this.pendulum_angle = pendulum_angle;
        this.pendulum_period = pendulum_period;
        this.pendulum_length = pendulum_length;
        ball_radius = pendulum_length / 7;
        start_time = (int)System.currentTimeMillis() / 1000;
        setBackground(new Color(200, 200, 255));
        calendar = new GregorianCalendar();
    }
    
    public void DrawTickMark(double angle, Graphics g) {
        int xw, yw, xz, yz;
        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x + r_inner * Math.sin(angle));
        yw = (int) (center_y - r_inner * Math.cos(angle));
        xz = (int) (center_x + r_outer * Math.sin(angle));
        yz = (int) (center_y - r_outer * Math.cos(angle));
        g.drawLine(xw, yw, xz, yz);
    }
    
    public void DrawHand(double angle, int length, Graphics g) {
        int xw, yw, xz, yz;
        angle = 3.1415 * angle / 180.0;
        xw = (int) (center_x + length * Math.sin(angle));
        yw = (int) (center_y - length * Math.cos(angle));
        angle += 3.1415;
        xz = (int) (center_x + TICK_LEN * Math.sin(angle));
        yz = (int) (center_y - TICK_LEN * Math.cos(angle));
        g.drawLine(xw, yw, xz, yz);
    }
    
    public void DrawDial(Graphics g) {
        g.drawOval(center_x - r_outer, center_y - r_outer, 2 * r_outer, 2 * r_outer);
        for (int i = 0; i <= 11; i++) {
            DrawTickMark(i * 30.0, g);
        }
    }

    public void DrawPendulum(Graphics g, int start_x, int start_y) {
        int x, y;
        int angle = getPendulumAngle();
        x = start_x + (int) (pendulum_length * Math.sin(Math.PI * angle / 180.0));
        y = start_y + (int) (pendulum_length * Math.cos(Math.PI * angle / 180.0));
        g.drawLine(start_x, start_y, x, y);
        g.fillOval(x - ball_radius, y - ball_radius, ball_radius * 2, ball_radius * 2);
    }

    public int getPendulumAngle() {
        double timeElapsed = (System.currentTimeMillis() / 1000.0) - start_time;
        return (int)(pendulum_angle * Math.cos((2 * Math.PI / pendulum_period) * timeElapsed));
    }
    
    public void paintComponent(Graphics g) {
        int minute, second, hour;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = getSize();
        center_x = size.width / 2;
        center_y = (size.height- pendulum_length - 2*ball_radius) / 2;
        r_outer = Math.min(size.width, size.height - pendulum_length - 2*ball_radius) / 2;
        r_inner = r_outer - TICK_LEN;
        Date time = new Date();
        calendar.setTime(time);
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR);
        if (hour > 11) hour = hour - 12;
        second = calendar.get(Calendar.SECOND);
        DrawDial(g);
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(5));
        DrawHand(360.0 * (hour * 60 + minute) / (60.0 * 12), (int) (0.75 * r_inner), g);
        g2d.setColor(new Color(255, 0, 0));
        g2d.setStroke(new BasicStroke(3));
        DrawHand(360.0 * (minute * 60 + second) / 3600.0, (int) (0.97 * r_outer), g);
        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(1));
        DrawHand(second * 6.0, (int) (0.97 * r_inner), g);
        DrawPendulum(g2d, center_x, center_y + r_outer);
    }
}

class ClockWindow extends JFrame {
    public ClockWindow(int pendulum_angle, int pendulum_period, int pendulum_length) {
        setContentPane(new ClockPane(pendulum_angle, pendulum_period, pendulum_length));
        setTitle("Clock");
    }
}
