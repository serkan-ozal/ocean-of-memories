package com.zeroturnaround.rebellabs.oceanofmemories.common.util;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class MemoryUtil {

	public static final long BYTE_COUNT_IN_KB = 1024;
	public static final long BYTE_COUNT_IN_MB = 1024 * BYTE_COUNT_IN_KB;
	public static final long KB_COUNT_IN_MB = 1024;
	
	public static final int NR_BITS = Integer.valueOf(System.getProperty("sun.arch.data.model"));
	public static final int BYTE = 8;
	public static final int WORD = NR_BITS / BYTE;
	
	private static Unsafe unsafe;
	
	static {
		init();
	}
	
	private MemoryUtil() {
		
	}
	
	private static void init() {
		try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public static void info() {
        System.out.println("Unsafe: " + unsafe);
        System.out.println("\tAddressSize : " + unsafe.addressSize());
        System.out.println("\tPage Size   : " + unsafe.pageSize());
    }
	
	public static double byte2KB(long sizeAsBytes) {
		return Util.makeDouble2DigitPrecisioned((double)sizeAsBytes / BYTE_COUNT_IN_KB);
	}
	
	public static double byte2MB(long sizeAsBytes) {
		return Util.makeDouble2DigitPrecisioned((double)sizeAsBytes / BYTE_COUNT_IN_MB);
	}
	
	public static double kb2MB(long sizeAsKBs) {
		return Util.makeDouble2DigitPrecisioned((double)sizeAsKBs / KB_COUNT_IN_MB);
	}
	
	public static void runGC() {
		long start = System.nanoTime();
		for (int i = 0; i < 3; i++) {
			System.gc();
		}
		long finish = System.nanoTime();
		System.out.println("GC executed in " + ((finish - start) / 1000000) + " milliseconds ...\n");
	}
	
	public static void printMemoryUsage() {
		Runtime runtime = Runtime.getRuntime();
		System.out.println(String.format("%-10s %-10s %-10s", "MAX", "TOTAL", "FREE"));
		System.out.println("========== ========== ==========");
		System.out.println(String.format("%10d %10d %10d\n\n", runtime.maxMemory(), runtime.totalMemory(), runtime.freeMemory()));
	}
	
	public static void dumpFromObject(Object obj, long size) {
		int sizeLength = String.valueOf(size).length();
    	for (int i = 0; i < size; i++) {
    		if (i % 16 == 0) {
    			System.out.print(String.format("[%0" + sizeLength + "d]: ", i));
    		}
	    	System.out.print(String.format("%02x ", unsafe.getByte(obj, (long)i)));
			if ((i + 1) % 16 == 0) {
				System.out.println();
			}
    	}	
    	System.out.println();
    }

}
