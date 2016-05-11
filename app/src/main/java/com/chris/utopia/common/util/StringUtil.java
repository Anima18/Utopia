package com.chris.utopia.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
/**
 * Created by Chris on 2015/9/2.
 */
public class StringUtil {

	static final char DBC_CHAR_START = 33;
	static final char DBC_CHAR_END = 126;
	static final int CONVERT_STEP = 65248;
	static final char SBC_SPACE = 12288;
	static final char DBC_SPACE = ' ';

	/**
	 * check Object obj is null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(final Object obj) {
		if (null != obj && !"".equals(obj)) {
			return false;
		}
		return true;
	}

	/**
	 * check Object obj is not null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(final Object obj) {
		return !isEmpty(obj);
	}

	public static int percent(int y, int z) {
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;
		BigDecimal bg = new BigDecimal(fen);
		double value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		return (int) (value * 100);
	}

	public static String listToSqlIn(List<String> list) {
		if (CollectionUtil.isNotEmpty(list)) {
			StringBuffer sb = new StringBuffer();
			for (String str : list) {
				sb.append("'");
				sb.append(str);
				sb.append("'");
				sb.append(",");
			}
			String str = sb.toString();
			return str.substring(0, str.length() - 1);
		} else {
			return null;
		}
	}

	public static String replace(String str, String oldChar, String newChar) {
		if (isNotEmpty(str)) {
			if (newChar == null) {
				return str.replace("'" + oldChar + "'", "null");
			} else {
				return str.replace(oldChar, newChar);
			}

		}
		return null;
	}

	public static String changeSKUFormat(String sku) {
		if (sku == null || sku.length() != 14) {
			return sku;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(sku.substring(0, 2));
		sb.append("-");
		sb.append(sku.substring(2, 7));
		sb.append("-");
		sb.append(sku.substring(7, 9));
		sb.append("-");
		sb.append(sku.substring(9, 11));
		sb.append("-");
		sb.append(sku.substring(11, 14));

		return sb.toString();
	}

	public static String toDatabaseColumnName(String str) {
		if (isEmpty(str)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char c;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(str.substring(index, i));
				sb.append("_");
				index = i;
			}
		}
		if (index == 0) {
			sb.append(str);
		} else {
			sb.append(str.substring(index));
		}
		return sb.toString().toUpperCase();
	}

	public static String helfToCircle(String str) {

		StringBuffer buf = new StringBuffer();
		char[] ca = str.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == DBC_SPACE) {
				buf.append(SBC_SPACE);
			} else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) {
				buf.append((char) (ca[i] + CONVERT_STEP));
			} else {
				buf.append(ca[i]);
			}
		}

		return buf.toString();
	}

	public static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getType());
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String setUrlParam(Object o) {
		if (isEmpty(o)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field f : fields) {
			String fieldName = f.getName();
			Object fieldValue = getFieldValueByName(fieldName, o);
			if (isNotEmpty(fieldValue)) {
				sb.append("&");
				sb.append(fieldName);
				sb.append("=");
				sb.append(fieldValue.toString());
			}
		}
		return sb.toString();
	}

	public static String setUrlParam(List<String> paramList, String name) {
		if (CollectionUtil.isEmpty(paramList)) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (String param : paramList) {
			sb.append("&");
			sb.append(name);
			sb.append("=");
			sb.append(param);
		}
		return sb.toString();
	}
	
	public static String listToDbIn(List<String> paramList) {
		if (CollectionUtil.isEmpty(paramList)) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for (String param : paramList) {
			sb.append("'");
			sb.append(param);
			sb.append("',");
		}
		String str = sb.toString();
		return str.substring(1, str.length()-2);
	}
}
