package com.zeroturnaround.rebellabs.oceanofmemories.common.domain.model;

public interface Mergeable<T> {
	
	Mergeable<T> merge(T objToMerge);
	
}
