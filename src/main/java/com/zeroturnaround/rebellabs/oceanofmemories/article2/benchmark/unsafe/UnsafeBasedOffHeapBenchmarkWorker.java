package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.unsafe;

import sun.misc.Unsafe;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTradeBean;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class UnsafeBasedOffHeapBenchmarkWorker extends BaseOffHeapBenchmarkWorker {

	public static final String TYPE = "UNSAFE BASED OFFHEAP";
	
	protected static final Unsafe unsafe = JvmUtil.getUnsafe();
	protected int index;
	protected long allocatedAddress;
	
	public UnsafeBasedOffHeapBenchmarkWorker(int elementCount) {
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
		return new OffHeapBenchmarkTradeBean();
	}
	
	@Override
	public void saveElement(OffHeapBenchmarkTrade element) {
		if (index >= elementCount) {
			throw new IllegalArgumentException("There is no space for new element !");
		}
		long address = allocatedAddress + (index++ * OffHeapBenchmarkTrade.SIZE);
		unsafe.putLong(address, element.getTradeId());
		unsafe.putLong(address + 8, element.getClientId());
		unsafe.putInt(address + 16, element.getVenueCode());
		unsafe.putInt(address + 20, element.getInstrumentCode());
		unsafe.putLong(address + 24, element.getPrice());
		unsafe.putLong(address + 32, element.getQuantity());
		unsafe.putChar(address + 40, element.getSide());
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(int elementOrder) {
		if (elementOrder >= elementCount) {
			throw 
				new IllegalArgumentException(
					"Out of bounds ! " + 
					(elementCount - 1) + " is allowed at max but you requested " + elementOrder);
		}
		long address = allocatedAddress + (elementOrder * OffHeapBenchmarkTrade.SIZE);
		return 
				new OffHeapBenchmarkTradeBean(
						unsafe.getLong(address), 
						unsafe.getLong(address + 8), 
						unsafe.getInt(address + 16), 
						unsafe.getInt(address + 20), 
						unsafe.getLong(address + 24), 
						unsafe.getLong(address + 32), 
						unsafe.getChar(address + 40));
	}
	
	@Override
	public void finish() {
		unsafe.freeMemory(allocatedAddress);
	}

}
