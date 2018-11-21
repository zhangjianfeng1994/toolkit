package com.zjf.toolkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/** 
* 32位流水号 (4(业务编码)+2(节点ID)+17(时间戳)+9(重复累加)) 
* @author zjf
*/ 

public class MyIdWorker{ 
	
	// 毫秒内自增位
	private final static long sequenceBits = 9L;

	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;

	private long sequence = 0L;
	
	//节点ID
	private final String nodeId;
	
	//业务标识
	public  final String service;
	
	public MyIdWorker(String service,String nodeId) {
		if (StringUtils.isBlank(nodeId)) {
			throw new NullPointerException("nodeId is null");
		}
		if (StringUtils.isBlank(service)) {
			throw new NullPointerException("service is null");
		}
		this.nodeId = nodeId;
		this.service = service;
	}

	public synchronized String nextId() {
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
		relNum = "000000000"+sequence;
		relNum = relNum.substring(relNum.length()-9);
		String nextId = service+nodeId+time+relNum;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}
	
} 