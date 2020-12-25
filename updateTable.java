package com.company;

import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class updateTable {

    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static PreparedStatement pst = null;
    private static ResultSet rscheck = null;
    private static ResultSet assRs = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;
    public static void update(){
        int firstChoice = 0;
        int secondChoice = 0;
        String sql = "call " + "";
        Scanner scan = new Scanner(System.in);
        Map<Integer, String> allMap = new HashMap<>();
        allMap.put(1,"更新入库订单");
        allMap.put(2,"更新销售订单");
        allMap.put(3,"退出");
        Map<Integer, String> mapIn = new HashMap<>();
        Map<Integer, String> mapOut = new HashMap<>();
        mapIn.put(1, "修改入库图书号");
        mapIn.put(2, "修改入库图书数量");
        mapIn.put(3, "修改入库时间");
        mapIn.put(4, "退出");
        mapOut.put(1, "修改销售图书价格");
        mapOut.put(2, "修改销售图书图书号");
        mapOut.put(3, "修改销售图书数量");
        mapOut.put(4, "修改销售图书入库时间");
        mapOut.put(5, "退出");
        Map<String ,String> mapOutHelp = new HashMap<>();
        for(int i = 1 ; i < 6; i ++ ){
            if(i == 1) {
                mapOutHelp.put(mapOut.get(i), "updateorder图书价格");
            }
            if(i == 2){
                mapOutHelp.put(mapOut.get(i), "updateorder图书号");

            }
            if(i == 3){
                mapOutHelp.put(mapOut.get(i), "updateorder销售数量");
            }
            if(i == 4){
                mapOutHelp.put(mapOut.get(i), "updateorder销售时间");
            }
        }
        while(true) {
            System.out.println("选择你想要进行的操作:(1~3)");
            for (int i = 1; i < allMap.size() + 1; i++) {
                System.out.println(i + ": " + allMap.get(i));
            }
            firstChoice = scan.nextInt();
            if (firstChoice > 3 || firstChoice < 0) {
                System.out.println("输入错误，重新输入");
            }


            if (firstChoice == 1) {
                while (true) {
                    System.out.println("以下为入库订单统计表：");
                    String findInSQL = "select * from 入库订单;";
                    try {
                        conn = DBConn.conn();
                        pst = conn.prepareStatement(findInSQL);
                        rscheck = pst.executeQuery();     //将查询的结果放入ResultSet结果集中
                        ResultSetMetaData rsmda = rscheck.getMetaData();
                        int col = rsmda.getColumnCount();
                        while(rscheck.next()) {
                            for (int i = 1; i <= col; i++) {
                                System.out.print(rsmda.getColumnName(i) + ": " + rscheck.getString(i)  + "\t");
                            }
                            System.out.println("");
                        }
                    } catch (SQLException e) {
                        System.out.println("操作失败");
                        e.printStackTrace();
                    }

                    for (int i = 1; i < mapIn.size() + 1; i++) {
                        System.out.println(i + ": " + mapIn.get(i));
                    }
                    while(true){
                        try{
                            System.out.println("输入你想要进行的操作:");
                            secondChoice = scan.nextInt();
                            break;
                        }
                        catch(Exception e){
                            System.out.println("输入非数字!重新输入.");
                        }
                    }
                    if (secondChoice > 4 || secondChoice < 0) {
                        System.out.println("输入错误，重新输入");
                        continue;
                    }
                    if (secondChoice == 4) {
                        break;
                    }
                    sql = "call " + mapIn.get(secondChoice) + "(?,?);";
                    System.out.println("输入想要修改书籍的入库单号:");
                    String str1 = scan.next();
                    String str2 = "";
                    if (secondChoice == 1) {
                        System.out.println("输入修改后的图书号:");
                        str2 = scan.next();
                    } else if (secondChoice == 2) {
                        System.out.println("输入修改后的图书数量:");
                        str2 = scan.next();
                    } else {
                        String judge = "";
                        while(true){
                            System.out.println("是否使用当前时间输入？（y/n）");
                            judge = scan.next();
                            if(judge.equals("y") || judge.equals("Y") || judge.equals("N") || judge.equals("n")){
                                break;
                            }
                            else{
                                System.out.println("输入错误，重新输入!");
                            }
                        }
                        if(judge.equals("y") || judge.equals("Y")){
                            LocalDateTime dateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            str2 = dateTime.format(formatter);
                        }
                        else{
                            System.out.println("输入修改时间(形如yyyy-MM-dd):");
                            str2 = scan.next();
                            System.out.println("输入具体时间：");
                            String help5 = scan.next();
                            str2 = str2 + " " + help5;
                        }
                    }
                    try {
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, str1);
                        ps.setString(2, str2);
                        ps.execute();
                        System.out.println("操作成功！已完成修改！");

                    } catch (SQLException e) {
                        System.out.println("操作失败，检查输入是否有误！重新操作！");
                        e.printStackTrace();
                    } finally {
                        DBConn.close();
                    }
                }

            }
            if (firstChoice == 2) {
                while(true){
                    System.out.println("以下为销售订单统计表：");
                    String findInSQL = "select * from 销售订单;";
                    try {
                        conn = DBConn.conn();
                        pst = conn.prepareStatement(findInSQL);
                        rscheck = pst.executeQuery();     //将查询的结果放入ResultSet结果集中
                        ResultSetMetaData rsmda = rscheck.getMetaData();
                        int col = rsmda.getColumnCount();
                        while(rscheck.next()) {
                            for (int i = 1; i <= col; i++) {
                                System.out.print(rsmda.getColumnName(i) + ": " + rscheck.getString(i)  + "\t");
                            }
                            System.out.println("");
                        }
                    } catch (SQLException e) {
                        System.out.println("操作失败");
                        e.printStackTrace();
                    }finally {
                        DBConn.close();
                    }
                    System.out.println("输入你想要修改的对象:");
                    for (int i = 1; i < mapOut.size() + 1; i++) {
                        System.out.println(i + ": " + mapOut.get(i));
                    }
                    secondChoice = scan.nextInt();
                    if (secondChoice > 5 || secondChoice < 0) {
                        System.out.println("输入错误，重新输入");
                        continue;
                    }
                    if (secondChoice == 5) {
                        break;
                    }
                    /*
                    mapOut.put(1, "修改销售图书价格");
                    mapOut.put(2, "修改销售图书图书号");
                    mapOut.put(3, "修改销售图书数量");
                    mapOut.put(4, "修改销售图书入库时间");
                    mapOut.put(5, "退出");
                     */

                    sql = "call " + mapOutHelp.get(mapOut.get(secondChoice)) + "(?,?);";
                    System.out.println("输入想要销售书籍的销售单号:");
                    String str1 = scan.next();
                    String str2 = "";
                    if (secondChoice == 1) {
                        System.out.println("输入修改后的图书价格:");
                        str2 = scan.next();
                    } else if (secondChoice == 2) {
                        System.out.println("输入修改后的图书号:");
                        str2 = scan.next();
                    } else if(secondChoice == 3){
                        System.out.println("输入修改后的图书数量:");
                        str2 = scan.next();
                    }
                    else if(secondChoice == 4){
                        String t = "";
                        while(true) {
                            System.out.println("是否选择填入当前时间作为修改时间？(y/n)");
                            t = scan.next();
                            if(t.equals("y") || t.equals("Y") || t.equals("N") || t.equals("n")){
                                break;
                            }
                            else{
                                System.out.println("输入错误，检查输入。");
                            }
                        }
                        if(t.equals("Y") || t.equals("y")){
                            LocalDateTime dateTime = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            str2 = dateTime.format(formatter);
                        }
                        else{
                            System.out.println("输入修改时间(形如yyyy-MM-dd):");
                            str2 = scan.next();
                            System.out.println("输入具体时间：");
                            String help5 = scan.next();
                            str2 = str2 + " " + help5;
                        }
                    }
                    else{
                        break;
                    }
                    try {
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, str1);
                        ps.setString(2, str2);
                        ps.execute();
                        System.out.println("执行成功！已完成修改！");
                    } catch (SQLException e) {
                        System.out.println("操作失败，重新操作！");
                        e.printStackTrace();
                    } finally {
                        DBConn.close();
                    }
                }
            } else if (firstChoice == 3) {
                break;
            }
        }
    }
}
