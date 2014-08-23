package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.directbytebuffer;

import java.nio.ByteBuffer;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.ByteBufferBasedOffHeapBenchmarkWorker;

public class DirectByteBufferBasedOffHeapBenchmarkWorker extends ByteBufferBasedOffHeapBenchmarkWorker {

	public static final String TYPE = "DIRECT BYTE BUFFER BASED OFFHEAP";

	public DirectByteBufferBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
	}
	
	@Override
	protected ByteBuffer createByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size);
	}
	
}
