package com.jp.exchange.test.service;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.stock.StockType;
import com.jp.exchange.model.trade.Trade;
import com.jp.exchange.model.trade.TradeType;
import com.jp.exchange.service.stock.StockService;
import com.jp.exchange.service.stock.StockServiceImplemation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StockServiceImplemantationTest {

	
	private StockService stockService;
	private Stock stockA;
	private Stock stockB;

	@Before
	public void setup() {
		stockService = new StockServiceImplemation();
		stockA = new Stock("TEST1", StockType.COMMON, 7, 0, 15);
		stockB = new Stock("TEST2", StockType.PREFERRED, 5, 12, 125);
	}
	
	@Test
	public void testCalculatePERatio() {
		double pe = stockService.calculatePERatio(stockA, 10);
		System.out.println("PE: "+pe);
		assertEquals(1.42, pe, 2);
	}

	@Test
	public void testAddAndGetStock() {
		stockService.addStock(stockA);
		Stock result = stockService.getStock(stockA.getSymbol());
		assertEquals(stockA, result);
	}

	@Test
	public void testCalculateDividendYield() {
		double divident =  stockService.calculateDividendYield(stockA, 300);
		System.out.println("dividentx: "+Math.floor(divident));
		assertEquals(0.0, Math.floor(divident), 2);
	}

	@Test
	public void testCalculateDividendYieldPrefered() {
		double divident =  stockService.calculateDividendYield(stockB, 300);
		System.out.println("divident: "+Math.floor(divident));
		assertEquals(5.0, divident, 2);
	}

	
	
	@Test
	public void testCalculateGBCE() {
		List<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(stockA, Calendar.getInstance().getTime(), 1, TradeType.BUY, 93));
		trades.add(new Trade(stockB, Calendar.getInstance().getTime(), 3, TradeType.BUY, 18));
		double gbce = stockService.calculateGBCE(trades);
		System.out.println("gbce: "+gbce);
		assertEquals(1.0, gbce, 2);
	}

	@Test
	public void testCalculateVolumeWeightedStockPrice() {
		List<Trade> trades = new ArrayList<Trade>();
		trades.add(new Trade(stockA, Calendar.getInstance().getTime(), 5, TradeType.BUY, 93));
		trades.add(new Trade(stockA, Calendar.getInstance().getTime(), 12, TradeType.BUY, 18));
		trades.add(new Trade(stockA, Calendar.getInstance().getTime(), 8, TradeType.BUY, 67));
		double wsp= stockService.calculateVolumeWeightedStockPrice(trades);
		System.out.println("wsp: "+wsp);
		assertEquals(48.68, wsp, 2);
	}


}
