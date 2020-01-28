package com.jp.exchange.dao.trade;

import java.util.List;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.Trade;

public interface TradeDAO {
	void addTrade(Trade trade);

	List<Trade> getTrades(Stock stock, int minutes);

	List<Trade> getAllTrades();
}
