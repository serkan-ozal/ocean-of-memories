package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.directbytebuffer.DirectByteBufferBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.memorymappedfile.MemoryMappedFileBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.proxyonunsafe.ProxyOnUnsafeBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.serde.SerializationDeserializationBasedOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.unsafe.UnsafeBasedOffHeapBenchmarkWorker;

public class OffHeapBenchmarkWorkerFactory {

	private OffHeapBenchmarkWorkerFactory() {
		
	}
	
	public static OffHeapBenchmarkWorker createOffHeapBenchmarkWorker(String workerType, int elementCount) {
		if (workerType.equalsIgnoreCase(DirectByteBufferBasedOffHeapBenchmarkWorker.TYPE)) {
			return new DirectByteBufferBasedOffHeapBenchmarkWorker(elementCount);
		}
		else if (workerType.equalsIgnoreCase(MemoryMappedFileBasedOffHeapBenchmarkWorker.TYPE)) {
			return new MemoryMappedFileBasedOffHeapBenchmarkWorker(elementCount);
		}
		else if (workerType.equalsIgnoreCase(UnsafeBasedOffHeapBenchmarkWorker.TYPE)) {
			return new UnsafeBasedOffHeapBenchmarkWorker(elementCount);
		}
		else if (workerType.equalsIgnoreCase(ProxyOnUnsafeBasedOffHeapBenchmarkWorker.TYPE)) {
			return new ProxyOnUnsafeBasedOffHeapBenchmarkWorker(elementCount);
		}
		else if (workerType.equalsIgnoreCase(SerializationDeserializationBasedOffHeapBenchmarkWorker.TYPE)) {
			return new SerializationDeserializationBasedOffHeapBenchmarkWorker(elementCount);
		}
		else {
			throw new IllegalArgumentException("Invalid worker type: " + workerType);
		}
	}

}
