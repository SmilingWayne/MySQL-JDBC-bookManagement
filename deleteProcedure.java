package com.company;

import java.sql.*;

import java.util.*;

public class deleteProcedure {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;
    public static void deletePro(){

        conn = DBConn.conn();
        String sql = "call ";
        Scanner scan = new Scanner(System.in);

        Map<Integer , String> map = new HashMap<>();
        map.put(1,"删除入库订单");
        map.put(2,"删除退货订单");
        map.put(3,"删除销售订单");
        map.put(4,"批量删除图书号");
        System.out.println("选择将要进行的操作：");
        for(int i = 1; i <= map.size(); i ++ ){
            System.out.println(i + ": " + map.get(i));
        }
        int choice = 0;
        while(true) {
            choice = scan.nextInt();
            if(choice > 4 || choice < 1){
                System.out.println("输入错误，重新输入！(1~4)");
            }
            else{
                break;
            }
        }
        String add = "";
        if(choice == 1){
            System.out.println("输入入库单号:");
            add = scan.next();
        }
        else if(choice == 2){
            System.out.println("输入退货单号:");
            add = scan.next();
        }
        else if(choice == 3){
            System.out.println("输入销售单号:");
            add = scan.next();
        }
        else{
            System.out.println("输入批量删除书号:");
            add = scan.next();
        }
        sql = sql + map.get(choice) + "(?);";
        try{
            tableSelect ts = new tableSelect();

            cs = conn.prepareCall(sql);
            cs.setString(1, add);
            cs.execute();
            System.out.println("操作成功！");
            if(choice == 1){
                ts.select("3");
            }
            else if(choice == 2){
                ts.select("7");
            }
            else if(choice == 3){
                ts.select("9");
            }
            else{
                ts.select("4");
            }
        }
        catch(SQLException s){
            System.out.println("查找失败，检查数据是否符合规范！");
            s.printStackTrace();

        }finally {

            DBConn.close();
        }



    }
}
