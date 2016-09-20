package com.chris.utopia.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

/**
 * Created by Chris on 2015/9/2.
 */
public class FileUtil {

	public static boolean createFile(File file) throws IOException {
		try {
			if (file.exists()) {
				file.delete();
			}
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) {
			      return false;
				}
			}
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static boolean createDir(String destDirName) { 
		boolean flag = false;
        File dir = new File(destDirName);  
        if (!dir.exists()) {  
        	if (dir.mkdirs()) {  
            	flag = true;  
            }
        }else {
        	flag = true;
        }   
        return flag;
    }  

	public static String readTxtFile(File fileName) throws Exception {
		String result = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		return result;
	}

	public static File writeTxtFile(String content, String fileName) throws IOException {
		RandomAccessFile mm = null;
		FileOutputStream o = null;
		try {
			File file = new File(fileName);
			createFile(file);
			o = new FileOutputStream(file);
			o.write(content.getBytes("UTF-8"));
			o.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
	}

	public void writeFileByRow(String fileName, String[] arrs) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		for (String arr : arrs) {
			bw.write(arr + "\t\n");
		}

		bw.close();
		osw.close();
		fos.close();
	}
	
	public static void deleteFile(File file) {
		if(file.exists()) {
			file.delete();
		}
	}
}
