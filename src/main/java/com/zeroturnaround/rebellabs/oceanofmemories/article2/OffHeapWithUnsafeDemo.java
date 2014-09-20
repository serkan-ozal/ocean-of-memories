package com.zeroturnaround.rebellabs.oceanofmemories.article2;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.OffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.unsafe.UnsafeBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;

public class OffHeapWithUnsafeDemo {

	static {
		System.setProperty("disableHotspotSA", "true");
	}
	
	public static void main(String[] args) {
		final int ELEMENT_COUNT = 1000;
		int key = 1000;
		OffHeapBenchmarkWorker runner = 
				new UnsafeBasedOffHeapBenchmarkWorker(ELEMENT_COUNT);
		
		for (int i = 0; i < ELEMENT_COUNT; i++, key++) {
			OffHeapBenchmarkTrade element = runner.createElement();
			element.setTradeId(key << 1);
			element.setClientId(key << 2);
			element.setVenueCode(key >> 2);
			element.setInstrumentCode((int) (key >> 4));
			element.setPrice(key << 3);
			element.setQuantity(key << 4);
			element.setSide((char) (key % 128));
			runner.saveElement(element);
		}

		for (int i = 0; i < ELEMENT_COUNT; i++, key++) {
			System.out.println(runner.getElement(i));
		}
	}

}
