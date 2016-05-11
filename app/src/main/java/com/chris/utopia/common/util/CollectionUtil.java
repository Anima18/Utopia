package com.chris.utopia.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("rawtypes")
public class CollectionUtil {
	 public static boolean isEmpty(Collection c){
		 if(c != null){
			 if(c.size() > 0){
				 return false;
			 }else{
				 return true;
			 }
		 }else{
			 return true;
		 }
	 }
	 
	 public static boolean isNotEmpty(Collection c){
		 return !isEmpty(c);
	 }
	 
	 public static boolean equalslength(String[]... stringArray){
		 if(stringArray != null){
			 for(String[] s : stringArray){
				 if(s == null){
					 return false;
				 }
			 }
			 for(int i = 0;i < stringArray.length-1; i++){
				 if(stringArray[i].length != stringArray[i+1].length){
					 return false;
				 }
			 }
			 return true;
		 }else{
			 return false;
		 }		 
	 }
	 
	public static List<String> stringToList(String str,String pattern){
		if(StringUtil.isEmpty(str)) 
			return null;
		String[] strArray = str.split(pattern);
		return Arrays.asList(strArray);
	}
	
	public static boolean isElement(String e,Collection<String> c){
		if(c.contains(e))
			return true;
		else
			return false;
	}
}
