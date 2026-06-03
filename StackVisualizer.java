package Visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StackVisualizer {
    public ArrayList<Node> nodes = new ArrayList<>();
    private int addressCounter = 1000;
    public Node highlightedNode = null;
    // Stack Operations
    public void push(int value)
    {
        nodes.add(new Node(value, addressCounter++));
    }
    public void pop() throws Exception {
        if (nodes.isEmpty()) throw new Exception();
        nodes.remove(nodes.size() - 1);
    }

    public void clear() {
        nodes.clear();
        highlightedNode = null;
    }

    public Node search(int value) {
        highlightedNode = null;
        for (Node n : nodes) {
            if (n.data == value) {
                highlightedNode = n;
                break;
            }
        }
        return highlightedNode;
    }

    // Drawing Method for Stack
    public void drawStack(Graphics g, Node highlighted) {
        int x = 100, stackBottomY = 400, boxHeight = 50, gap = 10;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            int boxY = stackBottomY - i * (boxHeight + gap);
            g.setColor(new Color(0, 102, 204));
            g.fillRect(x, boxY, 80, boxHeight);
            g.setColor(Color.WHITE);
            g.drawRect(x, boxY, 80, boxHeight);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(String.valueOf(n.data), x + 30, boxY + 30);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(String.valueOf(n.address), x + 45, boxY + 45);
        }
        if (!nodes.isEmpty()) {
            int topY = stackBottomY - (nodes.size() - 1) * (boxHeight + gap);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("TOP", x + 25, topY - 5);
        }
    }
}
