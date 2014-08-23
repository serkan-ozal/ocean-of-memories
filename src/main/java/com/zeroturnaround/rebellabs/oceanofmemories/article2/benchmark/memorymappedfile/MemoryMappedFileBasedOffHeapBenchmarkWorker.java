package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.memorymappedfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.ByteBufferBasedOffHeapBenchmarkWorker;

public class MemoryMappedFileBasedOffHeapBenchmarkWorker extends ByteBufferBasedOffHeapBenchmarkWorker {

	public static final String TYPE = "MEMORY MAPPED FILE BASED OFFHEAP";
	
	public MemoryMappedFileBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
	}
	
	@Override
	protected ByteBuffer createByteBuffer(int size) {
		FileChannel fc = null;
		try {
			fc = 
				new RandomAccessFile(
					new File("OffHeapBenchmarkTrade.bin"), "rw").getChannel();
			return fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
		}
		catch (Throwable t) {
			logger.error("Unable to initialize memory mapped file !", t);
			throw new IllegalStateException(t);
		}
		finally {
			if (fc != null) {
				try {
					fc.close();
				} 
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
	
	@Override
	public void finish() {
		((MappedByteBuffer)bb).force();
	}
	
}
