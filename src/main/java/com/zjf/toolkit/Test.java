package com.zjf.toolkit;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.DateUtils;

public class Test {
	public static void main(String[] args) throws ParseException {
		
		Test test = new Test();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = format.format(new Date());
		System.out.println(time);
		//test.mapTest();
		//test.testException();
		//System.out.println("main 方法");
		// 测试从图片文件转换为Base64编码
       // System.out.println(GetImageStr("D:\\home\\sltf\\aaa.jpg"));
		//test.subString();
	}
	
	//date时间设置
	public void setDate() throws ParseException {
		Date ss =  DateUtils.parseDate("1970-01-01 15:44:18","yyyy-MM-dd HH:mm:ss");
		Date ss1 =  DateUtils.parseDate("1970-01-01 17:44:18","yyyy-MM-dd HH:mm:ss");
		//System.out.println(ss);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(ss);
		Calendar calendarStart1 = Calendar.getInstance();
		calendarStart1.setTime(ss1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendarStart.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendarStart.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendarStart.get(Calendar.SECOND));
		Date sDate = calendar.getTime();
		System.out.println(sDate);
		calendar.set(Calendar.HOUR_OF_DAY, calendarStart1.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendarStart1.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendarStart1.get(Calendar.SECOND));
		Date sDate1 = calendar.getTime();
		System.out.println(sDate1);
		
		System.out.println(calendar.get(Calendar.YEAR));
		//默认从0-11
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.DATE));

		System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
		//12小时制
		//System.out.println(calendar.get(Calendar.HOUR));
		System.out.println(calendar.get(Calendar.MINUTE));
		System.out.println(calendar.get(Calendar.SECOND));
	}
	
	//异常测试
	public void testException() {
		String filename = "d:\\test.txt";
		try {
			FileReader reader = new FileReader(filename);
			Scanner in = new Scanner(reader);
			String input = in.next();
			int value = Integer.parseInt(input);
			System.out.println(value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		System.out.println("this is test2 block!");

	}
	
	
    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        Decoder decoder = Base64.getDecoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }

            // 生成jpeg图片
            File file = new File(imgFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void subString() {
		String string = "20180501" ;
		String strSub = string.substring(4, 6);
		System.out.println(strSub);
	}
    
    public void mapTest() {
  		Map<String, Object> map = new HashMap<>();
  		map.put("mapFlags", "1,2".split(","));
  		String[] mapFlags = (String[]) map.get("mapFlags");
  		StringBuffer str5 = new StringBuffer();
  		for (String s : mapFlags) {
  		    str5.append(s);
  		}
  		List<Map<String, Object>> list = new ArrayList<>();
  		map.put("transferList", list);
  		List<Map<String, Object>> transferList = (List<Map<String, Object>>) map.get("transferList");
  		String userid = String.valueOf("");
  		System.out.println(userid);
  	}
}
