package com.zjf.toolkit;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Json, 用于传递数据到页面
 * @author mrathena
 * */
public final class Json {
	
	// 静态部分
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private static final int ERROR = -1;
	
	public static Json success() {
		return new Json(SUCCESS);
	}
	public static Json success(String message) {
		return new Json(SUCCESS, message);
	}
	public static Json failure() {
		return new Json(FAILURE);
	}
	public static Json failure(String message) {
		return new Json(FAILURE, message);
	}
	public static Json error() {
		return new Json(ERROR);
	}
	public static Json error(String message) {
		return new Json(ERROR, message);
	}
	
	public static String successStr() {
		return new Json(SUCCESS).toJsonString();
	}
	public static String successStr(String message) {
		return new Json(SUCCESS, message).toJsonString();
	}
	public static String failureStr() {
		return new Json(FAILURE).toJsonString();
	}
	public static String failureStr(String message) {
		return new Json(FAILURE, message).toJsonString();
	}
	public static String errorStr() {
		return new Json(ERROR).toJsonString();
	}
	public static String errorStr(String message) {
		return new Json(ERROR, message).toJsonString();
	}
	
	// 动态部分
	private int status;
	private String message;
	private Map<String, Object> data;
	
	private Json(int status) {
		this.status = status;
	}
	private Json(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public Json put(String key, Object value) {
		if (this.data == null) {
			this.data = new LinkedHashMap<>();
		}
		data.put(key, value);
		return this;
	}
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}
	// 故意去除了所有setter, 防止误用
	
	@Override
	public String toString() {
		return "JSON [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
	
	public String toJsonString() {
		return JSON.toJSONString(this, SerializerFeature.WRITE_MAP_NULL_FEATURES);
	}
	
	public static void main(String[] args) {
		Json json = Json.success("aaa").put("111", "113123").put("222", "2rsdf");
		Json json2 = Json.failure().put("json", json);
		System.out.println(json2.getMessage());
		System.out.println(JSON.toJSONString(json));
		System.out.println(JSON.toJSONString(json2));
	}
	
}