/**
 * @版权所有 城云科技（杭州）有限公司
 */
package com.yc.common.utils;

import java.math.BigDecimal;

/**
 * 金额计算工具类
 *
 * @author changyq
 * @version 2017/3/21 16:07
 */
public class MoneyUtil {

    //认保留2位小数
    public static final int SCALE = 2;

    /**
     * 计算单价乘以数量，默认保留2位小数
     *
     * @param args1
     * @param args2
     * @return
     */
    public static BigDecimal multipy(BigDecimal args1, BigDecimal args2) {
        return multipy(args1, args2, SCALE);
    }

    /**
     * 两个BigDecimal对象相乘,保留scale位
     *
     * @param args1
     * @param args2
     * @param scale
     * @return
     */
    public static BigDecimal multipy(BigDecimal args1, BigDecimal args2, int scale) {
        return args1.multiply(args2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法,四舍五入保留指定小数位数
     *
     * @param args1
     * @param args2
     * @param scale
     * @return
     */
    public static BigDecimal add(BigDecimal args1, BigDecimal args2, int scale) {
        if (args1 == null && args2 != null) {
            return args2.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }

        if (args2 == null && args1 != null) {
            return args1.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }

        if (args1 == null && args2 == null) {
            return new BigDecimal("0.00");
        }

        return args1.add(args2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法,四舍五入保留2位小数
     *
     * @param args1
     * @param args2
     * @return
     */
    public static BigDecimal add(BigDecimal args1, BigDecimal args2) {
        return add(args1, args2, SCALE);
    }

    /**
     * 比较两个数字是否一样大，精度为小数点后2位，四舍五入
     *
     * @param args1
     * @param args2
     * @return
     */
    public static boolean equals(BigDecimal args1, BigDecimal args2) {
        if (args1 == null && args2 == null) {
            return true;
        }
        if ((args1 == null && args2 != null) || (args1 != null && args2 == null)) {
            return false;
        }
        return args1.setScale(2, BigDecimal.ROUND_HALF_UP).equals(args2.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 计算总价（保留两位小数，四舍五入）
     *
     * @param price 单价
     * @param count 数量
     * @return
     */
    public static BigDecimal getTotalPrice(BigDecimal price, BigDecimal count) {
        BigDecimal zero = new BigDecimal("0.00");
        if (zero.compareTo(count) == 0) {
            return zero;
        } else {
            return price.multiply(count).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * 两个Integer相除，得到两位小数精度的数字,结果四舍五入
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public static BigDecimal divide(Integer dividend, Integer divisor) {
        return new BigDecimal(dividend).divide(new BigDecimal(divisor), SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法 args1 - args2 保留scale位
     *
     * @param args1
     * @param args2
     * @param scale
     * @return
     */
    public static BigDecimal subtract(BigDecimal args1, BigDecimal args2, int scale) {
        return args1.subtract(args2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal subtract(BigDecimal args1, BigDecimal args2) {
        return args1.subtract(args2).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两个BigDecimal对象相除，默认保留2位小数
     *
     * @param args1
     * @param args2
     * @return
     */
    public static BigDecimal divide(BigDecimal args1, BigDecimal args2) {
        return divide(args1, args2, SCALE);
    }

    /**
     * 两个BigDecimal对象相除,保留scale位
     *
     * @param args1
     * @param args2
     * @param scale
     * @return
     */
    public static BigDecimal divide(BigDecimal args1, BigDecimal args2, int scale) {
        return args1.divide(args2, scale, BigDecimal.ROUND_HALF_UP);
    }

}
