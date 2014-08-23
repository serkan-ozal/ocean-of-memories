package com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model;

public interface OffHeapBenchmarkTrade {

	/*
	 * @00  8 <Object Header>
	 * @08  8 OffHeapBenchmarkTradeBean.tradeId
	 * @16  8 OffHeapBenchmarkTradeBean.clientId
	 * @24  8 OffHeapBenchmarkTradeBean.price
	 * @32  8 OffHeapBenchmarkTradeBean.quantity
	 * @40  4 OffHeapBenchmarkTradeBean.venueCode
	 * @44  4 OffHeapBenchmarkTradeBean.instrumentCode
	 * @48  8 OffHeapBenchmarkTradeBean.side
	 * @56    <End of Object>
	 * 
	 */
	
	int LAYOUTED_SIZE = 48; // 56 - 8 = 48;
	int SIZE = 42; // 8 + 8 + 4 + 4 + 8 + 8 + 2;
	
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
