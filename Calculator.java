// Import the necessary packages for the graphical user interface and event handling.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A simple calculator GUI application using Java Swing.
 */

public class Calculator extends JFrame {

    private JPanel container = new JPanel();

    // Labels for the calculator buttons
    String[] buttonLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "=", "C", "+", "-", "*", "/"};
    JButton[] buttons = new JButton[buttonLabels.length];

    private JLabel display = new JLabel();
    private Dimension defDimension = new Dimension(61, 60);
    private Dimension opDimension = new Dimension(80, 47);
    private double number1;
    private boolean operatorClicked = false, update = false;
    private String operator = "";

    public Calculator() {
        // Set up the main frame
        this.setSize(320, 408); //(Width, Height)
        this.setTitle("Calculator"); //Program name
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initComponents();
        this.setContentPane(container);
        this.setVisible(true);
    }

    private void initComponents() {
        // Set up the components and layout
        Font font = new Font("Helvetica", Font.BOLD, 36); // Font for the display
        display = new JLabel("0");
        display.setFont(font);
        display.setHorizontalAlignment(JLabel.CENTER);
        display.setPreferredSize(new Dimension(380, 60));

        // Panels for operators, numbers, and display
        JPanel operatorPanel = new JPanel();
        operatorPanel.setPreferredSize(new Dimension(89, 300));
        JPanel numberPanel = new JPanel();
        numberPanel.setPreferredSize(new Dimension(320 - 120, 300));
        JPanel displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(280, 80));

        // Create and customize buttons
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBackground(new Color(51, 51, 51));
            buttons[i].setForeground(Color.white);
            buttons[i].setFont(new Font("Helvetica", Font.BOLD, 18));
            buttons[i].setPreferredSize(defDimension);

            switch (i) {
                case 11: // "=" button
                    buttons[i].setForeground(Color.white);
                    buttons[i].setBackground(new Color(167, 45, 69));
                    buttons[i].addActionListener(new EqualListener());
                    numberPanel.add(buttons[i]);
                    break;

                case 12: // "C" (Clear) button
                    buttons[i].setForeground(Color.white);
                    buttons[i].setBackground(new Color(167, 45, 69));
                    buttons[i].addActionListener(new ResetListener());
                    buttons[i].setPreferredSize(opDimension);
                    operatorPanel.add(buttons[i]);
                    break;

                case 13: // "+" button
                    buttons[i].setForeground(Color.white);
                    buttons[i].addActionListener(new PlusListener());
                    buttons[i].setPreferredSize(opDimension);
                    operatorPanel.add(buttons[i]);
                    break;

                case 14: // "-" button
                    buttons[i].setForeground(Color.white);
                    buttons[i].addActionListener(new MinusListener());
                    buttons[i].setPreferredSize(opDimension);
                    operatorPanel.add(buttons[i]);
                    break;

                case 15: // "*" button
                    buttons[i].setForeground(Color.white);
                    buttons[i].addActionListener(new MultiplyListener());
                    buttons[i].setPreferredSize(opDimension);
                    operatorPanel.add(buttons[i]);
                    break;

                case 16: // "/" button
                    buttons[i].setForeground(Color.white);
                    buttons[i].addActionListener(new DivideListener());
                    buttons[i].setPreferredSize(opDimension);
                    operatorPanel.add(buttons[i]);
                    break;

                default: // Digit buttons
                    buttons[i].addActionListener(new DigitListener());
                    numberPanel.add(buttons[i]);
                    break;
            }
        }

        // Set up panels and container
        displayPanel.add(display);
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 6));
        displayPanel.setBackground(new Color(195, 194, 171));
        operatorPanel.setBackground(new Color(223, 216, 208));
        numberPanel.setForeground(new Color(223, 216, 208));
        numberPanel.setBackground(new Color(223, 216, 208));
        container.setBackground(new Color(223, 216, 208));

        // Add panels to the container
        container.add(displayPanel, BorderLayout.NORTH);
        container.add(numberPanel, BorderLayout.CENTER);
        container.add(operatorPanel, BorderLayout.EAST);

        // Create an outer panel to wrap the entire calculator interface
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setPreferredSize(new Dimension(310, 365));
        
        // Set a border for the outer panel to create an outline effect
        outerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 10));

        // Add the existing components (displayPanel, numberPanel, and operatorPanel) to the outer panel
        outerPanel.add(displayPanel, BorderLayout.NORTH);
        outerPanel.add(numberPanel, BorderLayout.CENTER);
        outerPanel.add(operatorPanel, BorderLayout.EAST);

        // Set the background color for the outer panel
        outerPanel.setBackground(new Color(223, 216, 208));

        // Add the outer panel to the container
        container.add(outerPanel, BorderLayout.CENTER);
    }

    private void performCalculation() {
        // Perform the calculation based on the operator
        double operand = Double.valueOf(display.getText()).doubleValue();
        switch (operator) {
            case "+":
                number1 += operand;
                break;
            case "-":
                number1 -= operand;
                break;
            case "*":
                number1 *= operand;
                break;
            case "/":
                if (operand != 0) {
                    number1 /= operand;
                } else {
                    display.setText("<html><font size='3'>Cannot be divided by 0</font></html>");
                    return;
                }
                break;
        }
        // Check the length of the result or number1
        String resultText = String.valueOf(number1);
        int resultLength = resultText.length();

        // Adjust the font size or switch to scientific notation if needed
        if (resultLength > 12) {
            // Adjust the font size as needed
            display.setText(String.format("<html><font size='6'>%s</font></html>", resultText));
        }
        else {
            display.setText(String.format(resultText));
        }
    }
 
    // Listener for digit buttons
    class DigitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String str = ((JButton) e.getSource()).getText();
    
            if (update) {
                update = false;
            } else {
                // Check if the display is not "0" or if the button pressed is a decimal point
                if (!display.getText().equals("0") || str.equals(".")) {
                    // Check if the current display text already contains a decimal point
                    if (!display.getText().contains(".") || !str.equals(".")) {
                        str = display.getText() + str;
                    }
                }
            }
    
            display.setText(str);
        }
    }
    
    // Listener for "=" button
    class EqualListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            performCalculation();
            update = true;
            operatorClicked = false;
        }
    }

    // Listener for "+" button
    class PlusListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (operatorClicked) {
                performCalculation();
                display.setText(String.valueOf(number1));
            } else {
                number1 = Double.valueOf(display.getText()).doubleValue();
                operatorClicked = true;
            }
            operator = "+";
            update = true;
        }
    }

    // Listener for "-" button
    class MinusListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (operatorClicked) {
                performCalculation();
                display.setText(String.valueOf(number1));
            } else {
                number1 = Double.valueOf(display.getText()).doubleValue();
                operatorClicked = true;
            }
            operator = "-";
            update = true;
        }
    }

    // Listener for "*" button
    class MultiplyListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (operatorClicked) {
                performCalculation();
                display.setText(String.valueOf(number1));
            } else {
                number1 = Double.valueOf(display.getText()).doubleValue();
                operatorClicked = true;
            }
            operator = "*";
            update = true;
        }
    }

    // Listener for "/" button
    class DivideListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (operatorClicked) {
                performCalculation();
                display.setText(String.valueOf(number1));
            } else {
                number1 = Double.valueOf(display.getText()).doubleValue();
                operatorClicked = true;
            }
            operator = "/";
            update = true;
        }
    }

    // Listener for "C" (Clear) button
    class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            operatorClicked = false;
            update = true;
            number1 = 0;
            operator = "";
            display.setText("0");
        }
    }

    // Main method to start the calculator
    public static void main(String[] args) {
        new Calculator();
        
    }
}
