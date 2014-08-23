package com.zeroturnaround.rebellabs.oceanofmemories.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

public class ClassUtil {

	private static final Logger logger = LogUtil.getLogger();
	
	public static final String CLASS_FILE_EXTENSION = ".class";
	public static int BYTE_COUNT_IN_A_ROW_FOR_PRINT = 16; // Not final, it can be set directly
	
	private ClassUtil() {
		
	}
	
	public static String getPathOfClass(Class<?> clazz) {
		return clazz.getResource(clazz.getSimpleName() + CLASS_FILE_EXTENSION).getPath();
	}
	
	public static InputStream getInputStreamOfClass(Class<?> clazz) {
		return clazz.getResourceAsStream(clazz.getSimpleName() + CLASS_FILE_EXTENSION);
	}
	
	public static File getFileOfClass(Class<?> clazz) {
		try {
			return new File(clazz.getResource(clazz.getSimpleName() + CLASS_FILE_EXTENSION).toURI());
		}
		catch (URISyntaxException e) {
			logger.error("Error occured while getting file of class " + clazz.getName(), e);
			return null;
		}
	}
	
	public static byte[] getContentOfClass(Class<?> clazz) {
		File classFile = getFileOfClass(clazz);
		if (classFile.exists()) {
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(classFile));
				byte[] buffer = new byte[(int)classFile.length()];
				bis.read(buffer);
				return buffer;
			}
			catch (IOException e) {
				logger.error("Error occured while reading content of class " + clazz.getName(), e);
			}
			finally {
				if (bis != null) {
					try {
						bis.close();
					} 
					catch (IOException e) {
						logger.error(
								"Error occured while closing file stream of class " + 
								clazz.getName() + e);
					}
				}
			}
		}
		else {
			logger.error("Class file for path " + classFile.getAbsolutePath() + " couldn't be found");
		}
		return null;
	}
	
	public static void printContentOfClassFile(Class<?> clazz) {
		byte[] classContent = getContentOfClass(clazz);
		if (classContent != null) {
			for (int i = 0; i < classContent.length; i++) {
	    		if (i % BYTE_COUNT_IN_A_ROW_FOR_PRINT == 0) {
					System.out.print(String.format("[0x%04x]: ", i));
				}
	    		System.out.print(String.format("%02x ", classContent[i]));
				if ((i + 1) % BYTE_COUNT_IN_A_ROW_FOR_PRINT == 0) {
					System.out.println();
				}
	    	}	
			System.out.println();
		}
	}
	
	public static void printContentOfClassFileByShowingPrintableCharacters(Class<?> clazz) {
		byte[] classContent = getContentOfClass(clazz);
		if (classContent != null) {
			for (int i = 0; i < classContent.length; i++) {
	    		if (i % BYTE_COUNT_IN_A_ROW_FOR_PRINT == 0) {
					System.out.print(String.format("[0x%04x]: ", i));
				}
	    		System.out.print(String.format("%02x ", classContent[i]));
				if ((i + 1) % BYTE_COUNT_IN_A_ROW_FOR_PRINT == 0) {
					for (int j = i + 1 - BYTE_COUNT_IN_A_ROW_FOR_PRINT; j <= i; j++) {
						byte b = classContent[j];
						// If non printable character, don't print it, just print dot character
						if (Util.isPrintableChacter((char)b)) { 
							System.out.print((char)b);
						}
						else {
							System.out.print('.');
						}
					}
					System.out.println();
				}
	    	}	
			int remaining = classContent.length % BYTE_COUNT_IN_A_ROW_FOR_PRINT;
			int complementary = BYTE_COUNT_IN_A_ROW_FOR_PRINT - remaining;
			if (complementary != 0) {
				for (int i = 0; i < complementary; i++) {
					System.out.print("   ");
				}
				int start = classContent.length - remaining;
				for (int j = 0; j < remaining; j++) {
					byte b = classContent[start + j];
					// If non printable character, don't print it, just print dot character
					if (Util.isPrintableChacter((char)b)) { 
						System.out.print((char)b);
					}
					else {
						System.out.print('.');
					}
				}
			}
			System.out.println();
		}
	}
	
}
