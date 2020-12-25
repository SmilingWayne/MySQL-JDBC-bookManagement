package com.company;

import java.sql.*;

import java.util.*;

public class checkandFound {

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;

    public static void check() {
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        while (true) {
            conn = DBConn.conn();
            Map<Integer, String> map = new HashMap<>();

            map.put(1, "查找用户消费记录");
            map.put(2, "查找盈利书籍");
            map.put(3, "模糊查找书名");
            map.put(4, "精确查询作者");
            map.put(5, "多重查找销售订单");
            map.put(6, "按销售总额查找");
            map.put(7, "获取书籍进货情况");
            map.put(8, "获取书籍退货情况");
            map.put(9, "退出查询");

            System.out.println("输入将要进行的操作：");
            for (int i = 1; i <= map.size(); i++) {
                System.out.println(i + ": " + map.get(i));
            }

            while (true) {
                choice = scan.nextInt();
                if (choice > 9 || choice < 0) {
                    System.out.println("输入有误，请重新输入！(1~9)");
                } else {
                    break;
                }
            }
            if(choice == 9){
                break;
            }
            String sql = "call ";
            if (choice == 2) {
                sql = sql + map.get(choice) + "();";
                try {
                    ps = conn.prepareStatement(sql);
                    ps.execute();
                    rs = ps.getResultSet();//将查询的结果放入ResultSet结果集中
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int col = rsmd.getColumnCount();
                    if(rs.next() == false){
                        System.out.println("未找到满足条件的信息.");
                    }
                    else {
                        rs.previous();
                        while (rs.next()) {
                            for (int i = 1; i <= col; i++) {
                                System.out.print(rsmd.getColumnName(i) + ":" + rs.getString(i) + "\t");
                            }
                            System.out.println("");
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("操作失败，重新操作！");
                    e.printStackTrace();
                } finally {
                    DBConn.close();
                }

            } else if (choice == 3 || choice == 4) {
                sql = sql + map.get(choice) + "(?);";
                String add1 = "";
                if (choice == 3) {
                    System.out.println("输入书名:");
                    add1 = scan.next();
                } else {
                    System.out.println("输入作者:");
                    add1 = scan.next();

                }
                try {
                    cs = conn.prepareCall(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    cs.setString(1, add1);
                    cs.execute();
                    rs = cs.getResultSet();
                    if(!rs.next()){
                        System.out.println("未找到满足条件的信息.");
                    }
                    else {
                        rs.previous();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int col = rsmd.getColumnCount();
                        while (rs.next()) {

                            for (int i = 1; i <= col; i++) {
                                String ans = "";

                                ans = rsmd.getColumnName(i) + ": " + rs.getString(i);

                                System.out.print(ans + "\t");
                            }
                            System.out.println("");
                        }
                    }

                } catch (SQLException s) {
                    System.out.println("查询操作失败,检查输入输出!");

                } finally {

                    DBConn.close();
                }

            } else if (choice == 1) {
                sql = sql + map.get(choice) + "(?,?);";
                String flag = "";
                while (true) {
                    System.out.println("是否精确查询？(y/n)");
                    flag = scan.next();
                    if (flag.equals("y") || flag.equals("Y") || flag.equals("N") || flag.equals("n")) {
                        break;
                    } else {
                        System.out.println("输入有误，重新输入!");
                    }
                }
                String add1 = "";
                String add2 = "";
                if (flag.equals("y") || flag.equals("Y")) {
                    System.out.println("输入您的编号：");
                    add1 = scan.next();
                    add2 = "1";
                } else {
                    System.out.println("输入您的姓名或想查询的名字:");
                    add1 = scan.next();
                    add2 = "-1";
                }
                try {
                    cs = conn.prepareCall(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    cs.setString(1, add1);
                    cs.setString(2, add2);
                    cs.execute();
                    rs = cs.getResultSet();
                    if(rs.next() == false){
                        System.out.println("未找到满足条件的信息.");
                    }
                    else {
                        rs.previous();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int col = rsmd.getColumnCount();
                        while (rs.next()) {

                            for (int i = 1; i <= col; i++) {
                                String ans = "";

                                ans = rsmd.getColumnName(i) + ": " + rs.getString(i);

                                System.out.print(ans + "\t");
                            }
                            System.out.println("");
                        }
                        if (rs == null) {
                            System.out.println("未查找到满足要求的信息。");
                        }
                    }
                } catch (SQLException s) {
                    System.out.println("查询操作失败,检查输入输出!");

                } finally {
                    DBConn.close();
                }

            } else if (choice == 5) {
                sql = sql + map.get(choice) + "(?,?);";
                System.out.println("输入目标销量：");
                int add1 = scan.nextInt();
                System.out.println("输入图书类别：");
                String add2 = scan.next();
                try {
                    cs = conn.prepareCall(sql);
                    cs.setInt(1, add1);
                    cs.setString(2, add2);
                    cs.execute();
                    rs = cs.getResultSet();
                    if(rs.next() == false){
                        System.out.println("未查找到满足要求的信息。");
                    }
                    else {
                        rs.previous();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int col = rsmd.getColumnCount();
                        while (rs.next()) {

                            for (int i = 1; i <= col; i++) {
                                String ans = "";
                                ans = rsmd.getColumnName(i) + ": " + rs.getString(i);

                                System.out.print(ans + "\t");
                            }
                            System.out.println("");
                        }
                    }
                } catch (SQLException s) {
                    System.out.println("查询操作失败,检查输入输出!");

                } finally {
                    DBConn.close();
                }
            }
            else if(choice == 6 || choice == 7 || choice == 8){

                String add1 = "";
                String add2 = "";
                if(choice == 6){
                    sql = sql + "checkbook总额(?);";
                    System.out.println("输入预计销售总额:");
                    add2 = scan.next();
                }
                else if(choice == 7){
                    sql = sql + "获取书籍进货情况(?);";
                    System.out.println("输入查询书籍的图书号:");
                    add1 = scan.next();
                }
                else if(choice == 8){
                    sql = sql + "获取书籍销售情况(?);";
                    System.out.println("输入查询书籍的图书号:");
                    add1 = scan.next();
                }
                try {
                    cs = conn.prepareCall(sql);
                    if(choice == 6) {
                        cs.setString(1, add2);
                    }
                    else {
                        cs.setString(1, add1);
                    }
                    cs.execute();
                    rs = cs.getResultSet();
                    if (rs.next() == false) {
                        System.out.println("未查找到满足要求的信息。");
                    } else {
                        rs.previous();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int col = rsmd.getColumnCount();
                        while (rs.next()) {

                            for (int i = 1; i <= col; i++) {
                                String ans = "";

                                ans = rsmd.getColumnName(i) + ": " + rs.getString(i);

                                System.out.print(ans + "\t");
                            }
                            System.out.println("");
                        }
                    }
                }catch(SQLException s){
                    System.out.println("输入有误，重新查询!");
                }
                finally {
                    DBConn.close();
                }
            }
        }
        System.out.println("查询结束。");
        scan.close();
    }
}
