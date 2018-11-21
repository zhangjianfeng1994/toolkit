package com.zjf.toolkit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ParameterUtil {

	private ParameterUtil() {}

	public static Map<String, Object> removeEmptyParameters(Map<String, String> parameters) {
		Map<String, Object> result = new HashMap<>();
		result.putAll(parameters);
		for (Iterator<String> iterator = result.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			Object object = result.get(key);
			if (object == null || object.toString().trim().length() == 0) {
				iterator.remove();
			}
		}
		return result;
	}

}