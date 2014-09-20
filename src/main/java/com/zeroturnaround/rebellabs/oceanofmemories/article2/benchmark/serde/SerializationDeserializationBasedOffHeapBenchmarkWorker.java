package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.serde;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import sun.misc.Unsafe;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.BaseOffHeapBenchmarkWorker;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;
import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTradeBean;
import com.zeroturnaround.rebellabs.oceanofmemories.common.util.JvmUtil;

public class SerializationDeserializationBasedOffHeapBenchmarkWorker extends BaseOffHeapBenchmarkWorker {

	public static final String TYPE = "SERIALIZATION/DESERIALIZATION BASED OFFHEAP";
	
	protected static final Unsafe UNSAFE = JvmUtil.getUnsafe();
	protected static final int BYTE_ARRAY_START_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
	protected static final int BYTE_ARRAY_INDEX_SCALE = UNSAFE.arrayIndexScale(byte[].class);
	protected static final int STREAM_HEADER_SIZE = 4;
	protected static final int OFFSET_INDEX_ELEMENT_SIZE = 8;
	
	protected int index;
	protected int offsetIndex;
	protected long allocatedAddress;
	protected long offsetAddress;
	protected long allocationSize;
	protected UnsafeBasedOutputStream unsafeBasedOutputStream;
	protected ObjectOutputStream oos;
	protected UnsafeBasedInputStream unsafeBasedInputStream;
	protected ObjectInputStream ois;
	
	public SerializationDeserializationBasedOffHeapBenchmarkWorker(int elementCount) {
		super(elementCount, TYPE);
		this.allocationSize = 	STREAM_HEADER_SIZE + 
								(elementCount *  OffHeapBenchmarkTrade.STREAMED_APPROXIMATE_SIZE);
		this.allocatedAddress = UNSAFE.allocateMemory(allocationSize);
		this.offsetAddress = UNSAFE.allocateMemory(elementCount * OFFSET_INDEX_ELEMENT_SIZE);
		try {
			unsafeBasedOutputStream = 
				new UnsafeBasedOutputStream(
						UNSAFE, 
						allocatedAddress, 
						allocationSize);
			oos = new ObjectOutputStream(unsafeBasedOutputStream);
			unsafeBasedInputStream = 
				new UnsafeBasedInputStream(
						UNSAFE, 
						allocatedAddress, 
						allocationSize);
			ois = new ObjectInputStream(unsafeBasedInputStream);
		} 
		catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public void reset() {
		UNSAFE.setMemory(allocatedAddress, allocationSize, (byte) 0);
		UNSAFE.setMemory(offsetAddress, elementCount * OFFSET_INDEX_ELEMENT_SIZE, (byte) 0);
		index = 0;
		offsetIndex = 0;
		unsafeBasedOutputStream.reset();
		unsafeBasedInputStream.reset();
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
		try {
			UNSAFE.putLong(offsetAddress + offsetIndex, unsafeBasedOutputStream.offset());
			oos.writeObject(element);
			oos.flush();
			index++;
			offsetIndex += OFFSET_INDEX_ELEMENT_SIZE;
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public OffHeapBenchmarkTrade getElement(int elementOrder) {
		if (elementOrder >= elementCount) {
			throw 
				new IllegalArgumentException(
					"Out of bounds ! " + 
					(elementCount - 1) + " is allowed at max but you requested " + elementOrder);
		}
		try {
			long offset = UNSAFE.getLong(offsetAddress + (elementOrder * OFFSET_INDEX_ELEMENT_SIZE));
			unsafeBasedInputStream.moveTo(offset);
			return (OffHeapBenchmarkTrade)ois.readObject();
		} 
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void flush() {
		try {
			oos.flush();
		} 
		catch (IOException e) {
			logger.error(e);
		}
	}
	
	@Override
	public void finish() {
		try {
			oos.close();
		} 
		catch (IOException e) {
			logger.error(e);
		}
		UNSAFE.freeMemory(allocatedAddress);
		UNSAFE.freeMemory(offsetAddress);
	}
	
	private static class UnsafeBasedOutputStream extends OutputStream {
		
		private final Unsafe unsafe;
		private final long startAddress;
		private final long endAddress;
		private long currentAddress;
		
		UnsafeBasedOutputStream(Unsafe unsafe, long startAddress, long size) {
			this.unsafe = unsafe;
			this.startAddress = startAddress;
			this.endAddress = startAddress + size;
			this.currentAddress = startAddress;
		}

		@Override
		public void write(int b) throws IOException {
			if (currentAddress >= endAddress) {
				throw new IOException("There is no writable space for new data !");
			}
			unsafe.putByte(currentAddress++, (byte) b);
		}
		
		public void write(byte b[], int off, int len) throws IOException {
			if (currentAddress + len >= endAddress) {
				throw new IOException(
						"There is no writable space for new data array " + 
							"(" + 	"remaining: " + (endAddress - currentAddress) + ", " + 
									"requested: " + len + 
							") !");
			}
			unsafe.copyMemory(	b, 
								BYTE_ARRAY_START_OFFSET + (BYTE_ARRAY_INDEX_SCALE * off),
								null, 
								currentAddress, 
								len);
			currentAddress += len;
		}
		
		@SuppressWarnings("unused")
		void moveTo(long offset) {
			currentAddress = startAddress + offset;
		}
		
		void reset() {
			currentAddress = 0;
		}
		
		long offset() {
			return currentAddress - startAddress;
		}

	}
	
	private static class UnsafeBasedInputStream extends InputStream {
		
		private final Unsafe unsafe;
		private final long startAddress;
		private final long endAddress;
		private long currentAddress;
		
		UnsafeBasedInputStream(Unsafe unsafe, long startAddress, long size) {
			this.unsafe = unsafe;
			this.startAddress = startAddress;
			this.endAddress = startAddress + size;
			this.currentAddress = startAddress;
		}

		@Override
		public int read() throws IOException {
			if (currentAddress >= endAddress) {
				throw new IOException("There is no writable space for new data !");
			}
			return unsafe.getByte(currentAddress++) & 0xFF;
		}

		public int read(byte b[], int off, int len) throws IOException {
			if (currentAddress >= endAddress) {
				throw new IOException(
						"There is no writable space for new data array " + 
							"(" + 	"remaining: " + (endAddress - currentAddress) + ", " + 
									"requested: " + len + 
							") !");
			}
			len = Math.min((int) (endAddress - currentAddress), len);
			unsafe.copyMemory(
					null, 
					currentAddress, 
					b, 
					BYTE_ARRAY_START_OFFSET + (BYTE_ARRAY_INDEX_SCALE * off),  
					len);
			currentAddress += len;
			return len;
		}
		
		@Override
		public long skip(long n) throws IOException {
			return currentAddress += n;
		}
		
		@Override
		public int available() throws IOException {
			return (int) (endAddress - currentAddress);
		}
		
		void moveTo(long offset) {
			currentAddress = startAddress + offset;
		}
		
		@Override
		public void reset() {
			currentAddress = 0;
		}

	}

}
