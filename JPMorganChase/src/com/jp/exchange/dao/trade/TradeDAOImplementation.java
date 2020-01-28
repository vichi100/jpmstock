package com.jp.exchange.dao.trade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.Trade;

public class TradeDAOImplementation implements TradeDAO {

	private Map<String, List<Trade>> tradeMap = new HashMap<String, List<Trade>>(); 
	@Override
	public void addTrade(Trade trade) {
		List<Trade> trades = new ArrayList<Trade>();
		if (tradeMap.containsKey(trade.getStock().getSymbol())) {
			trades = tradeMap.get(trade.getStock().getSymbol());
		}
		trades.add(trade);
		tradeMap.put(trade.getStock().getSymbol(), trades);
	}

	@Override
	public List<Trade> getTrades(Stock stock, int minutes) {
		List<Trade> result = new ArrayList<Trade>();
		Date afterDate = getDateXMinutesEarlier(minutes);
		List<Trade> trades = tradeMap.get(stock.getSymbol());
		Collections.sort(trades); 
		Iterator<Trade> it = trades.iterator();
		while (it.hasNext()) {
			Trade trade = it.next();
			if (trade.getTimestamp().before(afterDate)) { 
				break;
			}
			result.add(trade);
		}
		return result;
	}

	@Override
	public List<Trade> getAllTrades() {
		List<Trade> result = new ArrayList<Trade>();
		for (String stockSymbol : tradeMap.keySet()) {
			result.addAll(tradeMap.get(stockSymbol));
		}
		return result;
	}

	private Date getDateXMinutesEarlier(int minutes) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -minutes);
		return c.getTime();
	}

}