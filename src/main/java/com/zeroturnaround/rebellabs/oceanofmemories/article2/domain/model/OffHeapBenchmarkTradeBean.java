package com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model;

@SuppressWarnings("serial")
public class OffHeapBenchmarkTradeBean implements OffHeapBenchmarkTrade {

	private long tradeId;
	private long clientId;
	private int venueCode;
	private int instrumentCode;
	private long price;
	private long quantity;
	private char side;
	
	public OffHeapBenchmarkTradeBean() {
		
	}
	
	public OffHeapBenchmarkTradeBean(long key) {
		this.tradeId = key << 1;
		this.clientId = key << 2;
		this.venueCode = (int) (key >> 2);
		this.instrumentCode = (int) (key >> 4);
		this.price = key << 3;
		this.quantity = key << 4;
		this.side = (char) (key % 128);
	}
	
	public OffHeapBenchmarkTradeBean(long tradeId, long clientId,
			int venueCode, int instrumentCode, long price, long quantity, char side) {
		this.tradeId = tradeId;
		this.clientId = clientId;
		this.venueCode = venueCode;
		this.instrumentCode = instrumentCode;
		this.price = price;
		this.quantity = quantity;
		this.side = side;
	}

	@Override
	public long getTradeId() {
		return tradeId;
	}
	
	@Override
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	
	@Override
	public long getClientId() {
		return clientId;
	}
	
	@Override
	public void setClientId(long clientId) {
		this.clientId = clientId;
	}
	
	@Override
	public int getVenueCode() {
		return venueCode;
	}
	
	@Override
	public void setVenueCode(int venueCode) {
		this.venueCode = venueCode;
	}
	
	@Override
	public int getInstrumentCode() {
		return instrumentCode;
	}
	
	@Override
	public void setInstrumentCode(int instrumentCode) {
		this.instrumentCode = instrumentCode;
	}
	
	@Override
	public long getPrice() {
		return price;
	}
	
	@Override
	public void setPrice(long price) {
		this.price = price;
	}
	
	@Override
	public long getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public char getSide() {
		return side;
	}
	
	public void setSide(char side) {
		this.side = side;
	}
	
	@Override
	public String toString() {
		return "OffHeapBenchmarkTradeBean [tradeId=" + tradeId + ", clientId="
				+ clientId + ", venueCode=" + venueCode + ", instrumentCode="
				+ instrumentCode + ", price=" + price + ", quantity="
				+ quantity + ", side=" + side + "]";
	}

}
