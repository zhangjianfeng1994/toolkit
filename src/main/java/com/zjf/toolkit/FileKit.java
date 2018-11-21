package com.zjf.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class FileKit {

	public static void main(String[] args) {
		String source = "D:/develop/_resource/jdk-8u144-windows-x64.exe";
		String target = "D:/develop/_resource/jdk.exe";
		long start = System.currentTimeMillis();
		FileKit.fileCopyNIO(source, target);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	private FileKit() {}

	/** 循环删除目录/文件 (谨慎使用) */
	public static void delete(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}
		File[] files = file.listFiles();
		for (File file2 : files) {
			delete(file2);
		}
		file.delete();
	}

	/**
	 * 文件拷贝
	 */
	public static boolean fileCopy(String source, String target) {
		try (InputStream in = new FileInputStream(source); OutputStream out = new FileOutputStream(target);) {
			byte[] buffer = new byte[4096];
			int bytesToRead;
			while ((bytesToRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesToRead);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 文件拷贝
	 */
	public static boolean fileCopyNIO(String source, String target) {
		try (FileInputStream in = new FileInputStream(source); FileOutputStream out = new FileOutputStream(target);) {
			FileChannel inChannel = in.getChannel();
			FileChannel outChannel = out.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			while (inChannel.read(buffer) != -1) {
				buffer.flip();
				outChannel.write(buffer);
				buffer.clear();
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

}
