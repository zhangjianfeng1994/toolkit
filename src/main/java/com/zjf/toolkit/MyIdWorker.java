package com.zjf.toolkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/** 
* 32位流水号 (4(业务编码)+3(系统环境编码)+2(节点ID)+17(时间戳)+6(重复累加)) 
* @author zjf
*/ 
public class MyIdWorker {
		// 毫秒内自增位
		private final static long sequenceBits = 11L;

		private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

		private static long lastTimestamp = -1L;

		private static long sequence = 0L;
		
		
		/**
		 * nodeId获取配置文件中的节点id
		 */
		public static final String NODE_ID="nodeId";
		
		//节点ID
		private final static String nodeId = "01";
		
		//转账业务标识
		public  final static String service = "T304";
		/**
		 * 系统号:304转账业务子系统
		 */
		public static final int SYSTEM_TRANS = 304;
		
		/**
		 * 系统号:401移动端前台子系统
		 */
		public static final int SYSTEM_MOBILE = 401;
		

		public synchronized String nextId(int systemNumber) {
			long timestamp = timeGen();
			if (timestamp < lastTimestamp) {
				try {
					throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp)
							+ " milliseconds");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (lastTimestamp == timestamp) {
				// 当前毫秒内，则+1
				sequence = (sequence + 1) & sequenceMask;
				if (sequence == 0) {
					// 当前毫秒内计数满了，则等待下一秒
					timestamp = tilNextMillis(lastTimestamp);
				}
			} else {
				sequence = 0;
			}
			lastTimestamp = timestamp;
			// ID偏移组合生成最终的ID，并返回ID
			Date nowTime = new Date(timestamp);
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String time = format.format(nowTime);
			String relNum="";
			relNum = "000000"+sequence;
			relNum = relNum.substring(relNum.length()-6);
			String nextId = null;
			if (SYSTEM_TRANS == systemNumber) {
				nextId = service+SYSTEM_TRANS+nodeId+time+relNum;
			}else {
				nextId = service+SYSTEM_MOBILE+nodeId+time+relNum;
			}
			return nextId;
		}

		private static long tilNextMillis(final long lastTimestamp) {
			long timestamp = timeGen();
			while (timestamp <= lastTimestamp) {
				timestamp = timeGen();
			}
			return timestamp;
		}

		private static long timeGen() {
			return System.currentTimeMillis();
		}
}
