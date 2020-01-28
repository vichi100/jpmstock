package com.jp.exchange.service.trade;

import java.util.List;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.Trade;

public interface TradeService {

	public void recordTrade(Trade trade);

	public List<Trade> getTrades(Stock stock, int numberOfMinutes);

	public List<Trade> getAllTrades();
}
