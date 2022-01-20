package DBUtils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import Parser.ParserUtils;
import java.sql.Connection;

public class Database {
    
    private static final String CONFIG_FILE =
    		"/var/lib/tomcat8/webapps/ProjetWeb/common/access.config";
    
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        
        // check if the DB was initialized
        if (URL == null && USERNAME == null && PASSWORD == null) {
            // if not
            initDBConnection();
        }
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }
    
    public static ResultSet execQuery(String query) throws SQLException {
        ResultSet res = null;
        Connection db = Database.getConnection();
        Statement stmt = db.createStatement();
        res = stmt.executeQuery(query);
        //db.close();
        return res;
    }
    
    public static int execUpdate(String query) throws SQLException {
        Connection db = Database.getConnection();
        Statement stmt = db.createStatement();
        int r = stmt.executeUpdate(query);
        // db.close();
        return r;
    }
    
    private static void initDBConnection() {
        Map<String, String> attrs = ParserUtils.readConfigFile(CONFIG_FILE);
        if (attrs == null) {
            throw new RuntimeException("Failed to load configuration file");
        }

        String host = attrs.get("host");
        String db = attrs.get("db");
        URL = "jdbc:mysql://" + host + ":3306/" + db;
        USERNAME = attrs.get("username");
        PASSWORD = attrs.get("password");
    }
}