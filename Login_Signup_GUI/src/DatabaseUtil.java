import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/diracmusicdb";
        String username = "root";
        String password = "Mysql618#";
        return DriverManager.getConnection(url, username, password);
    }
}
