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
	
	private FileChannel fc;
	
	public MemoryMappedFileBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
	}
	
	@Override
	protected ByteBuffer createByteBuffer(int size) {
		try {
			fc = 
				new RandomAccessFile(
					new File("OffHeapBenchmarkTrade.bin"), "rw").getChannel();
			return fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
		}
		catch (Throwable t) {
			logger.error("Unable to initialize memory mapped file !", t);
			if (fc != null) {
				try {
					fc.close();
				} 
				catch (IOException e) {
					logger.error(e);
				}
			}
			throw new IllegalStateException(t);
		}
	}
	
	@Override
	public void flush() {
		((MappedByteBuffer)bb).force();
	}
	
	@Override
	public void finish() {
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
