package com.yc.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumUtils {

    // 订单号前缀
    public static final String ORDER_PREFIX = "D";
    //支付订单前缀
    public static final String PAY_ORDER_PREFIX = "P";

    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 订单号生成
     *
     * @param
     * @return
     */
    public static String generateOrderNum(Long id){return ORDER_PREFIX + generateNum(id);}
    /**
     * 支付订单号生成
     */
    public static String generatePayOrderNum(Long id){return PAY_ORDER_PREFIX+generateNum(id);}
    /**
     * 处理能力，每秒1000笔订单不重复，最高每用户1000笔订单不重复，
     * 支持水平扩展，综合处理能力为每秒 1000 * N,N为水平扩展的微服务数量
     *
     * @param
     * @return
     */
    private static String generateNum(Long id) {
        String dateStr = df.format(new Date()).substring(2);
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //随机码
        int x = new Random().nextInt(90) + 10;

        //userId +
        return id+dateStr + x;
    }
}
