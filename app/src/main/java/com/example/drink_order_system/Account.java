package com.example.drink_order_system;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//  用户信息管理模块登录注册实现功能模块，主要参数设计账号、密码等
public class Account {
    private final String username;
    private final String password;
    private final Context mContext;

    Account(String username, String password, Context mContext) {
        this.username = username;
        this.password = password;
        this.mContext = mContext;
    }

    Account(String username) {
        this.username = username;
        this.password = "";
        mContext = null;
    }

    Account(String username, Context mContext) {
        this.username = username;
        this.password = null;
        this.mContext = mContext;
    }

    Account(String username, String password) {
        this.username = username;
        this.password = password;
        mContext = null;
    }


    // 判断俩个对象是否相等
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Account) {
            Account a = (Account) obj;
            return this.username.equals(a.username) && this.password.equals(a.password);
        }
        System.out.println("notInstance"); // 用户不存在
        return false;
    }


    //    FileInputStream打开一个名为"username+bill.txt"的文件，
    //    然后使用InputStreamReader和LineNumberReader来读取文件内容。主要实现查询用户账单信息
    private int getTotalLines() throws IOException {
        FileInputStream fis = mContext.openFileInput(username + "bill.txt");
        Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    //    读取文件内容
    static String readAppointedLineNumber(File sourceFile, int lineNumber) {
        if (lineNumber < 0) {
            return "NotFound";
        }
        try {
            FileReader in = new FileReader(sourceFile);
            LineNumberReader reader = new LineNumberReader(in);
            String s;
            int i;
            while (reader.readLine() != null) {
                i = reader.getLineNumber();
                if (i == lineNumber) {
                    s = reader.readLine();
                    System.out.println(s);
                    return s;
                }
            }
            reader.close();
            in.close();
            return "NotFound";
        } catch (IOException e) {
            return "NotFound";
        }
    }


    //    判断登录用户是否已经注册了
    public boolean exist() {
        try {
            FileOutputStream fos1;
            FileInputStream fis;
            if (mContext != null) {
                fos1 = mContext.openFileOutput("user_info.txt", mContext.MODE_APPEND);
                fis = mContext.openFileInput("user_info.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                    System.out.println(username + " " + password);
                    String[] list = s.split(",");
                    System.out.println(list[0] + "?" + list[1]);
                    Account temp = new Account(list[0], list[1]);
//                  判断是登录用户是否存在相等
                    System.out.println(temp.username.toLowerCase(Locale.ROOT).equals(this.username.toLowerCase(Locale.ROOT)) + " " + temp.password.equals(this.password));
                    if (temp.equals(this)) {
                        //System.out.println(s);
                        return true;
                    }
                }
                reader.close();
                in.close();
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

//    用于保存账户信息。它首先检查mContext是否为null，如果不为null，则执行以下操作：
//    打开名为"user_info.txt"的文件，以追加模式写入数据。
//    打开名为"user_info.txt"的文件，以读取模式读取数据。
//    使用InputStreamReader和LineNumberReader来读取文件内容。
//    逐行读取文件，将每行的内容分割成字符串数组。
//    如果数组的第一个元素与username相等（忽略大小写），则关闭reader和in，并返回false。
//    关闭reader和in。
//    再次打开名为"user_info.txt"的文件，以追加模式写入数据。
//    将username、password和一个换行符拼接成一个字符串。
//    将该字符串转换为字节数组，并以UTF-8编码写入文件。
//    打开名为"username+bill.txt"的文件，以追加模式写入数据。
//    如果在读取或写入文件过程中发生异常，将捕获IOException异常，并打印堆栈跟踪信息和"read error"。
//
    public boolean saveAccount() {
        try {
            FileInputStream fis;
            FileOutputStream fos;
            if (mContext != null) {
                fos = mContext.openFileOutput("user_info.txt", mContext.MODE_APPEND);
                fis = mContext.openFileInput("user_info.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                    String[] list = s.split(",");
                    if ((list[0]).equalsIgnoreCase(username)) {
                        System.out.println("equal");
                        reader.close();
                        in.close();
                        reader.close();
                        in.close();
                        return false;
                    }
                }
                reader.close();
                in.close();
                fos = mContext.openFileOutput("user_info.txt", mContext.MODE_APPEND);
                String info = username + "," + password + "\n";
                fos.write(info.getBytes(StandardCharsets.UTF_8));
                fos = mContext.openFileOutput(username + "bill.txt", mContext.MODE_APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("read error");
        }
        return true;
    }

//    保存用户下单的具体信息
    public void saveBill(String takeAway, String cost) {
        try {
            FileOutputStream fos = mContext.openFileOutput(username + "bill.txt", mContext.MODE_APPEND);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            SimpleDateFormat billDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// HH:mm:ss
            int line = getTotalLines();
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String orderNumber = billDateFormat.format(date) + String.format("%05d", line);
            String info = orderNumber + "," + simpleDateFormat.format(date) + "," + takeAway + "," + cost + "\n";
            System.out.println(info);
            fos.write(info.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("write error");
        }
    }
}
