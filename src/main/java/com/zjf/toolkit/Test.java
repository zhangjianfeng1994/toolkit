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
import java.util.Random;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;


public class Test {
	public static void main(String[] args) {
		String refundMsg = "[洗衣桶编号:"+11111+",在当前时间:"+LocalDateTime.now()+",无订单]";
		String msg = "洗衣机指令接收,请求洗衣接口结果:拒绝洗衣,拒绝原因:"+refundMsg;
		System.out.println(msg);
		
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
    @org.junit.Test
    public void subString() {
		String string = "20180501" ;
		String strSub = string.substring(1);
		System.out.println(strSub);
		List<String> mechanismNames = new ArrayList<>();
		mechanismNames.add("学校");
		mechanismNames.add("学院");
		mechanismNames.add("专业");
		mechanismNames.add("班级");
		StringBuffer mechanismName = null;
		for (String string1 : mechanismNames) {
			if (mechanismName == null) {
				mechanismName = new StringBuffer(string1);
			}else {
				mechanismName = mechanismName.append("/").append(string1);
			}
		}
		System.out.println(mechanismName);
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
  		
  		System.out.println((String)map.get("slbusiid"));
  		
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
    	LocalDate accountTime = LocalDate.parse("2019-05-14",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	System.out.println(accountTime.toString().split("T")[0].replace("-", "."));
    	System.out.println(accountTime);
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
		LocalDateTime lastDayOfThisMonthEnd = LocalDateTime.of(firstMondayOf2015, LocalTime.MAX);//当天最晚时间
		System.out.println("当天最后时间:"+lastDayOfThisMonthEnd);
    	LocalDateTime localDateTime = LocalDateTime.parse("2019-05-14 11:22:45",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	System.out.println(localDateTime.toString().split("T")[0].replace("-", "."));
    }
    
    @org.junit.Test
    public void testbigdecal() {
		BigDecimal bigss=BigDecimal.ZERO;
		List<BigDecimal> list=new ArrayList<BigDecimal>();
		BigDecimal transAmt = new BigDecimal("100");
		bigss = bigss.add(transAmt);
		//bigss = bigss.negate();
		bigss = transAmt.subtract(bigss);
		System.out.println("总金额是：-----:"+bigss.toString());
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

	@org.junit.Test
	public void testLocalTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss");
		LocalDateTime time = LocalDateTime.now();
		String localTime = df.format(time);
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		String monthStr = String.format("%02d", month);
		System.out.println(year+"02"+monthStr);
		System.out.println(time.isAfter(null));
	}
    @org.junit.Test
    public void testForBreak() {
    	
    	for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 1) {
					break;
				}
				System.out.println("J:"+j);
			}
			System.out.println("I:"+i);
		}
    }
    @org.junit.Test
    public void testInteger() {
    	Integer i = 128;
    	Integer j = 128;
    	System.out.println(i == j);
    	System.out.println(i.intValue() == j.intValue());
    	Integer m = 127;
    	Integer n = 127;
    	System.out.println(m == n);
    	String string = "-";
    	System.out.println(NumberUtils.isNumber(string));
    }
    
    @org.junit.Test
    public void testList() {
    	List<String> list1 = new ArrayList<String>();
    	list1.add("A");
    	list1.add("B");
    	list1.add("D");
    	list1.add("E");
    	List<String> list11 = new ArrayList<String>();
    	list11.addAll(list1);
    	
    	
        List<String> list2 = new ArrayList<String>();
        list2.add("B");
        list2.add("C");
        
        list1.removeAll(list2);
        
        list11.retainAll(list2);
        
        list2.removeAll(list11);
        System.out.println("求差集List1中有的但是List2中没有的元素:"+list1);
        System.out.println("求交集:"+list11);
        System.out.println("求差集List2中有的但是List1中没有的元素:"+list2);
    }
    
    @org.junit.Test
    public void testRandom() {
        int min = 0;
        int max = 5;
        for(int i=0;i<10;i++){
            System.out.println(new Random().nextInt(max));
        }
    }
    @org.junit.Test
    public void testJson() {
    	String orderNoItems = "{\"E106201911191025150009\":\"21196615480903729155\"}";
    	Gson g = new Gson();
		JsonObject json = g.fromJson(orderNoItems,JsonObject.class);
		String[] orderNoItem = json.get("E106201911191025150009").toString().split(",");
		String qq = orderNoItem[0];
		qq = qq.replaceAll("\"", "");
		System.out.println(qq);
		System.out.println("aaa");
    }
    
}
