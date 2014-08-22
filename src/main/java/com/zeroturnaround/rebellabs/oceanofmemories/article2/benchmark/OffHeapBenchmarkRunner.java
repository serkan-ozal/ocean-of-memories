package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;

public interface OffHeapBenchmarkRunner {

	long getElementCount();
	OffHeapBenchmarkTrade createElement();
	void saveElement(OffHeapBenchmarkTrade element);
	OffHeapBenchmarkTrade getElement(long elementOrder);

}
