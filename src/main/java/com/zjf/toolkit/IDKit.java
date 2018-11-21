package com.zjf.toolkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public final class IDKit {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new ConcurrentHashMap<>(100000);

		Thread[] threads = new Thread[1000];

		for (int i = 0; i < 1000; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
//						map.put(generateId(), 0);
						map.put(generateSerialNumber(), 0);
					}
				}
			});
		}

		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}

		System.out.println(map.size());
	}

	private IDKit() {}

	public static String generateId() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

	public static String generateSerialNumber() {
		String a = String.valueOf(System.currentTimeMillis());
		a = a.substring(0, a.length() - 3);// 去除其毫秒位
		String b = String.valueOf(System.nanoTime());
		b = b.substring(b.length() - 9);// 留下其毫秒位和纳秒位
		String c = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
		return a + b + c;
	}

	public static String generateSerialNumber2() {
		String a = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String b = String.valueOf(System.nanoTime());
		b = b.substring(b.length() - 9);// 留下其毫秒位和纳秒位
		String c = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
		return a + b + c;
	}

}