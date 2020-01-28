package com.jp.exchange.dao.stock;

import java.util.HashMap;
import java.util.Map;

import com.jp.exchange.model.stock.Stock;

public class StockDAOImplementation implements StockDAO {

	private Map<String, Stock> stockMap = new HashMap<String, Stock>();

	@Override
	public void addStock(Stock stock) {
		stockMap.put(stock.getSymbol(), stock);

	}

	@Override
	public Stock getStock(String symbol) {
		// TODO Auto-generated method stub
		return stockMap.get(symbol);
	}

}
