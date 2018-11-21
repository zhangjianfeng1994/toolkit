package com.zjf.toolkit;

import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MD5Kit {

	public static void main(String[] args) {
		System.out.println(MD5Kit.getMD5("胡诗瑞"));
	}

	private MD5Kit() {}

	// 用于MD5计算
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static MessageDigest MD = null;

	static {
		try {
			MD = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			log.error(null, e);
		}
	}

	public static String getMD5(String string) {
		return getMD5(string.getBytes());
	}

	public static String getMD5(byte[] bytes) {
		MD.update(bytes);
		return bufferToHex(MD.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
			char c1 = hexDigits[bytes[l] & 0xf];
			stringbuffer.append(c0);
			stringbuffer.append(c1);
		}
		return stringbuffer.toString();
	}

}