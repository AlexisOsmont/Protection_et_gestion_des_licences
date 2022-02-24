package Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;

public class Database {
    
    private static String URL 	= "jdbc:mysql://localhsot:3306/auth";
    private static String USERNAME 	= "tomcat";
    private static String PASSWORD 	= "tomcatpasswd";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    public static ResultSet execQuery(String query) throws SQLException {
        ResultSet res = null;
        Connection db = Database.getConnection();
        Statement stmt = db.createStatement();
        res = stmt.executeQuery(query);
        db.close();
        return res;
    }
    
    public static int execUpdate(String query) throws SQLException {
        Connection db = Database.getConnection();
        Statement stmt = db.createStatement();
        int r = stmt.executeUpdate(query);
        db.close();
        return r;
    }
}
