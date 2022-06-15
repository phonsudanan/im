package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public  Connect() throws ClassNotFoundException {
    String url = "jdbc:mysql://localhost/land_tax";
    String user = "root";
    String pass = "12345678";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,user,pass);
        if (con != null) {
            System.out.println("Database Connected.");
        } else {
            System.out.println("Database Connect Failed.");
        }

    } catch (SQLException e){
        e.printStackTrace();
    }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Connect main = new Connect();
    }



}
