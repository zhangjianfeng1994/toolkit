package com.zjf.toolkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ReflectionKit {

	public static void main(String[] args) {
		class User {
			private String name;

			public User(String name) {
				this.name = name;
			}

			@Override
			public String toString() {
				return "User [name=" + name + "]";
			}
		}
		User user = new User("mrathena");
		System.out.println(user);
		System.out.println(ReflectionKit.getValue(user, "name"));
		ReflectionKit.setValue(user, "name", "sflksjdfl");
		System.out.println(user);
		System.out.println(ReflectionKit.getValue(user, "name"));
	}

	private ReflectionKit() {}

	/**
	 * 通过反射取对象指定字段(属性)的值
	 *
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段的名字
	 * @throws 如果取不到对象指定字段的值则抛出异常
	 * @return 字段的值
	 */
	public static Object getValue(Object target, String fieldName) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");

		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				target = f.get(target);
				clazz = target.getClass();
			}

			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			return f.get(target);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过反射给对象的指定字段赋值
	 *
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段的名称
	 * @param value
	 *            值
	 */
	public static void setValue(Object target, String fieldName, Object value) {
		Class<?> clazz = target.getClass();
		String[] fs = fieldName.split("\\.");
		try {
			for (int i = 0; i < fs.length - 1; i++) {
				Field f = clazz.getDeclaredField(fs[i]);
				f.setAccessible(true);
				Object val = f.get(target);
				if (val == null) {
					Constructor<?> c = f.getType().getDeclaredConstructor();
					c.setAccessible(true);
					val = c.newInstance();
					f.set(target, val);
				}
				target = val;
				clazz = target.getClass();
			}

			Field f = clazz.getDeclaredField(fs[fs.length - 1]);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}