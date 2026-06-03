package Visualizer;
import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame
{
    private JPanel controlPanel, topPanel, sidePanel, centerPanel;
    private JTextField valueField, posField, searchField;
    private JLabel addressLabel;
    private String currentMode = "Singly Linked List";

    private LinkedListVisualizer listVisualizer = new LinkedListVisualizer();
    private StackVisualizer stackVisualizer = new StackVisualizer();

    public MainFrame() {
        setTitle("Linked List & Stack Visualizer");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(173, 216, 230));

        setupTopPanel();
        setupSidePanel();
        setupControlPanel();
        setupCenterPanel();
        setVisible(true);
    }
    private void setupTopPanel()
    {
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(70, 130, 180));
        topPanel.setPreferredSize(new Dimension(0, 60));
        JLabel title = new JLabel("Linked List & Stack Visualizer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);
        addressLabel = new JLabel("Address: - ");
        addressLabel.setForeground(Color.WHITE);
        searchField = new JTextField(12);
        JButton searchBtn = new JButton("Search");
        styleButton(searchBtn);
        searchBtn.addActionListener(e -> searchValue());
        rightPanel.add(addressLabel);
        rightPanel.add(searchField);
        rightPanel.add(searchBtn);

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
    }
    private void setupSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setBackground(Color.DARK_GRAY);
        sidePanel.setPreferredSize(new Dimension(200, 0));
        sidePanel.setLayout(new GridLayout(4, 1, 10, 10));
        String[] modes = {"Singly Linked List", "Doubly Linked List", "Circular Linked List", "Stack"};
        for (String mode : modes) {
            JButton btn = new JButton(mode);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            styleButton(btn);
            btn.addActionListener(e -> {
                currentMode = mode;
                JOptionPane.showMessageDialog(this, mode + " mode selected!");
                clearAll();
                updateControlPanel();
            });
            sidePanel.add(btn);
        }
        add(sidePanel, BorderLayout.WEST);
    }

    private void setupControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(100, 149, 237));
        valueField = new JTextField(8);
        posField = new JTextField(5);
        add(controlPanel, BorderLayout.SOUTH);
        updateControlPanel();
    }

    private void setupCenterPanel() {
        centerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawNodes(g);
            }
        };
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(1000, 650));
        add(centerPanel, BorderLayout.CENTER);
    }

    private void updateControlPanel() {
        controlPanel.removeAll();
        controlPanel.add(new JLabel("Value:"));
        controlPanel.add(valueField);

        if (!currentMode.equals("Stack")) controlPanel.add(new JLabel("Position:"));
        if (!currentMode.equals("Stack")) controlPanel.add(posField);

        if (currentMode.equals("Stack")) {
            JButton push = new JButton("Push");
            JButton pop = new JButton("Pop");
            JButton clear = new JButton("Clear All");
            JButton[] buttons = {push, pop, clear};
            for (JButton b : buttons) { styleButton(b); controlPanel.add(b); }
            push.addActionListener(e -> { try { stackVisualizer.push(Integer.parseInt(valueField.getText())); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Enter valid value!"); } });
            pop.addActionListener(e -> { try { stackVisualizer.pop(); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Stack is empty!"); } });
            clear.addActionListener(e -> { stackVisualizer.clear(); repaint(); });
        } else {
            JButton insertBeg = new JButton("Insert at Beginning");
            JButton insertMid = new JButton("Insert at Position");
            JButton insertEnd = new JButton("Insert at End");
            JButton delBeg = new JButton("Delete Beginning");
            JButton delEnd = new JButton("Delete End");
            JButton delPos = new JButton("Delete at Position");
            JButton clear = new JButton("Clear All");

            JButton[] buttons = {insertBeg, insertMid, insertEnd, delBeg, delEnd, delPos, clear};
            for (JButton b : buttons) { styleButton(b); controlPanel.add(b); }

            insertBeg.addActionListener(e -> { try { listVisualizer.insertAtBeginning(Integer.parseInt(valueField.getText())); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Enter valid value!"); } });
            insertMid.addActionListener(e -> { try { listVisualizer.insertAtPosition(Integer.parseInt(valueField.getText()), Integer.parseInt(posField.getText())); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Enter valid position or value!"); } });
            insertEnd.addActionListener(e -> { try { listVisualizer.insertAtEnd(Integer.parseInt(valueField.getText())); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Enter valid value!"); } });
            delBeg.addActionListener(e -> { try { listVisualizer.deleteBeginning(); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"List is empty!"); } });
            delEnd.addActionListener(e -> { try { listVisualizer.deleteEnd(); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"List is empty!"); } });
            delPos.addActionListener(e -> { try { listVisualizer.deleteAtPosition(Integer.parseInt(posField.getText())); repaint(); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Enter valid position!"); } });
            clear.addActionListener(e -> { listVisualizer.clear(); repaint(); });
        }

        controlPanel.revalidate();
        controlPanel.repaint();
    }

    private void styleButton(JButton b) {
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void drawNodes(Graphics g) {
        switch (currentMode) {
            case "Stack" -> stackVisualizer.drawStack(g, stackVisualizer.highlightedNode);
            case "Singly Linked List" -> listVisualizer.drawSingly(g, listVisualizer.highlightedNode);
            case "Doubly Linked List" -> listVisualizer.drawDoubly(g, listVisualizer.highlightedNode);
            case "Circular Linked List" -> listVisualizer.drawCircular(g, listVisualizer.highlightedNode);
        }
    }
    private void searchValue() {
        try {
            int val = Integer.parseInt(searchField.getText());
            listVisualizer.highlightedNode = listVisualizer.search(val);
            stackVisualizer.highlightedNode = stackVisualizer.search(val);
            if (listVisualizer.highlightedNode == null && stackVisualizer.highlightedNode == null) {
                JOptionPane.showMessageDialog(this,"Value not found!");
            } else if (listVisualizer.highlightedNode != null) {
                addressLabel.setText("Address: " + listVisualizer.highlightedNode.address);
            } else {
                addressLabel.setText("Address: " + stackVisualizer.highlightedNode.address);
            }
            repaint();
        } catch(Exception e){ JOptionPane.showMessageDialog(this,"Enter valid search value!"); }
    }
    private void clearAll() {
        listVisualizer.clear();
        stackVisualizer.clear();
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(MainFrame::new); }


}
