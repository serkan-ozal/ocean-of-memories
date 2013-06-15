package com.zeroturnaround.rebellabs.oceanofmemories.article0.gc;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.MemoryUtil;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.Util;

public class GarbageCollectionAllocationDeallocationDemo {

	public static void main(String[] args) {
		MemoryUtil.runGC();Util.waitFor(5000);
		MemoryUtil.printMemoryUsage();
		
		
		final int SAMPLE_OBJECT_COUNT = 1000000;
		SampleClass[] sampleObjectArray = new SampleClass[SAMPLE_OBJECT_COUNT];
		MemoryUtil.printMemoryUsage();
		Util.waitFor(5000);
		MemoryUtil.runGC();

		System.out.println("Initial Memory Usage: ");
		MemoryUtil.printMemoryUsage();
		
		for (int i = 0; i < SAMPLE_OBJECT_COUNT; i++) {
			sampleObjectArray[i] = new SampleClass();
		}
		
		Util.waitFor(5000);

		System.out.println("Memory Usage Before GC: ");
		MemoryUtil.printMemoryUsage();
		
		sampleObjectArray = null;
		
		MemoryUtil.runGC();
		
		Util.waitFor(5000);

		System.out.println("Memory Usage After GC: ");
		MemoryUtil.printMemoryUsage();
		
		/**
		 * For object memory layout in Java,
		 * 
		 * @see http://hg.openjdk.java.net/jdk7/hotspot/hotspot/file/9b0ca45cd756/src/share/vm/oops/oop.hpp
		 */
		SampleClass sampleObject = new SampleClass();
		MemoryUtil.dumpFromObject(sampleObject, 32);
	}

	static class SampleClass {

		byte  b = 2;   //0x00000002
		short s = 5;   //0x00000005
		int   i = 10;  //0x0000000A
		long  l = 100; //0x0000000000000064

	}
	
}
