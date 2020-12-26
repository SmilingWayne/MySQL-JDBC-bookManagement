package com.company;

import java.util.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/图书管理系统";
    static final String USER = "root";
    static final String PASS = "root";
    static final String helper = "jdbc:mysql://localhost:3306/";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        // prod.procedure(a);
        /*
        * ** 查找和视图
        * ** 更新操作
        * ** 删除操作
        * ** 添加操作
        * ** 权限设置
        *


         */
        Map<Integer, String> map = new HashMap<>();

        map.put(1, "查看视图");
        map.put(2, "条件检索");
        map.put(3, "更新操作");
        map.put(4, "插入数据");
        map.put(5, "删除数据");
        map.put(6, "退出");

        Map<String ,String> mapView = new HashMap<>();
        mapView.put("1", "查看vip客户");
        mapView.put("2", "查询仓库库存");
        mapView.put("3", "查询入库订单");
        mapView.put("4", "查询购买者及购买情况");
        mapView.put("5", "查询进货情况视图");
        mapView.put("6", "查询退货情况视图");
        mapView.put("7", "查询销售情况视图");
        mapView.put("8", "查询销售订单");
        mapView.put("9", "退出");
        boolean visited = false;
        while(true) {
            login lgn = new login();
            int res = lgn.login();
            if(res < 0){
                break;
            }
            while(true) {
                int trigger = -1;
                String t = "";
                try{

                    for (int i = 1; i < 7; i++) {
                        System.out.println(i + ") " + map.get(i));
                    }
                    System.out.println("选择进行的操作:");
                    if(visited){
                        //String u = scan.next();
                        //System.out.println(u);
                    }
                    t = scan.nextLine();
                    trigger = Integer.parseInt(t);
                }
                catch(Exception s){
                    s.printStackTrace();
                    //System.out.println(t);
                    System.out.println("输入非数字，重新输入");
                }
                if(trigger > 6 || trigger <= 0){
                    System.out.println("输入错误，重新输入");
                    continue;
                }
                if(trigger == 1){
                    int trigger1 = -1;
                    while(true){
                        for(int i = 1 ; i < 10; i ++){
                            System.out.println(i + ") " + mapView.get(i + ""));
                        }
                        System.out.println("选择下一个操作：");
                        String g = scan.nextLine();
                        trigger1 = Integer.parseInt(g);
                        if(trigger > 9 || trigger <= 0){
                            System.out.println("输入错误，重新输入！");
                            continue;
                        }
                        else{
                            break;
                        }
                    }
                    if(trigger1 == 9){
                        System.out.println("已退出视图查询窗口.");
                        break;
                    }
                    else{
                        viewSelect vs = new viewSelect();
                        vs.select(trigger1 + "");
                    }
                    visited = true;
                }
                if(trigger == 2){
                    checkandFound caf = new checkandFound();
                    caf.check();
                    visited = true;
                }
                if(trigger == 3){
                    updateTable ut = new updateTable();
                    ut.update();
                    visited = true;
                }
                if(trigger == 4){
                    tableInsert ti = new tableInsert();
                    ti.insert();
                    visited = true;
                }
                if(trigger == 5){
                    deleteProcedure dP = new deleteProcedure();
                    dP.deletePro();
                    visited = true;
                }
                if(trigger == 6){
                    String cho = "";
                    while(true) {
                        try {
                            System.out.println("是否确定退出？(y/n)");
                            cho = scan.next();
                            if (cho.equals("Y") || cho.equals("y") || cho.equals("N") || cho.equals("n")) {
                                break;
                            }
                        } catch (Exception x) {
                            System.out.println("输入错误！");
                        }
                    }
                    if(cho.equals("Y") || cho.equals("y"))
                        break;
                    }
                }

            }
        System.out.println("已退出数据库.");
        }
    }



