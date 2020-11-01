import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI extends JFrame {

    private JTextField txtCustomerID = new JTextField(10);
    private JTextField txtCustomerFirstName = new JTextField(10);
    private JTextField txtCustomerLastName = new JTextField(10);
    private JTextField txtCustomerPhoneNumber = new JTextField(10);

    private JButton btnLoad = new JButton("Load Customer");
    private JButton btnSave = new JButton("Save Customer");

    public CustomerUI() {
        this.setTitle("Customer View");
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setSize(500, 200);

        JPanel panelButton = new JPanel();
        panelButton.add(btnLoad);
        panelButton.add(btnSave);
        this.getContentPane().add(panelButton);

        JPanel panelCustomerID = new JPanel();
        panelCustomerID.add(new JLabel("Customer ID"));
        panelCustomerID.add(txtCustomerID);
        txtCustomerID.setHorizontalAlignment(JTextField.RIGHT);
        this.getContentPane().add(panelCustomerID);

        JPanel panelCustomerFirstName = new JPanel();
        panelCustomerFirstName.add(new JLabel("Customer FirstName: "));
        panelCustomerFirstName.add(txtCustomerFirstName);
        this.getContentPane().add(panelCustomerFirstName);

        JPanel panelCustomerLastName = new JPanel();
        panelCustomerLastName.add(new JLabel("Customer LastName: "));
        panelCustomerLastName.add(txtCustomerLastName);
        this.getContentPane().add(panelCustomerLastName);

        JPanel panelCustomerPhoneNumber = new JPanel();
        panelCustomerPhoneNumber.add(new JLabel("Customer PhoneNumber: "));
        panelCustomerPhoneNumber.add(txtCustomerPhoneNumber);
        this.getContentPane().add(panelCustomerPhoneNumber);

    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JTextField getTxtCustomerID() {
        return txtCustomerID;
    }

    public JTextField getTxtCustomerFirstName() {
        return txtCustomerFirstName;
    }

    public JTextField getTxtCustomerLastName() {
        return txtCustomerLastName;
    }

    public JTextField getTxtCustomerPhoneNumber() {
        return txtCustomerPhoneNumber;
    }

    public static class CustomerController implements ActionListener {
        private CustomerUI customerUI;
        private DataAdapter dataAdapter;

        public CustomerController(CustomerUI customerUI, DataAdapter dataAdapter) {
            this.dataAdapter = dataAdapter;
            this.customerUI = customerUI;

            customerUI.getBtnLoad().addActionListener(this);
            customerUI.getBtnSave().addActionListener(this);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == customerUI.getBtnLoad())
                loadCustomer();
            else if (e.getSource() == customerUI.getBtnSave())
                saveCustomer();
        }

        private void saveCustomer() {
            int customerID;
            try {
                customerID = Integer.parseInt(customerUI.getTxtCustomerID().getText());
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Customer ID!, Please Provide a valid customer ID!");
                return;
            }

            String customerFirstName = customerUI.getTxtCustomerFirstName().getText().trim();
            String customerLastName = customerUI.getTxtCustomerLastName().getText().trim();
            String customerPhoneNumber = customerUI.getTxtCustomerPhoneNumber().getText().trim();

            if (customerFirstName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Invalid Customer First Name! Please Provide a non-empty customer First Name!");
                return;
            }

            if (customerLastName.length() == 0) {
                JOptionPane.showMessageDialog(null, "Invalid Customer Last Name! Please Provide a non-empty customer Last Name!");
                return;
            }

            if (customerPhoneNumber.length() == 0) {
                JOptionPane.showMessageDialog(null, "Invalid Phone Number! Please Provide a non-empty Phone Number!");
                return;
            }

            // Validations complete, make an object

            Customer customer = new Customer(customerID, customerFirstName, customerLastName, customerPhoneNumber);
            dataAdapter.saveCustomer(customer);
            return;
        }

        private void loadCustomer() {
            int customerID = 0;
            try {
                customerID = Integer.parseInt(customerUI.getTxtCustomerID().getText());
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid customer ID! Please provide a valid customer ID!");
                return;
            }

            Customer customer = dataAdapter.loadCustomer(customerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null, "This CustomerID does not exist in the database!");
                return;
            }

            customerUI.getTxtCustomerFirstName().setText(customer.getFirstName());
            customerUI.getTxtCustomerLastName().setText(customer.getLastName());
            customerUI.getTxtCustomerPhoneNumber().setText(customer.getPhoneNumber());

        }
    }

}
