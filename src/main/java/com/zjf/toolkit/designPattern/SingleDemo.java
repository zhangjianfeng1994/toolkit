package com.zjf.toolkit.designPattern;

public class SingleDemo {

	// 私有构造器
	private SingleDemo() {
	}

	// 定义一个静态枚举类
	static enum SingletonEnum {
		// 创建一个枚举对象，该对象天生为单例
		INSTANCE;
		private SingleDemo singleDemo;

		// 私有化枚举的构造函数
		private SingletonEnum() {
			singleDemo = new SingleDemo();
		}
		public SingleDemo getInstnce() {
			return singleDemo;
		}
	}
	
	//对外暴露一个获取SingleDemo对象的静态方法
    public static SingleDemo getInstance(){
        return SingletonEnum.INSTANCE.getInstnce();
    }
    
    public static void main(String[] args) {
        System.out.println(SingleDemo.getInstance()==SingleDemo.getInstance());
	}
}
