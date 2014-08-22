package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

public abstract class BaseOffHeapBenchmarkRunner implements OffHeapBenchmarkRunner {

	protected long elementCount; 
	
	public BaseOffHeapBenchmarkRunner(long elementCount) {
		this.elementCount = elementCount;
	}
	
	public long getElementCount() {
		return elementCount;
	};

}
