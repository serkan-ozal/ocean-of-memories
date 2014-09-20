package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.proxyonunsafe;

import sun.misc.Unsafe;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class ProxyOnUnsafeBasedOffHeapBenchmarkWorker extends BaseOffHeapBenchmarkWorker {

	public static final String TYPE = "PROXY ON UNSAFE BASED OFFHEAP";
	
	protected static final Unsafe unsafe = JvmUtil.getUnsafe();
	protected int index;
	protected long allocatedAddress;
	
	public ProxyOnUnsafeBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
		allocatedAddress = unsafe.allocateMemory(elementCount * OffHeapBenchmarkTrade.SIZE);
	}
	
	@Override
	public void reset() {
		unsafe.setMemory(allocatedAddress, elementCount * OffHeapBenchmarkTrade.SIZE, (byte) 0);
		index = 0;
	}
	
	@Override
	public OffHeapBenchmarkTrade createElement() {
		if (index >= elementCount) {
			throw new IllegalArgumentException("There is no space for new element !");
		}
		return 
			new UnsafeBasedProxyOffHeapBenchmarkTrade(
					unsafe, 
					allocatedAddress + (index++ * OffHeapBenchmarkTrade.SIZE));
	}
	
	@Override
	public void saveElement(OffHeapBenchmarkTrade element) {
		// Nothing required since object saves itself
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(int elementOrder) {
		if (elementOrder >= elementCount) {
			throw 
				new IllegalArgumentException(
					"Out of bounds ! " + 
					(elementCount - 1) + " is allowed at max but you requested " + elementOrder);
		}
		return 
			new UnsafeBasedProxyOffHeapBenchmarkTrade(
					unsafe, 
					allocatedAddress + (elementOrder * OffHeapBenchmarkTrade.SIZE));
	}
	
	@Override
	public void finish() {
		unsafe.freeMemory(allocatedAddress);
	}

}
