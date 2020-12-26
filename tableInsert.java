package com.company;

import java.sql.*;

import java.util.*;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class tableInsert {
    public static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    public static void insert() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            conn = DBConn.conn();
            Map<String, String> map = new HashMap<>();

            map.put("1", "仓库");
            map.put("2", "入库订单");
            map.put("3", "图书");
            map.put("4", "管理员");
            map.put("5", "购买者");
            map.put("6", "退货订单");
            map.put("7", "销售订单");
            String target = "";
            while (true) {
                for (String list : map.keySet()) {
                    System.out.println(list + " : " + map.get(list));
                }
                System.out.println("输入想要插入信息的表:");
                target = scan.next();
                int num = Integer.parseInt(target);
                if (num > 9 || num < 1) {
                    System.out.println("输入数字错误，重新输入!");
                } else {
                    break;
                }
            }

            String helper = "";
            int m = Integer.parseInt(target);
            if (m == 1) {
                helper = " values(?,?);";
            } else if (m == 4 || m == 2) {
                helper = " values(?,?,?,?,?);";
            } else if (m == 3 || m == 7) {
                helper = " values(?,?,?,?,?,?);";
            } else if (m == 6) {
                helper = " values(?,?,?,?,?,?,?);";
            } else if (m == 5) {
                helper = " values(?,?,?,?);";
            }
            String sql = "Insert into " + map.get(target) + " " + helper;
            String s1 = "";
            String s2 = "";
            String s3 = "";
            String s4 = "";
            String s5 = "";
            String s6 = "";
            String s7 = "";
            if (m == 1) {
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        System.out.println("输入图书号:");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入库存总量:");
                        s2 = scan.next();
                    }
                }
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("操作结果如下：");
                    //这里要补充一个展示插入结果的函数进来
                    tableSelect test = new tableSelect();
                    test.select(target);
                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();
                }

            }
            if (m == 2) {
                for (int i = 0; i < 5; i++) {
                    if (i == 0) {
                        System.out.println("输入入库单号:");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入图书号：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入图书数量：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入备注:");
                        s5 = scan.next();
                    }
                    if (i == 4) {
                        LocalDateTime dateTime = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        s4 = dateTime.format(formatter);
                    }
                }
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后入库订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("3");
                    //这里要补充一个展示插入结果的函数进来

                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();

                }
            }
            if (m == 3) {
                for (int i = 0; i < 6; i++) {
                    if (i == 0) {
                        System.out.println("输入图书号：");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入图书名称：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入图书作者：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入图书类别：");
                        s4 = scan.next();
                    }
                    if (i == 4) {
                        System.out.println("输入图书价格：");
                        s5 = scan.next();
                    }
                    if (i == 5) {
                        System.out.println("输入备注(不超过25个字):");
                        s6 = scan.next();
                    }
                }
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.setString(6, s6);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后入库订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("4");
                    //这里要补充一个展示插入结果的函数进来

                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();

                }
            }
            if (m == 4) {
                for (int i = 0; i < 5; i++) {
                    if (i == 0) {
                        System.out.println("输入姓名：");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入密码：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入职务：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入邮箱：");
                        s4 = scan.next();
                    }
                    if (i == 4) {
                        System.out.println("输入性别：");
                        s5 = scan.next();
                    }
                }

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后入库订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("5");
                    //这里要补充一个展示插入结果的函数进来
                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();

                }
            }
            if (m == 5) {
                for (int i = 0; i < 4; i++) {

                    if (i == 0) {
                        System.out.println("输入购买者号码：");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入购买者姓名：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入联系方式：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入优惠状态：");
                        s4 = scan.next();
                    }
                }

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后入库订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("6");
                    //这里要补充一个展示插入结果的函数进来
                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();

                }
            }
            if (m == 6) {
                for (int i = 0; i < 7; i++) {
                    if (i == 0) {
                        System.out.println("输入退货单号：");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入图书号：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入退货数量：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入退货时间：");
                        s4 = scan.next();
                    }
                    if (i == 4) {
                        System.out.println("输入备注(不超过25个字)：");
                        s5 = scan.next();
                    }
                    if (i == 5) {
                        System.out.println("输入原购单号:");
                        s6 = scan.next();
                    }
                    if (i == 6) {
                        System.out.println("输入退货单价：");
                        s7 = scan.next();
                    }
                }
                scan.close();
                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.setString(6, s6);
                    ps.setString(7, s7);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后入库订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("7");
                    //这里要补充一个展示插入结果的函数进来
                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();
                    scan.close();
                }
            }
            if (m == 7) {
                for (int i = 0; i < 6; i++) {
                    if (i == 0) {
                        System.out.println("输入销售订单：");
                        s1 = scan.next();
                    }
                    if (i == 1) {
                        System.out.println("输入图书号：");
                        s2 = scan.next();
                    }
                    if (i == 2) {
                        System.out.println("输入图书价格：");
                        s3 = scan.next();
                    }
                    if (i == 3) {
                        System.out.println("输入购买者号码：");
                        s4 = scan.next();
                    }
                    if (i == 4) {
                        System.out.println("输入销售数量：");
                        s5 = scan.next();
                    }
                    if (i == 5) {
                        System.out.println("输入销售时间(形如 YYYY-MM-DD)：");
                        String u = scan.next();
                        System.out.println("输入具体时间(形如HH:MM:SS):");
                        s6 = scan.next();
                        s6 = u + " " + s6;
                    }
                }

                try {
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, s1);
                    ps.setString(2, s2);
                    ps.setString(3, s3);
                    ps.setString(4, s4);
                    ps.setString(5, s5);
                    ps.setString(6, s6);
                    ps.executeUpdate();
                    System.out.println("执行插入操作成功！");
                    System.out.println("插入后销售订单如下:");
                    tableSelect test = new tableSelect();
                    test.select("9");
                    //这里要补充一个展示插入结果的函数进来
                } catch (SQLException s) {
                    s.printStackTrace();
                    System.out.println("插入失败，检查数据是否符合规范！");
                } finally {
                    DBConn.close();
                }
            }
            String nextChoice = "";
            while(true) {
                System.out.println("是否继续增加数据？(y/n)");
                nextChoice = scan.next();
                if(nextChoice.equals("y") || nextChoice.equals("Y") || nextChoice.equals("N") || nextChoice.equals("n")){
                    break;
                }
                else{
                    System.out.println("输入错误，重新输入");
                }
            }
            if(nextChoice.equals("n") || nextChoice.equals("N")){
                break;
            }
        }
    }

}
