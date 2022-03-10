package com.jointthinker.framework.common.util;

import java.text.DecimalFormat;

public class DoubleUtil {
	public static String to2BitsString(Double d){
		if(d==null){
			return "0.0";
		}else{
			DecimalFormat nf = new DecimalFormat("####.##");
			return nf.format(d);
		}
	}
	
	public static String to4BitsString(Double d){
		if(d==null){
			return "0.0";
		}else{
			DecimalFormat nf = new DecimalFormat("####.####");
			return nf.format(d);
		}
	}
	
	public static double getDoubleValue(Double d){
		if(d==null){
			return 0;
		}else{
			return d.doubleValue();
		}
	}
	
	public static double getDoubleValue(String s){
		if(s==null || s.equals("")){
			return 0;
		}else{
			return getDoubleValue(new Double(s));
		}
	}
	
	public static String to2BitsString(double d){
		return to2BitsString(new Double(d));
	}
	
	public static String subtract(Double d1, Double d2){
		double dd1 = d1==null?0:d1.doubleValue();
		double dd2 = d2==null?0:d2.doubleValue();
		return to2BitsString(new Double(dd1-dd2));
	}
}
