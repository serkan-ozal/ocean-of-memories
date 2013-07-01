package com.zeroturnaround.rebellabs.oceanofmemories.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

public class Util {
	
	public static final int ASCII_CODE_OF_MIN_PRINTABLE_CHARACTER = 33;
	public static final int ASCII_CODE_OF_MAX_PRINTABLE_CHARACTER = 127;
	
	private Util() {
		
	}
	
	public static double makeDouble2DigitPrecisioned(double value) {
		return ((int)(value * 100) / (double)100);
	}
	
	public static double percantegeOf(double value1, double value2) {
		return makeDouble2DigitPrecisioned((value1 * 100) / value2);
	}

	public static boolean isPrintableChacter(char c) {
		return 
			c >= ASCII_CODE_OF_MIN_PRINTABLE_CHARACTER && 
			c <= ASCII_CODE_OF_MAX_PRINTABLE_CHARACTER;
	}
	
	public static void waitFor(long millisecods) {
		try {
			Thread.sleep(millisecods);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void runClass(Class<?> classHasMainMethod, String[] vmArguments, String[] programArguments) {
		try {
			StringBuilder sbVmArguments = new StringBuilder();
			StringBuilder sbProgramArguments = new StringBuilder();
			
			if (vmArguments != null) {
				for (String arg : vmArguments) {
					sbVmArguments.append(arg).append(" ");
				}
			}
			
			if (programArguments != null) {
				for (String arg : programArguments) {
					sbProgramArguments.append(arg).append(" ");
				}
			}
			
			String classPath = URLDecoder.decode(Util.class.getClassLoader().getResource(".").getPath());
			Process proc = 
					Runtime.getRuntime().exec(
							"java" + " -cp " + "\"" + classPath + "\"" + " " +
							sbVmArguments.toString() + " " +
							classHasMainMethod.getName() + " " +
							sbProgramArguments.toString());
	        
			BufferedReader outputReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
	        BufferedReader errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
	        String line = null;
	
	        while ((line = outputReader.readLine()) != null) {	
	        	System.out.println(line);
	        }	
	        
	        while ((line = errorReader.readLine()) != null) {	
	        	System.out.println(line);
	        }
        
	        proc.waitFor();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
