package com.qxd.birth.common.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Created by xiangdong.qu on 17/1/4 17:09.
 * <p>
 * <p>
 * 1、File类的createNewFile根据抽象路径创建一个新的空文件，当抽象路径制定的文件存在时，创建失败
 * 2、File类的mkdir方法根据抽象路径创建目录
 * 3、File类的mkdirs方法根据抽象路径创建目录，包括创建必需但不存在的父目录
 * 4、File类的createTempFile方法创建临时文件，可以制定临时文件的文件名前缀、后缀及文件所在的目录，如果不指定目录，则存放在系统的临时文件夹下。
 * 5、除mkdirs方法外，以上方法在创建文件和目录时，必须保证目标文件不存在，而且父目录存在，否则会创建失败
 */
@Slf4j
public class IOTest {

    public static void main(String... args) {
        /*System.out.println("test");
        String s = "qxd";
        byte[] b = s.getBytes();
        for (byte temp : b) {
            System.out.println(temp);
        }*/


        try {
            //测试文件
            //testFile();
            //
            //testFileInputStream();

            testFileOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件夹 文件创建
     *
     * @throws IOException
     */
    private static void testFile() throws IOException {
        //文件操作
        File file = new File("/Users/admin/test/qxd/xxx.txt");
        System.out.println("绝对路径" + file.getAbsolutePath());
        //file.mkdirs();  //创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
        //file.createNewFile();  //当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
        File file1 = file.getParentFile();
        file1.mkdirs();
        file.createNewFile();
        System.out.println("父文件名称:" + file1.getName() + "\n父文件绝对路径:" + file1.getAbsolutePath());

        System.out.println("该分区大小" + file.getTotalSpace() / (1024 * 1024 * 1024) + "G"); //返回由此抽象路径名表示的文件或目录的名称。

        //f.delete();   //删除此抽象路径名表示的文件或目录
        System.out.println("文件名  " + file.getName());  //返回由此抽象路径名表示的文件或目录的名称。

        System.out.println("文件父目录字符串 " + file.getParent());// 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null。
    }

    /**
     * 测试文件输入流
     *
     * @throws IOException
     */
    public static void testFileInputStream() throws IOException {

        //TODO 自动生成的方法存根
        int count = 0;  //统计文件字节长度
        FileInputStream streamReader = null;   //文件输入流
        try {
            streamReader = new FileInputStream("/Users/admin/test/qxd/xxx.txt");
            // 进行转换的，FileInputStream是有缓冲区的，所以用完之后必须关闭，否则可能导致内存占满，数据丢失。
            while (streamReader.read() != -1) {  //读取文件字节，并递增指针到下一个字节
                count++;
            }
            System.out.println("---长度是： " + count + " 字节");

            String s = "\n";
            System.out.println(s.getBytes().length);
        } catch (final IOException e) {
            //TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
            try {
                streamReader.close();
            } catch (IOException e) {
                //TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试文件输出流
     */
    private static void testFileOutputStream() {

        byte[] buffer = new byte[512];   //一次取出的字节数大小,缓冲区大小

        int numberRead = 0;
        FileInputStream input = null;
        FileOutputStream out = null;
        try {
            input = new FileInputStream("/Users/admin/test/qxd/xxx.txt");
            out = new FileOutputStream("/Users/admin/test/qxd/xxx1.txt"); //如果文件不存在会自动创建

            while ((numberRead = input.read(buffer)) != -1) {  //numberRead的目的在于防止最后一次读取的字节小于buffer长度，
                out.write(buffer, 0, numberRead);       //否则会自动被填充0
            }
        } catch (final IOException e) {
            // TODO自动生成的 catch 块
            e.printStackTrace();
        } finally {
            try {
                input.close();
                out.close();
            } catch (IOException e) {
                // TODO自动生成的 catch 块
                e.printStackTrace();
            }

        }
    }

    /**
     * 测试对象 输入流,输出流
     */
    private static void objectStream() {

    }

    private static void printByteArray(byte[] b) {
        for (byte temp : b) {
            System.out.println(temp);
        }
    }
}
