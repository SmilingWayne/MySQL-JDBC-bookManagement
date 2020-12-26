package com.company;

import java.util.*;

import java.sql.*;

public class login {
    public static Connection conn = null;
    private static PreparedStatement ps = null;
    private static PreparedStatement ps2 = null;
    private static ResultSet rs = null;
    private static CallableStatement cs = null;
    public static int  login() throws ClassNotFoundException, SQLException{
        Scanner scan = new Scanner(System.in);
        int result = 1;
        int oper = 3;
        while(true){
            while(true) {
                try {
                    System.out.println("输入接下来的操作：");
                    System.out.println("0) 注册新用户");
                    System.out.println("1) 用户登录");
                    System.out.println("2) 退出登录界面");
                    oper = scan.nextInt();
                    break;
                }
                catch(Exception e){
                    System.out.println("输入非数字！");
                    e.printStackTrace();
                }
            }
            if(oper == 1 || oper == 0 || oper == 2){
                break;
            }
            else{
                System.out.println("输入错误，重新输入！");
            }
        }
        if(oper == 2){
            System.out.println("已退出。");
            return -1;
        }
        if(oper == 0){
            conn = DBConn.conn();
            String name = "";
            String code = "";
            String job = "";
            String mail = "";
            String gender = "";
            while(true){
                System.out.println("输入新用户名(不能含有空格,输入Exit退出):");
                name = scan.next();
                if(name.equals("Exit")){
                    return -1;
                }
                String sql2 = "select * from 管理员 where 姓名 = " + "'" + name + "';";
                try {
                    ps = conn.prepareStatement(sql2);
                    rs = ps.executeQuery();     //将查询的结果放入ResultSet结果集中

                    if(rs.next() == true){
                        System.out.println("用户名已存在！重新输入.");
                    }
                    else{
                        while(true){
                            System.out.println("输入密码(不超过20个字符，不少于4个字符)：");
                            code = scan.next();
                            if(code.length() <= 20 && code.length() >= 4){
                                break;
                            }
                            else{
                                System.out.println("密码太长或太短！");
                            }
                        }
                        System.out.println("输入职务：");
                        job = scan.next();
                        System.out.println("输入邮箱：");
                        mail = scan.next();
                        System.out.println("输入性别（男或女）：");
                        gender = scan.next();
                        String sql3 = "insert into 管理员 values(?,?,?,?,?);";
                        ps2 = conn.prepareStatement(sql3);
                        ps2.setString(1,name);
                        ps2.setString(2,code);
                        ps2.setString(3,job);
                        ps2.setString(4,mail);
                        ps2.setString(5,gender);
                        ps2.executeUpdate();
                        System.out.println("恭喜你，注册成功！");
                        break;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("检索错误！");
                }finally{
                    conn.close();
                }

            }



        }

        if(oper == 1){
            String name2 = "";
            String code2 = "";

            while(true){
                conn = DBConn.conn();
                System.out.println("输入您的姓名(输入Exit退出)：");
                name2 = scan.next();
                if(name2.equals("Exit")){
                    result = -1;
                    break;
                }
                System.out.println("输入您的密码：");
                code2 = scan.next();
                String sql4 = "select * from 管理员 where 姓名 = " + "'" + name2 + "' and 密码 = '" + code2 + "';";
                try{
                    ps = conn.prepareStatement(sql4);
                    rs = ps.executeQuery();
                    if(rs.next() == false){
                        System.out.println("密码错误或者用户名不存在！");
                    }
                    else{
                        System.out.println("登陆成功，欢迎你，" + name2 + "!");
                        return 1;

                    }

                }catch(SQLException e){
                    System.out.println("发生错误！");
                    e.printStackTrace();
                }finally{
                    conn.close();
                }

            }
        }
        return result;
    }
}
