package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

import java.nio.ByteBuffer;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTradeBean;

public abstract class ByteBufferBasedOffHeapBenchmarkWorker extends BaseOffHeapBenchmarkWorker {

	protected ByteBuffer bb;
	protected int index = 0;
	
	public ByteBufferBasedOffHeapBenchmarkWorker(int elementCount, String type) {
		super(elementCount, type);
		bb = createByteBuffer(elementCount * OffHeapBenchmarkTrade.SIZE);
	}
	
	abstract protected ByteBuffer createByteBuffer(int size);
	
	@Override
	public void reset() {
		bb.clear();
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
		bb.position(index++ * OffHeapBenchmarkTrade.SIZE);
		bb.putLong(element.getTradeId());
		bb.putLong(element.getClientId());
		bb.putInt(element.getVenueCode());
		bb.putInt(element.getInstrumentCode());
		bb.putLong(element.getPrice());
		bb.putLong(element.getQuantity());
		bb.putChar(element.getSide());
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(int elementOrder) {
		if (elementOrder >= elementCount) {
			throw 
				new IllegalArgumentException(
					"Out of bounds ! " + 
					(elementCount - 1) + " is allowed at max but you requested " + elementOrder);
		}
		bb.position(elementOrder * OffHeapBenchmarkTrade.SIZE);
		return 
			new OffHeapBenchmarkTradeBean(
					bb.getLong(), 
					bb.getLong(), 
					bb.getInt(), 
					bb.getInt(), 
					bb.getLong(), 
					bb.getLong(), 
					bb.getChar());
	}
	
}
