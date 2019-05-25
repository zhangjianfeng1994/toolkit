package com.zjf.toolkit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

public final class JsonKit {

	public static void main(String[] args) {
		Gson gson = new Gson();
		String aa = "{\"passportName\":\"测试\",\"manageDept\":3,\"forbidInHoliday\":null,\"forbidOutHoliday\":null}";
		Map<String, Object> map = new Gson().fromJson(aa,Map.class);
		
		//System.out.println(gson.toJson(map));
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put("aa", 11);
		map11.put("bb", "111");
		map11.put("cc", "");
		System.out.println(map11.get("a") != null&& map11.get("a") != "");
		String json = gson.toJson(map11);
		Map<String,Object> reqMap = JSON.parseObject(json,Map.class); 
		String externalAccountShortErrorId = (String) reqMap.get("bb");
		System.out.println(externalAccountShortErrorId);
		System.out.println(StringUtils.isNotBlank(externalAccountShortErrorId));
		if (StringUtils.isNotBlank(externalAccountShortErrorId)) {
			Long externalAccountShortErrorIdLong = Long.parseLong(externalAccountShortErrorId);
		}		
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