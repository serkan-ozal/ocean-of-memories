package com.zeroturnaround.rebellabs.oceanofmemories.article2;

import org.openjdk.jmh.annotations.Param;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.OffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.OffHeapBenchmarkWorkerFactory;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.directbytebuffer.DirectByteBufferBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.memorymappedfile.MemoryMappedFileBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.common.benchmark.BaseBenchmarkRunner;

public class OffHeapBenchmarkRunner extends BaseBenchmarkRunner {

	public static final int OFFHEAP_BENCHMARK_RUNNER_ID = 1;
	
	public static final int ELEMENT_COUNT = 1000000;
	
	@Param(
		value = {
			DirectByteBufferBasedOffHeapBenchmarkWorker.TYPE,
			MemoryMappedFileBasedOffHeapBenchmarkWorker.TYPE,
			// UnsafeBasedOffHeapBenchmarkWorker.TYPE // TODO Uncomment after implement
		})
    private String workerType;
	
	public OffHeapBenchmarkRunner() {
		super(OFFHEAP_BENCHMARK_RUNNER_ID);
	}
	
	@Override
	protected void doRun() {
		int key = 1000;
		OffHeapBenchmarkWorker runner = 
				OffHeapBenchmarkWorkerFactory.createOffHeapBenchmarkWorker(workerType, ELEMENT_COUNT);
		
		for (int i = 0; i < ELEMENT_COUNT; i++, key++) {
			OffHeapBenchmarkTrade element = runner.createElement();
			element.setTradeId(key << 1);
			element.setClientId(key << 2);
			element.setVenueCode(key >> 2);
			element.setInstrumentCode((int) (key >> 4));
			element.setPrice(key << 3);
			element.setQuantity(key << 4);
			element.setSide((char) (key % Character.MAX_VALUE));
			runner.saveElement(element);
		}

		for (int i = 0; i < ELEMENT_COUNT; i++, key++) {
			runner.getElement(i);
		}
		
		runner.finish();
	}

}
