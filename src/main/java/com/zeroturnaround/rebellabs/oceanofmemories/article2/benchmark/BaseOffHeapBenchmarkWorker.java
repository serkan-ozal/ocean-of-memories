package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

import org.apache.log4j.Logger;

public abstract class BaseOffHeapBenchmarkWorker implements OffHeapBenchmarkWorker {

	protected final Logger logger = Logger.getLogger(getClass());
	
	protected int elementCount; 
	protected String type;
	
	public BaseOffHeapBenchmarkWorker(int elementCount) {
		this.elementCount = elementCount;
	}
	
	public BaseOffHeapBenchmarkWorker(int elementCount, String type) {
		this.elementCount = elementCount;
		this.type = type;
	}
	
	@Override
	public int getElementCount() {
		return elementCount;
	};
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void flush() {
		
	}
	
	@Override
	public void finish() {
		
	}

}
