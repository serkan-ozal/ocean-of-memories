package com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model;

public interface OffHeapBenchmarkTrade {

	long getTradeId();
	void setTradeId(long tradeId);
	
	long getClientId();
	void setClientId(long clientId);
	
	int getVenueCode();
	void setVenueCode(int venueCode);
	
	int getInstrumentCode();
	void setInstrumentCode(int instrumentCode);
	
	long getPrice();
	void setPrice(long price);
	
	long getQuantity();
	void setQuantity(long quantity);
	
	char getSide();
	void setSide(char side);

}
