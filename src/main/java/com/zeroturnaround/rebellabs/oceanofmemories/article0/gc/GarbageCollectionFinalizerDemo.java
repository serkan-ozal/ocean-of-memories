package com.zeroturnaround.rebellabs.oceanofmemories.article0.gc;

import com.zeroturnaround.rebellabs.oceanofmemories.common.util.MemoryUtil;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.Util;

public class GarbageCollectionFinalizerDemo {

	public static void main(String[] args) {
		final int SAMPLE_OBJECT_COUNT = 1000000;
		final SampleClass[] sampleObjectArray = new SampleClass[SAMPLE_OBJECT_COUNT];

		System.out.println("Just Before Creation: ");
		printNumberOfInstances();
		
		for (int i = 0; i < SAMPLE_OBJECT_COUNT; i++) {
			sampleObjectArray[i] = new SampleClass();
		}
		
		System.out.println("Just After Creation: ");
		printNumberOfInstances();
		
		MemoryUtil.runGC();
		
		System.out.println("Just After GC: ");
		printNumberOfInstances();
		
		for (int i = 1; i <= 10; i++) {
			Util.waitFor(i * 100);
			System.out.println((i * 100) + " milliseconds After GC: ");
			printNumberOfInstances();
		}	
	}
	
	private static void printNumberOfInstances() {
		System.out.println("\tNumber Of Created Instances: " + SampleClass.numberOfCreatedInstances);
		System.out.println("\tNumber Of Deleted Instances: " + SampleClass.numberOfDeletedInstances);
		System.out.println();
	}

	static class SampleClass {
		
		static long numberOfCreatedInstances = 0;
		static long numberOfDeletedInstances = 0; 
		
		SampleClass() {
			numberOfCreatedInstances++;
		}
		
		@Override
		protected void finalize() throws Throwable {
			numberOfDeletedInstances++;
			super.finalize();
		}

	}
	
}
