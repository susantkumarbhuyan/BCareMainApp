package com.bcareapp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonsDataUtil {
	private static final long nanosPerMilli = 1000000;

	public static <T> List<T> iteratorToList(Iterator<T> itr) {
		List<T> list = new ArrayList<T>();

		if (itr != null) {
			while (itr.hasNext()) {
				list.add(itr.next());
			}
		}
		return list;
	}

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	/** Convert nanoseconds time to milliseconds
     * @param nanos must be nanoseconds
     * @return time value in milliseconds */
	public static long nanosToMillis(long nanos) {
		return nanos / nanosPerMilli;
	}
	
	
}
