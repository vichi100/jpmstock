package com.jp.exchange.service.stock;

import java.math.BigDecimal;
import java.util.List;

import com.jp.exchange.dao.stock.StockDAO;
import com.jp.exchange.dao.stock.StockDAOImplementation;
import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.trade.*;
import com.jp.exchange.model.stock.StockType;

public class StockServiceImplemation implements StockService {

	private static volatile StockServiceImplemation instance = null;
	private StockDAO stockDAO = new StockDAOImplementation();

	public static StockServiceImplemation getInstance() {
		if (instance == null) {
			synchronized (StockServiceImplemation.class) {
				if (instance == null) {
					instance = new StockServiceImplemation();
				}

			}

		}
		return instance;
	}

	@Override
	public void addStock(Stock stock) {
		stockDAO.addStock(stock);
	}

	@Override
	public Stock getStock(String symbol) {
		return stockDAO.getStock(symbol);
	}

	public double calculateDividendYield(Stock stock, double price) {
		if (StockType.PREFERRED.equals(stock.getType())) {
			return (stock.getFixedDividend() * stock.getParValue()) / price;
		}
		double result = stock.getLastDividend() / price;
		return round(result, 3);
	}

	public double calculatePERatio(Stock stock, double price) {
		double result = price / stock.getLastDividend();
		return round(result, 3);
	}

	public double calculateVolumeWeightedStockPrice(List<Trade> trades) {
		double sumOfPriceQuantity = 0;
		int sumOfQuantity = 0;

		for (Trade trade : trades) {
			sumOfPriceQuantity = sumOfPriceQuantity + (trade.getPrice() * trade.getQuantity());
			sumOfQuantity = sumOfQuantity + trade.getQuantity();
		}
		double result = sumOfPriceQuantity / sumOfQuantity;
		return round(result, 3);
	}

	public double calculateGBCE(List<Trade> trades) {
		double total = 1;
		for (Trade trade : trades) {
			total = total * trade.getPrice();
		}
		double result = Math.pow(total, (1 / trades.size()));
		return round(result, 3);
	}

	private static double round(double value, int places) {
		BigDecimal bd = new BigDecimal(value);
		return bd.doubleValue();
	}

}