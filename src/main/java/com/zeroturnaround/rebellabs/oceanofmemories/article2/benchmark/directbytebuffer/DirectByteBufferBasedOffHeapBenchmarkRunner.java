package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.directbytebuffer;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkRunner;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;

public class DirectByteBufferBasedOffHeapBenchmarkRunner extends BaseOffHeapBenchmarkRunner {

	public DirectByteBufferBasedOffHeapBenchmarkRunner(long elementCount) {
		super(elementCount);
	}
	
	@Override
	public OffHeapBenchmarkTrade createElement() {
		return null;
	}
	
	@Override
	public void saveElement(OffHeapBenchmarkTrade element) {
		
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(long elementOrder) {
		return null;
	}

}
