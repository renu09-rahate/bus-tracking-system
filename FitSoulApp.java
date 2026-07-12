import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FitSoulApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FitSoulGUI());
    }
}

// ================= USER CLASS =================
class User {
    private String name;
    private double weight;
    private double height;

    public User(String name, double weight, double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }
}

// ================= BMI CALCULATOR =================
class BMICalculator {

    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    public String getCategory(double bmi) {

        if (bmi < 18.5)
            return "Underweight";

        else if (bmi < 25)
            return "Normal";

        else if (bmi < 30)
            return "Overweight";

        else
            return "Obese";
    }
}

// ================= DIET PLAN =================
class DietPlan {

    public String suggestDiet(double bmi) {

        if (bmi < 18.5)
            return "🥗 High Protein Diet";

        else if (bmi < 25)
            return "🍎 Balanced Healthy Diet";

        else if (bmi < 30)
            return "🥦 Low Calorie Diet";

        else
            return "🏃 Strict Low Calorie Diet + Daily Exercise";
    }
}

// ================= GUI =================
class FitSoulGUI extends JFrame implements ActionListener {

    private JTextField nameField;
    private JTextField weightField;
    private JTextField heightField;

    private JButton calculateButton;
    private JButton resetButton;

    private JLabel resultLabel;

    public FitSoulGUI() {

        setTitle("FitSoul - Smart Health System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Name"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Weight (kg)"));
        weightField = new JTextField();
        panel.add(weightField);

        panel.add(new JLabel("Height (m)"));
        heightField = new JTextField();
        panel.add(heightField);

        calculateButton = new JButton("Calculate BMI");
        calculateButton.setBackground(Color.GREEN);

        resetButton = new JButton("Reset");
        resetButton.setBackground(Color.ORANGE);

        panel.add(calculateButton);
        panel.add(resetButton);

        resultLabel = new JLabel("<html><center>Enter details and click Calculate</center></html>");
        resultLabel.setVerticalAlignment(SwingConstants.TOP);

        add(panel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);

        calculateButton.addActionListener(this);

        resetButton.addActionListener(e -> {
            nameField.setText("");
            weightField.setText("");
            heightField.setText("");
            resultLabel.setText("<html><center>Enter details and click Calculate</center></html>");
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                return;
            }

            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            if (weight <= 0 || height <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Weight and Height must be greater than zero.");
                return;
            }

            User user = new User(name, weight, height);

            BMICalculator bmi = new BMICalculator();

            double value = bmi.calculateBMI(user.getWeight(), user.getHeight());

            String category = bmi.getCategory(value);

            DietPlan diet = new DietPlan();

            String plan = diet.suggestDiet(value);

            resultLabel.setText(
                    "<html>"
                            + "<h2>Health Report</h2>"
                            + "<b>Name :</b> " + user.getName()
                            + "<br><b>Weight :</b> " + user.getWeight() + " kg"
                            + "<br><b>Height :</b> " + user.getHeight() + " m"
                            + "<br><b>BMI :</b> " + String.format("%.2f", value)
                            + "<br><b>Category :</b> " + category
                            + "<br><b>Diet Plan :</b> " + plan
                            + "</html>");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers.");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());
        }
    }
}