package com.bcareapp.constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetEnvironmentFactoryConstant {
	public static Object environmentInstance;
	private static Class<?> c1 = null;
	private static Method m1 = null;

	public static Object getEnvironmentInstance() {
		try {
			c1 = Class.forName("com.bcareapp.config.EnvironmentFactory");
			m1 = c1.getMethod("getInstance");
			environmentInstance = m1.invoke(c1);
			return environmentInstance;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return environmentInstance;
	}

}
