package com.zeroturnaround.rebellabs.oceanofmemories.common.benchmark;

public interface BenchmarkRunner {

	int getId();
	String getName();
	String getDescription();
	void run();

}
