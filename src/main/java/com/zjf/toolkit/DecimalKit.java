package com.zjf.toolkit;

import java.math.BigDecimal;

public final class DecimalKit {

	private DecimalKit() {}

	/** [舍位模式][向远离0的方向移动][1.1->2 1.5->2 1.8->2 -1.1->-2 -1.5->-2 -1.8->-2] */
	public final static int ROUND_UP = 0;
	/** [舍位模式][向靠近0的方向移动(直接删除多余的小数位)][1.1->1 1.5->1 1.8->1 -1.1->-1 -1.5->-1 -1.8>-1] */
	public final static int ROUND_DOWN = 1;
	/** [舍位模式][向正无穷方向移动][1.1->2 1.5->2 1.8->2 -1.1->-1 -1.5->-1 -1.8->-1] */
	public final static int ROUND_CEILING = 2;
	/** [舍位模式][向负无穷方向移动][1.1->1 1.5->1 1.8->1 -1.1->-2 -1.5->-2 -1.8->-2] */
	public final static int ROUND_FLOOR = 3;
	/** [舍位模式][四舍五入] */
	public final static int ROUND_HALF_UP = 4;
	/** [舍位模式][以5为分界线，五舍六入][1.5->1 1.6->1 -1.5->-1 -1.6->-2 1.15->1.1 1.16->1.2 1.55->1.6 1.56->1.6] */
	public final static int ROUND_HALF_DOWN = 5;
	/** [舍位模式][以5为分界线，如果是5，则前一位变偶数][1.15->1.2 1.16->1.2 1.25->1.2 1.26->1.3] */
	public final static int ROUND_HALF_EVEN = 6;
	/** [舍位模式][无需舍位(最好不要用, 一般也没用)] */
	public final static int ROUND_UNNECESSARY = 7;

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal("1.123456789");
		System.out.println(DecimalKit.format(a, 9, DecimalKit.ROUND_UNNECESSARY));
		System.out.println(DecimalKit.formatToDown(a, 5));
		System.out.println(DecimalKit.formatToHalfUp(a, 3));
		System.out.println(DecimalKit.formatToHalfUp(a, 4));
		System.out.println(DecimalKit.formatToCeiling(a, 3));
		System.out.println(DecimalKit.formatToCeiling(a, 4));
	}

	/** [格式化BigDecimal(值, 精度(小数点后几位), 精确方式(见DecimalKit.ROUND_*))] */
	public static BigDecimal format(BigDecimal value, int scale, int roundingMode) {
		return new BigDecimal(value.toString()).setScale(scale, roundingMode);
	}

	/** [格式化BigDecimal, 直接截取到小数点后scale位] */
	public static BigDecimal formatToDown(BigDecimal value, int scale) {
		return format(value, scale, DecimalKit.ROUND_DOWN);
	}

	/** [格式化BigDecimal, 四舍五入到小数点后scale位] */
	public static BigDecimal formatToHalfUp(BigDecimal value, int scale) {
		return format(value, scale, DecimalKit.ROUND_HALF_UP);
	}

	/** [格式化BigDecimal, 直接进位到小数点后scale位] */
	public static BigDecimal formatToCeiling(BigDecimal value, int scale) {
		return format(value, scale, DecimalKit.ROUND_CEILING);
	}
}
