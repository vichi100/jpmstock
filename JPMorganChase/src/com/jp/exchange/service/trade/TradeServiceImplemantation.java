package com.jp.exchange.service.trade;

import java.util.List;

import com.jp.exchange.dao.trade.TradeDAO;
import com.jp.exchange.dao.trade.TradeDAOImplementation;
import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.Trade;

public class TradeServiceImplemantation implements TradeService {

	private static volatile TradeServiceImplemantation instance = null;
	private TradeDAO tradeDao = new TradeDAOImplementation();

	public static TradeService getInstance() {
		if (instance == null) {
			synchronized (TradeServiceImplemantation.class) {
				if (instance == null) {
					instance = new TradeServiceImplemantation();
				}

			}

		}
		return instance;
	}

	@Override
	public void recordTrade(Trade trade) {
		// TODO Auto-generated method stub
		if (trade != null && trade.getStock() != null) {
		      tradeDao.addTrade(trade);
		    }

	}

	@Override
	public List<Trade> getTrades(Stock stock, int numberOfMinutes) {
		// TODO Auto-generated method stub
		return tradeDao.getTrades(stock, numberOfMinutes);
	}

	@Override
	public List<Trade> getAllTrades() {
		// TODO Auto-generated method stub
		return tradeDao.getAllTrades();
	}

}
