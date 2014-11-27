package com.waylau.compressor.util;

public class ArrayUtil {

	public ArrayUtil() {
		// TODO Auto-generated constructor stub
	}
 
	public static boolean isExist(Object[] arr,Object a){
	     for(int i=0;i<arr.length;i++){//循环遍历
	       if(a==arr[i]){//只要有一个相等，就说明有了，那跳出返回true
	         return true;
	       }
	     }
	     return false;//遍历完还没跳出，说明一个也没有，返回 false
	}

}
