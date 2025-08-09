import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.HashMap;

public class CurrencyConverterGUI extends JFrame {
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    private HashMap<String, Double> rates;

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Currency Rates (base: USD)
        rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.86);
        rates.put("GBP", 0.74);
        rates.put("INR", 87.58);
        rates.put("JPY", 147.82);
        rates.put("AUD", 1.53);
        rates.put("CAD", 1.38);
        rates.put("CNY", 7.18);
        rates.put("AED", 3.67);
        rates.put("SAR", 3.75);

        String[] currencies = rates.keySet().toArray(new String[0]);

        // Fonts
        Font font = new Font("Arial", Font.PLAIN, 16);

        // Panel with Premium Gradient
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 219, 251);  // Light Sky Blue
                Color color2 = new Color(142, 84, 233);  // Soft Purple
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // From Currency
        JLabel fromLabel = new JLabel("From Currency:");
        fromLabel.setFont(font);
        fromCurrency = new JComboBox<>(currencies);
        fromCurrency.setFont(font);

        // To Currency
        JLabel toLabel = new JLabel("To Currency:");
        toLabel.setFont(font);
        toCurrency = new JComboBox<>(currencies);
        toCurrency.setFont(font);

        // Amount Field
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(font);
        amountField = new JTextField();
        amountField.setFont(font);

        // Buttons
        JButton convertButton = new JButton("Convert");
        convertButton.setFont(font);
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(font);

        // Result Label
        resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(Color.BLUE);

        // Add components to panel
        panel.add(fromLabel);
        panel.add(fromCurrency);
        panel.add(toLabel);
        panel.add(toCurrency);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(convertButton);
        panel.add(clearButton);

        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        // Convert Action
        convertButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = (String) fromCurrency.getSelectedItem();
                String to = (String) toCurrency.getSelectedItem();

                double usdAmount = amount / rates.get(from);
                double converted = usdAmount * rates.get(to);

                DecimalFormat df = new DecimalFormat("#,##0.00");
                resultLabel.setText("Converted Amount: " + df.format(converted) + " " + to);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear Action
        clearButton.addActionListener(e -> {
            amountField.setText("");
            fromCurrency.setSelectedIndex(0);
            toCurrency.setSelectedIndex(0);
            resultLabel.setText("Converted Amount: ");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CurrencyConverterGUI().setVisible(true);
        });
    }
}
