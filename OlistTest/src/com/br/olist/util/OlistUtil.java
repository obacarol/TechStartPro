package com.br.olist.util;

public class OlistUtil {

	public static Double convertStringToDouble(String value) {
		
		try {
			return Double.valueOf(value);
		}catch (Exception e) {
			return -1d;
		}
	}
	
	public static Integer convertStringToInteger(String value) {
		
		try {
			return Integer.valueOf(value);
		}catch (Exception e) {
			return -1;
		}
	}
		
}
