package com.zjf.toolkit;

/**
 * 验证码
 */
public final class VcodeKit {

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			System.out.println(newVcode(1, "x-xxx/xxx*xxx44*xxx"));
		}
	}

	private VcodeKit() {}

	private static final String PLACEHOLDER = "x";

	private static final String NUMBER = "0123456789";
	private static final String LETTER = "abcdefghijklmnopqrstuvwxyz";
	private static final String MIXTURE = "abcdefghijklmnopqrstuvwxyz0123456789";

	/** [生成验证码, type:1.数字,2.字母,3.混合, partten:用'x'来占位,其他字符原样输出] */
	public static String newVcode(int type, String pattern) {
		if (pattern == null || pattern.isEmpty()) {
			return null;
		}
		String character = null;
		if (type == 1) {
			character = NUMBER;
		} else if (type == 2) {
			character = LETTER;
		} else if (type == 3) {
			character = MIXTURE;
		} else {
			return null;
		}
		char[] chars = pattern.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			String charStr = String.valueOf(chars[i]);
			if (PLACEHOLDER.equalsIgnoreCase(charStr)) {
				String randomStr = String.valueOf(character.charAt((int) (Math.random() * character.length())));
				while (i == 0 && "0".equalsIgnoreCase(randomStr)) {
					randomStr = String.valueOf(character.charAt((int) (Math.random() * character.length())));
				}
				sb.append(randomStr);
			} else {
				sb.append(charStr);
			}
		}
		return sb.toString();
	}

	public static String newSmsVcode(int length) {
		return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, length - 1)));
	}

}