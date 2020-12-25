package com.company;

import java.sql.*;
import java.util.*;

public class viewSelect {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static final CallableStatement cs = null;
    public static void select(String a){
        conn = DBConn.conn();
        /*
        1 查看vip客户;
        2 查询仓库库存;
        3 查询入库订单
        4 查询购买者及购买情况
        5 查询进货情况视图
        6 查询退货情况视图
        7 查询销售情况视图
        8 查询销售订单
         */
        Map<String ,String> map = new HashMap<>();
        map.put("1", "查看vip客户");
        map.put("2", "查询仓库库存");
        map.put("3", "查询入库订单");
        map.put("4", "查询购买者及购买情况");
        map.put("5", "查询进货情况视图");
        map.put("6", "查询退货情况视图");
        map.put("7", "查询销售情况视图");
        map.put("8", "查询销售订单");
        int am = Integer.parseInt(a);
        if(am >= 9 || am <= 0){
            System.out.println("输入错误！");
            return ;
        }
        String target = map.get(a);
        String sql = "SELECT * FROM " + target;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();     //将查询的结果放入ResultSet结果集中
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            for(int i = 1; i <= col; i ++ ){
                System.out.print(rsmd.getColumnName(i) + "\t");
            }
            System.out.println("");
            while(rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i)  + "\t");
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
