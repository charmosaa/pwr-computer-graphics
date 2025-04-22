package Lab5;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Triangle2D {
    private Point[] vertices;
    private Color[] colors;
    private int totalPixels;

    public Triangle2D(Point[] vertices, Color[] colors) {
        this.vertices = vertices;
        this.colors = colors;
        this.totalPixels = 0;
    }

    public void drawTriangleIncr(BufferedImage image) {
        drawTriangle(image, null);
    }

    public void drawTriangleIncr(Graphics g) {
        drawTriangle(null, g);
    }

    private void drawTriangle(BufferedImage img, Graphics g) {
        Point[] v = vertices.clone();
        Color[] c = colors.clone();
        totalPixels = 0;

        // sorted vertices by y-coordinate
        for (int i = 0; i < 2; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (v[i].y > v[j].y) {
                    Point tmpV = v[i]; v[i] = v[j]; v[j] = tmpV;
                    Color tmpC = c[i]; c[i] = c[j]; c[j] = tmpC;
                }
            }
        }

        if (v[1].y == v[2].y) {
            drawFlatBottom(img, g, v[0], c[0], v[1], c[1], v[2], c[2]);
        } else if (v[0].y == v[1].y) {
            drawFlatTop(img, g, v[0], c[0], v[1], c[1], v[2], c[2]);
        } else {
            double alphaSplit = (double)(v[1].y - v[0].y) / (v[2].y - v[0].y);
            int newX = (int)(v[0].x + alphaSplit * (v[2].x - v[0].x));
            Point vi = new Point(newX, v[1].y);
            Color ci = interpolateColor(c[0], c[2], alphaSplit);

            drawFlatBottom(img, g, v[0], c[0], v[1], c[1], vi, ci);
            drawFlatTop(img, g, v[1], c[1], vi, ci, v[2], c[2]);
        }
    }

    private void drawFlatBottom(BufferedImage img, Graphics g, Point v0, Color c0, Point v1, Color c1, Point v2, Color c2) {
        int yStart = v0.y;
        int yEnd = v1.y;

        for (int y = yStart; y <= yEnd; y++) {
            double beta  = (double)(y - v0.y) / (v2.y - v0.y);

            int xStart = (int)(v0.x + beta * (v1.x - v0.x));
            int xEnd   = (int)(v0.x + beta  * (v2.x - v0.x));

            Color cStart = interpolateColor(c0, c1, beta);
            Color cEnd   = interpolateColor(c0, c2, beta);

            if (xStart > xEnd) {
                int tmpX = xStart; xStart = xEnd; xEnd = tmpX;
                Color tmpC = cStart; cStart = cEnd; cEnd = tmpC;
            }

            for (int x = xStart; x <= xEnd; x++) {
                double t = (double)(x - xStart) / (xEnd - xStart);
                Color c = interpolateColor(cStart, cEnd, t);
                setPixel(img, g, x, y, c);
            }
        }
    }

    private void drawFlatTop(BufferedImage img, Graphics g, Point v0, Color c0, Point v1, Color c1, Point v2, Color c2) {
        int yStart = v0.y;
        int yEnd = v2.y;

        for (int y = yStart; y <= yEnd; y++) {
            double beta  = (double)(y - v1.y) / (v2.y - v1.y);

            int xStart = (int)(v0.x + beta * (v2.x - v0.x));
            int xEnd   = (int)(v1.x + beta  * (v2.x - v1.x));

            Color cStart = interpolateColor(c0, c2, beta);
            Color cEnd   = interpolateColor(c1, c2, beta);

            if (xStart > xEnd) {
                int tmpX = xStart; xStart = xEnd; xEnd = tmpX;
                Color tmpC = cStart; cStart = cEnd; cEnd = tmpC;
            }

            for (int x = xStart; x <= xEnd; x++) {
                double t = (double)(x - xStart) / (xEnd - xStart);
                Color c = interpolateColor(cStart, cEnd, t);
                setPixel(img, g, x, y, c);
            }
        }
    }

    private void setPixel(BufferedImage img, Graphics g, int x, int y, Color color) {
        if (img != null) {
            img.setRGB(x, y, color.getRGB());
        } else if (g != null) {
            g.setColor(color);
            g.fillRect(x, y, 1, 1);
        }
        totalPixels++;
    }

    private Color interpolateColor(Color c1, Color c2, double t) {
        int r = (int)(c1.getRed()   + t * (c2.getRed()   - c1.getRed()));
        int g = (int)(c1.getGreen() + t * (c2.getGreen() - c1.getGreen()));
        int b = (int)(c1.getBlue()  + t * (c2.getBlue()  - c1.getBlue()));
        return new Color(inBounds(r), inBounds(g), inBounds(b));
    }

    private int inBounds(int value) {
        return Math.max(0, Math.min(255, value));
    }

    public int getPixels() {
        return totalPixels;
    }
}
