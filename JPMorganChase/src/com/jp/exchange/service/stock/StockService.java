package com.jp.exchange.service.stock;

import java.util.List;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.*;

public interface StockService {

	
	public Stock getStock(String symbol);
	public void addStock(Stock stock);
	
	public double calculateDividendYield(Stock stock, double price);

	public double calculatePERatio(Stock stock, double price);

	public double calculateVolumeWeightedStockPrice(List<Trade> trades);

	public double calculateGBCE(List<Trade> trades);
}
