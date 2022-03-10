package com.jointthinker.framework.common.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	  public static String md5(String str) {
	        String reStr = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(str.getBytes());
	            byte ss[] = md.digest();
	            reStr = bytes2String(ss);
	        } catch (NoSuchAlgorithmException e) {

	        }
	        return reStr;

	    }
	  
	  private static String bytes2String(byte[] aa) {
	        String hash = "";

	        for (int i = 0; i < aa.length; i++) {
	            int temp;
	            if (aa[i] < 0)
	                temp = 256 + aa[i];
	            else
	                temp = aa[i];
	            if (temp < 16)
	                hash += "0";
	            hash += Integer.toString(temp, 16);
	        }
	        hash = hash.toUpperCase();
	        //hash = hash.toLowerCase();

	        return hash;
	    }
}
