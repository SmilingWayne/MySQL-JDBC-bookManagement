package com.company;
import java.sql.*;
import java.util.*;
public class DBConn {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/图书销售系统";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final Connection conn = null;
    private static PreparedStatement ps ;
    public static Connection conn(){
        Connection conn = null;
        try{
            Class.forName(JDBC_DRIVER);
            try{
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(){
        if(conn != null){
            try{
                conn.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
