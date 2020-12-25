package com.company;

import java.sql.*;

import java.util.*;


public class tableSelect {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;
    public static void select(String a) {
        conn = DBConn.conn();
        /*
        1 仓库
        2 仓库信息表
        3 入库订单
        4 图书
        5 管理员
        6 购买者
        7 退货订单
        8 销售情况表
        9 销售订单
         */
        Map<String , String> map = new HashMap<>();
        map.put("1","仓库");
        map.put("2","仓库信息表");
        map.put("3","入库订单");
        map.put("4","图书");
        map.put("5","管理员");
        map.put("6","购买者");
        map.put("7","退货订单");
        map.put("8","销售情况表");
        map.put("9","销售订单");
        if(Integer.parseInt(a) >= 10 || Integer.parseInt(a) <= 0){
            System.out.println("输入错误！");
            return;
        }
        String target = map.get(a);

        String sql = "select * from " + target;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();     //将查询的结果放入ResultSet结果集中
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();

            System.out.println("");
            while(rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rsmd.getColumnName(i) + ": " + rs.getString(i)  + "\t");
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println("操作失败");
            e.printStackTrace();
        }finally {
            DBConn.close();
        }
    }
}
