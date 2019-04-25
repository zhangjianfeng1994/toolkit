package com.zjf.toolkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public final class JsonKit {

	public static void main(String[] args) {
		Gson gson = new Gson();
		String aa = "{\"passportName\":\"测试\",\"manageDept\":3,\"forbidInHoliday\":null,\"forbidOutHoliday\":null}";
		Map<String, Object> map = new Gson().fromJson(aa,Map.class);
		
		System.out.println(gson.toJson(map));
	}

	private JsonKit() {}

	// json转object
	@SuppressWarnings("unchecked")
	public static Object jsonToObject(String json) {
		if (json.startsWith("[")) {
			@SuppressWarnings("rawtypes")
			List list = JSON.parseArray(json);
			List<Object> result = new LinkedList<>();
			for (Object item : list) {
				if (item != null && item.toString().startsWith("{")) {
					result.add(jsonToObject(item.toString()));
				} else {
					result.add(item);
				}
			}
			return result;
		} else if (json.startsWith("{")) {
			Map<String, Object> result = JSON.parseObject(json, LinkedHashMap.class);
			for (String key : result.keySet()) {
				Object value = result.get(key);
				if (value != null && value.toString().startsWith("{")) {
					result.put(key, jsonToObject(value.toString()));
				}
			}
			return result;
		}
		return null;
	}

}