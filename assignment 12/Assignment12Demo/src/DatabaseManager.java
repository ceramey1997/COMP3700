import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager databaseManager;
    public static DatabaseManager getInstance() {
        if (databaseManager == null) databaseManager = new DatabaseManager();
        return databaseManager;
    }

    private DatabaseManager() {}

    private Connection con() {
        //sqlite connection string
        String url = "jdbc:sqlite:D:\\Auburn University\\Fall 2020\\COMP3700\\12\\Assignment12Demo\\data\\store.db";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url);
            System.out.println("Connection Successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public String getProductInformation(String productID) {
        String result = "";
        String errorResult = "";
        Boolean cought = false;
        String sql = "SELECT * FROM Products WHERE ProductID=" + productID;

        try (Connection connection = this.con()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            //loop through the result set
            while (resultSet.next()) {
                //try catch because purchases table only has 3 columns.
                try {

                    result += resultSet.getString(1) + ", "
                            + resultSet.getString(2) + ", "
                            + resultSet.getString(3) + ", "
                            + resultSet.getString(4);
                    result += "";

                } catch (Exception e) {
                    cought = true;
                    errorResult += resultSet.getString(1) + ", "
                            + resultSet.getString(2) + ", "
                            + resultSet.getString(3);
                    errorResult += "";

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        result += "";
        if (cought == true) {
            return errorResult;
        } else{
            return result;
        }
    }
}
