package com.np.crm.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 14-10-16.
 */
public class NumberUtil {

    public static String formatStr1 = "#,##0.00";
    public static String formatStr2 = "###0.00";
    public static String formatStr3 = "###0.00000000";


    /**
     * 涉及小数位的保留小数位
     */
    public static final int DECIMAL_DOTLENG2 = 2;
    /**
     * 涉及金额保留小数位
     */
    public static final int DECIMAL_AMOUNT_DOTLENG4 = 4;

    /**
     * 保留 len 位小数
     *
     * @param bigDecimal
     * @param len
     * @return
     */
    public static BigDecimal getScale(BigDecimal bigDecimal, int len) {
        if (bigDecimal != null) {
            return bigDecimal.setScale(len, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    /**
     * 显示保留四位的金额
     *
     * @param amount
     * @return
     */
    public static BigDecimal getResponseAmountScale(BigDecimal amount) {
        return getScale(amount, DECIMAL_AMOUNT_DOTLENG4);
    }

    /**
     * 显示保留2位的金额
     *
     * @param amount
     * @return
     */
    public static BigDecimal getResponseAmountScale2M(BigDecimal amount) {
        return getScale(amount, DECIMAL_DOTLENG2);
    }

    /**
     * 显示保留两位小数
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal getResponseScale(BigDecimal bigDecimal) {
        return getScale(bigDecimal, DECIMAL_DOTLENG2);
    }


    public static String formatDouble(Double d) throws Exception {
        DecimalFormat decimalFormat = new DecimalFormat(formatStr3);//格式化设置
        return decimalFormat.format(d);
    }
    
    /**
	 * 转换成int
	 * @param s
	 * @param i
	 * @return
	 * @author zhiya.chai
	 */
	public static int parseInt(Object s, int i) {
		try {
			return Integer.parseInt(s.toString());
		} catch (Exception e) {
			return i;
		}
	}

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("123123.1299999");
        System.out.println("11----------->>" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("22----------->>" + bigDecimal.setScale(2, BigDecimal.ROUND_DOWN));
        System.out.println("33----------->>" + bigDecimal.setScale(2, BigDecimal.ROUND_FLOOR));
        System.out.println("44----------->>" + bigDecimal.setScale(2, BigDecimal.ROUND_UP));

    }

}
