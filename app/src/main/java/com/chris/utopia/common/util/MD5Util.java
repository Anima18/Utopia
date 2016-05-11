package com.chris.utopia.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Chris on 2015/9/25.
 */
public class MD5Util {
  public static String getMD5String(String str) {  
      MessageDigest messageDigest = null;  

      try {  
          messageDigest = MessageDigest.getInstance("MD5");  

          messageDigest.reset();  

          messageDigest.update(str.getBytes("UTF-8"));  
      } catch (NoSuchAlgorithmException e) {  
          System.out.println("NoSuchAlgorithmException caught!");  
          System.exit(-1);  
      } catch (UnsupportedEncodingException e) {  
          e.printStackTrace();  
      }  

      byte[] byteArray = messageDigest.digest();  

      StringBuffer md5StrBuff = new StringBuffer();  

      for (int i = 0; i < byteArray.length; i++) {              
          if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
              md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
          else  
              md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
      }  

      return md5StrBuff.toString();  
  }  
}
