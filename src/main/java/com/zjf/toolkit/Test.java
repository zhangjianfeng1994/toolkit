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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import net.sf.json.JSONObject;


public class Test {
	public static void main(String[] args) throws ParseException {
		
		Test test = new Test();
		//test.bitwiseOperatorsTest();
		//test.mapTest();
		//test.testException();
		//System.out.println("main 方法");
		// 测试从图片文件转换为Base64编码
       // System.out.println(GetImageStr("D:\\home\\sltf\\aaa.jpg"));
		//test.subString();
		Map<String, Object> reqTools = new HashMap<>();
		reqTools.put("aa", "ss");
		//reqTools.put("bb", null);
		JSONObject jsonObject = new JSONObject();
    	jsonObject.put("key", "null");
        jsonObject.put("key2", "notNull");
        System.out.println(jsonObject.get("key").getClass());
        String userid = String.valueOf(jsonObject.get("key"));
        System.out.println(!userid.equals("null"));
	    
        
        List<String> transBillList = new ArrayList<>();
        transBillList.add("1");
        transBillList.add("2");
        transBillList.add("3");
        transBillList.add("4");
        transBillList.add("5");
        String [] custProductUserContactInfoIdArr = new String[transBillList.size()] ;
        for (int i = 0; i < transBillList.size(); i++) {
        	custProductUserContactInfoIdArr[i] = transBillList.get(i);
        }
        for (int i = 0; i < custProductUserContactInfoIdArr.length; i++) {
			
        	System.out.println(custProductUserContactInfoIdArr[i]);
		}
		
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
    @org.junit.Test
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
  	
  		String aa = (String)map.get("11");
  		String bb = String.valueOf(map.get("11"));
  		System.out.println(String.class.isInstance(aa));
  		System.out.println(Object.class.isInstance(aa));
  		System.out.println(bb);
  		System.out.println(String.class.isInstance(bb));
  		
  	}
    //位运算符测试
    @org.junit.Test
    public void bitwiseOperatorsTest() {
    	long sequence = 128L;
    	long sequenceMask = 2049;
    	sequence = sequence  & sequenceMask;
    	System.out.println(sequence);
    }
    @org.junit.Test
    public void documentTest() {
		String originDataVar = "<?xml version=\"1.0\" encoding=\"GBK\"?><Agw><Head direction=\"request\"><requesterId>1234</requesterId><transCode>DSEC0001</transCode><reqSeriaNo>20000000032017092000000002</reqSeriaNo><platformCode>123</platformCode></Head><Body></Body></Agw>";
		Document document;
		try {
			document = DocumentHelper.parseText(originDataVar);
			System.out.println("自初始==="+document.asXML());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
    
    @org.junit.Test
    public void testDate() {
    	//取当前日期：
    	LocalDate today = LocalDate.now(); // -> 2014-12-24
    	DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd");
		String nowYearMonth =  today.format(fmt);
		System.out.println(nowYearMonth);
    	// 取本月第1天：
    	LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2017-03-01
    	System.out.println(firstDayOfThisMonth);
    	// 取本月第2天：
    	LocalDate secondDayOfThisMonth = today.withDayOfMonth(2); // 2017-03-02
    	// 取本月最后一天，再也不用计算是28，29，30还是31：
    	LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth()); // 2017-12-31
    	System.out.println(lastDayOfThisMonth);
    	// 取下一天：
    	LocalDate firstDayOf2015 = today.plusDays(1); // 变成了2018-01-01
    	System.out.println(firstDayOf2015);
    	// 取2017年1月第一个周一，用Calendar要死掉很多脑细胞：
    	LocalDate firstMondayOf2015 = LocalDate.parse("2017-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2017-01-02
    }
    
    @org.junit.Test
    public void testbigdecal() {
		BigDecimal bigss=BigDecimal.ZERO;
		List<BigDecimal> list=new ArrayList<BigDecimal>();
		BigDecimal transAmt = new BigDecimal("100");
		bigss=bigss.add(transAmt);
		System.out.println("总金额是：-----"+bigss);
    }
    @org.junit.Test
    public void testsqlValidate() {
    	String str = "";
    	str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                System.out.println(true);
            }
        }
        String str1 = "abcdefghi";
        boolean status = str1.contains("def");
        if (status) {
        	System.out.println("包含");
        }else{
            System.out.println("不包含");
        }
        System.out.println(false);
    }
    
    
}
