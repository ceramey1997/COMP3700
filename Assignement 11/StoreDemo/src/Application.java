import java.sql.*;

public class Application {

    private static Application instance;   // Singleton pattern

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
    // Main components of this application

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private DataAdapter dataAdapter;

    private User currentUser = null;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Create the Product View and Controller here!

    private ProductUI productUI = new ProductUI();

    private CustomerUI customerUI = new CustomerUI();

    private CheckoutUI checkoutUI;

    private MainScreen mainScreen = new MainScreen();

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public ProductUI getProductView() {
        return productUI;
    }

    public CustomerUI getCustomerView() {
        return customerUI;
    }

    public CheckoutUI getCheckoutScreen() {
        return checkoutUI;
    }

    public LoginUI loginUI;

    public LoginUI getLoginScreen() {
        return loginUI;
    }

    public LoginUI.LoginController loginController; // = new LoginUI.LoginController(loginScreen, dataAdapter);


    private CustomerUI.CustomerController customerController;

    private ProductUI.ProductController productController;

    public ProductUI.ProductController getProductController() {
        return productController;
    }

//    private CheckoutController checkoutController;

//    public CheckoutController getCheckoutController() {
//        return checkoutController;
//    }

    public DataAdapter getDataAdapter() {
        return dataAdapter;
    }

    private void initializeDatabase(Statement stmt) throws SQLException {
        // create the tables and insert sample data here!

        // stmt.execute("create table Products (ProductID int PRIMARY KEY, ProductName char(30), Price double, Quantity double)");

    }

    private Application() {
        // create SQLite database connection here!
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:store.db");
            Statement stmt = connection.createStatement();
            if (!stmt.executeQuery("select * from Products").next()) // Product table do not exist
                initializeDatabase(stmt);


        }
        catch (ClassNotFoundException ex) {
            System.out.println("SQLite is not installed. System exits with error!");
            ex.printStackTrace();
            System.exit(1);
        }

        catch (SQLException ex) {
            System.out.println("SQLite database is not ready. System exits with error!" + ex.getMessage());

            System.exit(2);
        }

        // Create data adapter here!
        dataAdapter = new DataAdapter(connection);

        productController = new ProductUI.ProductController(productUI, dataAdapter);

        loginUI = new LoginUI(dataAdapter);

        checkoutUI = new CheckoutUI(dataAdapter);

        //checkoutController = new CheckoutController(checkoutUI, dataAdapter);

        loginController = new LoginUI.LoginController(loginUI, dataAdapter);

        customerController = new CustomerUI.CustomerController(customerUI, dataAdapter);
    }


    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }
}
