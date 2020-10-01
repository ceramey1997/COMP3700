import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TotalPriceCalculatorGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField quantityTextField;
    private JLabel quantityLabel;
    private JTextField priceTextField;
    private JLabel priceLabel;
    private JButton calculateButton;
    private JLabel totalPriceLabel;

    public TotalPriceCalculatorGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // grab the text from the price and quantity text fields
                // convert to double
                int price = (int)((Double.parseDouble(priceTextField.getText())));
                int quantity = (int)((Double.parseDouble(quantityTextField.getText())));

                //calculate the totalPrice
                //update the totalPrice label
                int totalPrice = price * quantity;
                totalPriceLabel.setText("$" + totalPrice + " is the Total Price due");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new TotalPriceCalculatorGUI("Total Price Calculator");
        frame.setVisible(true);
    }
}
