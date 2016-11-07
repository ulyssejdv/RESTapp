package bookstore;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ulysse on 06/11/2016.
 */
public class DbManager {

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/rest_app_db";
        String user = "root";
        String pass = "motdepasseroot";

        Connection c = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

}
