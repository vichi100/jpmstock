package com.jp.exchange.test.service;

import org.junit.Before;
import org.junit.Test;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.stock.StockType;
import com.jp.exchange.model.trade.Trade;
import com.jp.exchange.model.trade.TradeType;
import com.jp.exchange.service.trade.TradeService;
import com.jp.exchange.service.trade.TradeServiceImplemantation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.List;

public class TradeServiceImplemantationTest {

	private Stock stock;
	private TradeService tradeService;

	@Before
	public void setup() {
		tradeService = new TradeServiceImplemantation();
		stock = new Stock("TEST", StockType.COMMON, 20, 0, 15);
	}

	@Test
	public void testRecordTrade() {
		Trade trade = new Trade(stock, Calendar.getInstance().getTime(), 5, TradeType.BUY, 30.0);
		tradeService.recordTrade(trade);
		List<Trade> result = tradeService.getTrades(stock, 10);
		System.out.println("size: " + result.size());
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void testGetAllTrades() {
		tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(), 4, TradeType.BUY, 20.0));
		tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(), 5, TradeType.BUY, 25.0));
		tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(), 7, TradeType.BUY, 30.0));
		List<Trade> result = tradeService.getAllTrades();
		assertEquals(3, result.size());
	}

}