package com.doro.itf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DBCONNCTION - commons-dbcp-1.4.jar, commons-pool-1.4.jar ,jdbc use
public class DbConnectionManager {

    private static DbConnectionManager instance = null;

    public static DbConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DbConnectionManager.class) {
                if (instance == null) {
                    instance = new DbConnectionManager();
                }
            }
        }
        return instance;
    }

    private DbConnectionManager() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("org.apache.commons.dbcp.PoolingDriver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }

    // dbconnection
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection("jdbc:apache:commons:dbcp:/pool_RevIFMain");

    }

}
