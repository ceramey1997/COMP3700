import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private DataAdapter dataAdapter;
    private JTextField txtUserName = new JTextField(10);
    private JTextField txtPassword = new JTextField(10);
    private JButton    btnLogin    = new JButton("Login");

    public LoginUI(DataAdapter dataAdapter) {
        this.setSize(300, 400);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.getContentPane().add(new JLabel ("Store Management System"));

        JPanel panelUserName = new JPanel();
        panelUserName.add(new JLabel("Username:"));
        panelUserName.add(txtUserName);

        this.getContentPane().add(panelUserName);

        JPanel panelPassword = new JPanel();
        panelPassword.add(new JLabel("Password:"));
        panelPassword.add(txtPassword);

        this.getContentPane().add(panelPassword);

        this.getContentPane().add(btnLogin);
        
        this.dataAdapter = dataAdapter;

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public static class LoginController implements ActionListener {
        private LoginUI loginUI;
        private DataAdapter dataAdapter;


        public LoginController(LoginUI loginUI, DataAdapter dataAdapter) {
            this.loginUI = loginUI;
            this.dataAdapter = dataAdapter;
            this.loginUI.getBtnLogin().addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginUI.getBtnLogin()) {
                String username = loginUI.getTxtUserName().getText().trim();
                String password = loginUI.getTxtPassword().getText().trim();

                System.out.println("Login with username = " + username + " and password = " + password);
                User user = dataAdapter.loadUser(username, password);

                if (user == null) {
                    JOptionPane.showMessageDialog(null, "This user does not exist!");
                }
                else {
                    Application.getInstance().setCurrentUser(user);
                    this.loginUI.setVisible(false);
                    Application.getInstance().getMainScreen().setVisible(true);
                }
            }
        }
    }
}
