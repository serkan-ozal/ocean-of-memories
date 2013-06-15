package com.zeroturnaround.rebellabs.oceanofmemories.article0.gc;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.MemoryUtil;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.Util;

public class GarbageCollectionParameterDemo {

	/**
	 * For GC Tuning arguments,
	 * 
	 * @link http://www.oracle.com/technetwork/java/javase/gc-tuning-6-140523.html
	 * @link http://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html
	 */
	public static void main(String[] args) {
		String[][] allGCArguments = 
				new String[][] {
					{                           "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps"},
					{"-XX:+UseSerialGC",        "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps"},
					{"-XX:+UseParallelGC",      "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps"},
					{"-XX:+UseConcMarkSweepGC", "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps"},
					{"-XX:+UseG1GC",            "-XX:+PrintGCDetails", "-XX:+PrintGCTimeStamps"}
				};
		
		for (String[] gcArguments : allGCArguments) {
			Util.runClass(GarbageCollectionTester.class, gcArguments, null);
		}
	}
	
	static class GarbageCollectionTester {

		public static void main(String[] args) {
			final int INTEGER_COUNT = 1000000;
			final Integer[] integerArray = new Integer[INTEGER_COUNT];
			
			MemoryUtil.runGC();

			System.out.println("Initial Memory Usage: ");
			MemoryUtil.printMemoryUsage();
			
			for (int i = 0; i < INTEGER_COUNT; i++) {
				integerArray[i] = new Integer(i);
			}
			
			Util.waitFor(5000);
			
			System.out.println("Memory Usage Before GC: ");
			MemoryUtil.printMemoryUsage();

			MemoryUtil.runGC();
			
			Util.waitFor(5000);

			System.out.println("Memory Usage After GC: ");
			MemoryUtil.printMemoryUsage();
		}
		
	}

}
