package DbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static Connection cnx=null;
    static {
        String url="jdbc:postgresql://localhost:5433/bible";
        String userName="postgres";
        String password="123456789";
        try {
            Class.forName("org.postgresql.Driver");
            cnx = DriverManager.getConnection(url, userName, password);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        return cnx;
    }
}
