package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.unsafe;

import sun.misc.Unsafe;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class UnsafeBasedOffHeapBenchmarkWorker extends BaseOffHeapBenchmarkWorker {

	public static final String TYPE = "UNSAFE BASED OFFHEAP";
	
	protected static final Unsafe unsafe = JvmUtil.getUnsafe();
	protected int index = 0;
	
	public UnsafeBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
	}
	
	@Override
	public void reset() {
		// TODO Implement
	}
	
	@Override
	public OffHeapBenchmarkTrade createElement() {
		// TODO Implement
		return null;
	}
	
	@Override
	public void saveElement(OffHeapBenchmarkTrade element) {
		if (index >= elementCount) {
			throw new IllegalArgumentException("There is no space for new element !");
		}
		// TODO Implement
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(int elementOrder) {
		if (elementOrder >= elementCount) {
			throw 
				new IllegalArgumentException(
					"Out of bounds ! " + 
					(elementCount - 1) + " is allowed at max but you requested " + elementOrder);
		}
		// TODO Implement
		return null;
	}

}
