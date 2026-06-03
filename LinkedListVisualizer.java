package Visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

public class LinkedListVisualizer {
    public ArrayList<Node> nodes = new ArrayList<>();
    private int addressCounter = 1000;
    public Node highlightedNode = null;
    // Linked List Operations
    public void insertAtBeginning(int value)
    {
        nodes.add(0, new Node(value, addressCounter++));
    }

    public void insertAtEnd(int value)
    {
        nodes.add(new Node(value, addressCounter++));
    }
    public void insertAtPosition(int value, int pos) throws Exception
    {
        if (pos < 1 || pos > nodes.size() + 1) throw new Exception();
        nodes.add(pos - 1, new Node(value, addressCounter++));
    }
    public void deleteBeginning() throws Exception
    {
        if (nodes.isEmpty()) throw new Exception();
        nodes.remove(0);
    }
    public void deleteEnd() throws Exception
    {
        if (nodes.isEmpty()) throw new Exception();
        nodes.remove(nodes.size() - 1);
    }
    public void deleteAtPosition(int pos) throws Exception
    {
        if (pos < 1 || pos > nodes.size()) throw new Exception();
        nodes.remove(pos - 1);
    }
    public void clear() {
        nodes.clear();
        highlightedNode = null;
    }
    public Node search(int value)
    {
        highlightedNode = null;
        for (Node n : nodes)
        {
            if (n.data == value)
            {
                highlightedNode = n;
                break;
            }
        }
        return highlightedNode;
    }
    // Drawing Methods for Linked Lists
    public void drawSingly(Graphics g, Node highlighted)
    {
        int x = 100, y = 250;
        for (Node n : nodes)
        {
            drawNodeBox(g, n, x, y);
            if (nodes.indexOf(n) < nodes.size() - 1) drawArrow(g, x + 80, y + 25, x + 120, y + 25);
            if (highlighted == n) g.setColor(Color.RED);
            g.drawRect(x - 2, y - 2, 84, 54);
            x += 120;
        }
    }
    public void drawDoubly(Graphics g, Node highlighted)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        int x = 100, y = 250;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            drawNodeBox(g, n, x, y);
            if (i < nodes.size() - 1)
            {
                n.next = nodes.get(i + 1);
                nodes.get(i + 1).prev = n;
                drawArrow(g, x + 80, y + 25, x + 120, y + 25);
            }
            if (i > 0)
            {
                int startX = x;
                int startY = y + 25;
                int endX = x - 120 + 80;
                int endY = y + 25;
                QuadCurve2D curve = new QuadCurve2D.Float();
                curve.setCurve(startX, startY, (startX + endX) / 2, startY - 40, endX, endY);
                g2.setColor(Color.MAGENTA);
                g2.draw(curve);
                int arrowSize = 6;
                g2.fillPolygon(new int[]{endX, endX - arrowSize, endX - arrowSize},
                        new int[]{endY, endY - arrowSize, endY + arrowSize}, 3);
            }

            if (highlighted == n) {
                g.setColor(Color.RED);
                g.drawRect(x - 2, y - 2, 84, 54);
            }

            x += 120;
        }
    }
    public void drawCircular(Graphics g, Node highlighted)
    {
        drawSingly(g, highlighted);
        if (!nodes.isEmpty()) {
            int tailX = 100 + (nodes.size() - 1) * 120 + 40;
            int tailY = 250 + 25;
            int headX = 100 + 40;
            int headY = 250 + 25;
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            QuadCurve2D curve = new QuadCurve2D.Float();
            curve.setCurve(tailX, tailY, (tailX + headX) / 2, tailY - 80, headX, headY);
            g2.draw(curve);
            g2.fillPolygon(new int[]{headX, headX + 8, headX + 8}, new int[]{headY - 2, headY - 8, headY + 5}, 3);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("HEAD", headX - 20, headY - 10);
            g2.drawString("TAIL", tailX - 20, tailY + 35);
        }
    }
    // Common Draw Utilities
    private void drawNodeBox(Graphics g, Node n, int x, int y)
    {
        g.setColor(new Color(0, 102, 204));
        g.fillRect(x, y, 80, 50);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, 80, 50);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(String.valueOf(n.data), x + 15, y + 28);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(String.valueOf(n.address), x + 45, y + 45);
    }
    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2)
    {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
        int arrowSize = 5;
        g.fillPolygon(new int[]{x2, x2 - arrowSize, x2 - arrowSize},
                new int[]{y2, y2 - arrowSize, y2 + arrowSize}, 3);
    }
}
