/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohdah.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Omar
 */
public class DBConnection {

    private static Connection conn;
    private final static String URL = "jdbc:mysql://localhost:3306/ohdah";
    private final static String USER = "root";
    private final static String PASSWORD = "12345";

    /**
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Connection get() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null && !conn.isClosed()) {
                return conn;
            }
        return null;
    }
}
