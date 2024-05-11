package com.yupi.usercenter.onece;

import com.alibaba.excel.EasyExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExcel {
    public static void main(String[] args) {
        // 写法1：JDK8+ ,不用额外写一个XingQiuTableUserInfoListener
        // since: 3.0.0-beta1
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
//        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
//        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
//        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new PageReadListener<XingQiuTableUserInfo>(dataList -> {
//            for (XingQiuTableUserInfo XingQiuTableUserInfo : dataList) {
//                log.info("读取到一条数据{}", JSON.toJSONString(XingQiuTableUserInfo));
//            }
//        })).sheet().doRead();


        // 有个很重要的点 XingQiuTableUserInfoListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法3：
        String fileName = "D:\\JAVA\\Code\\YuPiProject\\yupao-backend\\src\\main\\resources\\testExcel.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        readByListener(fileName);
        // 写法4
//        fileName = "D:\\JAVA\\Code\\YuPiProject\\yupao-backend\\src\\main\\resources\\testExcel.xlsx";
//        // 一个文件一个reader
//        try (ExcelReader excelReader = EasyExcel.read(fileName, XingQiuTableUserInfo.class, new XingQiuTableUserInfoListener()).build()) {
//            // 构建一个sheet 这里可以指定名字或者no
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            // 读取一个sheet
//            excelReader.read(readSheet);
//        }
    }

    /**
     * 通过监听器来进行excel数据读取
     * @param fileName
     */
    public static void readByListener(String fileName){
        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new TableListener()).sheet().doRead();
    }

    /**
     * 同步读
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XingQiuTableUserInfo> list = EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        for (XingQiuTableUserInfo xingQiuTableUserInfo : list) {
            System.out.println(xingQiuTableUserInfo);
        }
    }
}
