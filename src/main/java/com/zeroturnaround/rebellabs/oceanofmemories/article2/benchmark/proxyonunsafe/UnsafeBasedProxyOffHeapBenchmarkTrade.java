package com.zeroturnaround.rebellabs.oceanofmemories.article2.benchmark.proxyonunsafe;

import sun.misc.Unsafe;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTrade;

@SuppressWarnings("serial")
public class UnsafeBasedProxyOffHeapBenchmarkTrade implements OffHeapBenchmarkTrade {

	private final Unsafe unsafe;
	private final long address;

	public UnsafeBasedProxyOffHeapBenchmarkTrade(Unsafe unsafe, long address) {
		this.unsafe = unsafe;
		this.address = address;
	}
	
	@Override
	public long getTradeId() {
		return unsafe.getLong(address);
	}
	
	@Override
	public void setTradeId(long tradeId) {
		unsafe.putLong(address, tradeId);
	}
	
	@Override
	public long getClientId() {
		return unsafe.getLong(address + 8);
	}
	
	@Override
	public void setClientId(long clientId) {
		unsafe.putLong(address + 8, clientId);
	}
	
	@Override
	public int getVenueCode() {
		return unsafe.getInt(address + 16);
	}
	
	@Override
	public void setVenueCode(int venueCode) {
		unsafe.putInt(address + 16, venueCode);
	}
	
	@Override
	public int getInstrumentCode() {
		return unsafe.getInt(address + 20);
	}
	
	@Override
	public void setInstrumentCode(int instrumentCode) {
		unsafe.putInt(address + 20, instrumentCode);
	}
	
	@Override
	public long getPrice() {
		return unsafe.getLong(address + 24);
	}
	
	@Override
	public void setPrice(long price) {
		unsafe.putLong(address + 24, price);
	}
	
	@Override
	public long getQuantity() {
		return unsafe.getLong(address + 32);
	}
	
	@Override
	public void setQuantity(long quantity) {
		unsafe.putLong(address + 32, quantity);
	}
	
	@Override
	public char getSide() {
		return unsafe.getChar(address + 40);
	}
	
	public void setSide(char side) {
		unsafe.putChar(address + 40, side);
	}
	
	@Override
	public String toString() {
		return "OffHeapBenchmarkTradeBean [tradeId=" + getTradeId() + ", clientId="
				+ getClientId() + ", venueCode=" + getVenueCode() + ", instrumentCode="
				+ getInstrumentCode() + ", price=" + getPrice() + ", quantity="
				+ getQuantity() + ", side=" + getSide() + "]";
	}

}
