package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;

public interface OffHeapBenchmarkWorker {

	String getType();
	void reset();
	int getElementCount();
	OffHeapBenchmarkTrade createElement();
	void saveElement(OffHeapBenchmarkTrade element);
	OffHeapBenchmarkTrade getElement(int elementOrder);
	void flush();
	void finish();

}
