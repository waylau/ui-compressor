package com.waylau.uicompressor.util;
/**
 * 数组工具类
 * @author waylau.com
 * @date 2014-11-27
 *
 */
public class ArrayUtil {

	public ArrayUtil() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isExist(Object[] arr, Object a) {
		if (arr == null) {
			return false;
		}
		for (int i = 0; i < arr.length; i++) {// 循环遍历
			if (a.equals(arr[i])) {// 只要有一个相等，就说明有了，那跳出返回true
				return true;
			}
		}
		return false;// 遍历完还没跳出，说明一个也没有，返回 false
	}

}
