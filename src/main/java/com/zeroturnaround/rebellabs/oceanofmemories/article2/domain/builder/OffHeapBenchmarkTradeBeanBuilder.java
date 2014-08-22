package com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.builder;

import com.zeroturnaround.rebellabs.oceanofmemories.article2.domain.model.OffHeapBenchmarkTradeBean;
import com.zeroturnaround.rebellabs.oceanofmemories.common.domain.builder.Builder;

public class OffHeapBenchmarkTradeBeanBuilder implements Builder<OffHeapBenchmarkTradeBean> {

	private long elementKey;
	private long tradeId;
	private long clientId;
	private int venueCode;
	private int instrumentCode;
	private long price;
	private long quantity;
	private char side;
	
	@Override
	public OffHeapBenchmarkTradeBean build() {
		// TODO Auto-generated method stub
		return null;
	}

	public OffHeapBenchmarkTradeBeanBuilder tradeId(long tradeId) {
		this.tradeId = tradeId;
		return this;
	}

	public OffHeapBenchmarkTradeBeanBuilder clientId(long clientId) {
		this.clientId = clientId;
		return this;
	}

	public OffHeapBenchmarkTradeBeanBuilder venueCode(int venueCode) {
		this.venueCode = venueCode;
		return this;
	}

	public OffHeapBenchmarkTradeBeanBuilder instrumentCode(int instrumentCode) {
		this.instrumentCode = instrumentCode;
		return this;
	}
	
	public OffHeapBenchmarkTradeBeanBuilder price(long price) {
		this.price = price;
		return this;
	}

	public OffHeapBenchmarkTradeBeanBuilder quantity(long quantity) {
		this.quantity = quantity;
		return this;
	}

	public OffHeapBenchmarkTradeBeanBuilder side(char side) {
		this.side = side;
		return this;
	}

}
