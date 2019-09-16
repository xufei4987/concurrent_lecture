package com.youxu.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @description TODO
 * @Author YouXu
 * @Date 2019/8/1 15:29
 **/
public class MyDataSource {
    private LinkedList<Connection> pool = new LinkedList<>();
    private static final int INIT_CONNECTIONS = 10;
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private String url;
    private String userName;
    private String password;
    private static volatile MyDataSource dataSource;

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MyDataSource(String url, String userName, String password) {
        try {
            for (int i = 0; i < INIT_CONNECTIONS; i++) {
                Connection connection = DriverManager.getConnection(url, userName, password);
                pool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MyDataSource getDataSource(String url, String userName, String password) {
        if (dataSource == null) {
            synchronized (MyDataSource.class) {
                if (dataSource == null) {
                    dataSource = new MyDataSource(url, userName, password);
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection() {
        Connection connection = null;
        synchronized (pool) {
            while (pool.size() <= 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!pool.isEmpty()) {
                connection = pool.removeFirst();
            }
        }
        return connection;

    }

    public void release(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                notifyAll();
            }
        }
    }
}
