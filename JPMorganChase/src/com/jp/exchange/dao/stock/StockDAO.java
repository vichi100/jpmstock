package com.jp.exchange.dao.stock;

import com.jp.exchange.model.stock.Stock;

public interface StockDAO {
	void addStock(Stock stock);
	Stock getStock(String symbol);

}
