package com.jp.exchange.server;

import com.jp.exchange.service.stock.StockService;
import com.jp.exchange.service.trade.TradeService;
import com.jp.exchange.service.trade.TradeServiceImplemantation;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.jp.exchange.exception.SimpleException;
import com.jp.exchange.model.stock.Stock;
import com.jp.exchange.model.stock.StockType;
import com.jp.exchange.model.trade.Trade;
import com.jp.exchange.model.trade.TradeType;
import com.jp.exchange.service.stock.*;

public class App {
	private static Scanner scanner;

	private static StockService stockService = StockServiceImplemation.getInstance();
	private static TradeService tradeService = TradeServiceImplemantation.getInstance();

	public static void main(String[] args) {
		initStocks();
		displayChoice();

		scanner = new Scanner(System.in);
		String choice = null;
		while (true) {
			choice = scanner.nextLine();
			if ("exit".equals(choice)) {
				scanner.close();
				System.exit(0);
			} else {
				try {
					Stock stockSymbole;
					double stockPrice;

					switch (choice.toUpperCase()) {
					case "STOCK":
						stockSymbole = getStockSymbole();
						stockPrice = getStockPrice();
						calculateDividendYield(stockSymbole, stockPrice);
						break;
					case "PE":
						// System.out.println("trade");
						stockSymbole = getStockSymbole();
						stockPrice = getStockPrice();
						calculatePERatio(stockSymbole, stockPrice);
						break;
					case "TRADE":
						// System.out.println("trade");
						stockSymbole = getStockSymbole();
						int quantityFromUser = getQuantityFromUser();
						TradeType tradeTypeFromUser = getTradeType();
						stockPrice = getStockPrice();
						recordTrade(stockSymbole, quantityFromUser, tradeTypeFromUser, stockPrice);
						break;
					case "WSP":
						stockSymbole = getStockSymbole();
						calculateVolumeWeightedStockPrice(stockSymbole);
						break;
					case "GBSE":
						calculateGBCE();
						break;
					default:
						break;
					}
				} catch (NumberFormatException e) {
					displayResult("Invalid Option");
				} catch (SimpleException e1) {
					displayResult(e1.getMessage());
				}
				System.out.println("--------------------");
				displayChoice();
			}
		}
	}

	private static Stock getStockSymbole() throws SimpleException {
		System.out.println("Please type stock symbol");
		String stockSymbol = scanner.nextLine();
		Stock stock = stockService.getStock(stockSymbol);
		if (stock == null) {
			throw new SimpleException("Stock not found");
		}
		return stock;
	}

	private static double getStockPrice() throws SimpleException {
		System.out.println("Please type stock price");
		String stockPrice = scanner.nextLine();
		try {
			double result = Double.parseDouble(stockPrice);
			if (result <= 0) {
				throw new SimpleException("Invalid price: price should be greated than 0");
			}
			return result;
		} catch (NumberFormatException e) {
			throw new SimpleException("Price is not valid");
		}
	}

	private static TradeType getTradeType() throws SimpleException {
		System.out.println("Please input trade type Buy or Sell");
		String type = scanner.nextLine();
		try {
			return TradeType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new SimpleException("Trade type should be either BUY or SELL");
		}
	}

	private static int getQuantityFromUser() throws SimpleException {
		System.out.println("Please input quantity");
		String quantity = scanner.nextLine();
		try {
			int result = Integer.parseInt(quantity);
			if (result <= 0) {
				throw new SimpleException("Quantity should be greated than 0");
			}
			return result;
		} catch (NumberFormatException e) {
			throw new SimpleException("Quantity should be number");
		}
	}

	private static void displayChoice() {
		System.out.println("Type Stock to calculate dividend yield for stock");
		System.out.println("Type PE to calculate PE ratio for stock");
		System.out.println("Type Trade to record a trade for stock");
		System.out.println("Type WSP to calculate Volume Weighted Stock Price for a stock");
		System.out.println("Type GBSE calculate GBCE All Share Index");
		System.out.println("Type Exit to quite");
	}

	private static void calculateDividendYield(Stock stock, double price) {
		double result = stockService.calculateDividendYield(stock, price);
		displayResult("Dividend Yield: " + result);
	}

	private static void calculatePERatio(Stock stock, double price) {
		double result = stockService.calculatePERatio(stock, price);
		displayResult("PE Ratio: " + result);
	}

	private static void calculateVolumeWeightedStockPrice(Stock stock) {
		List<Trade> trades = tradeService.getTrades(stock, 10);
		if (trades == null || trades.isEmpty()) {
			displayResult("Volume Weighted Stock Price: No trades");
		} else {
			double result = stockService.calculateVolumeWeightedStockPrice(trades);
			displayResult("Volume Weighted Stock Price: " + result);
		}
	}

	private static void recordTrade(Stock stock, int quantity, TradeType type, double price) {
		tradeService.recordTrade(new Trade(stock, Calendar.getInstance().getTime(), quantity, type, price));
		displayResult("Trade recorded");
	}

	private static void calculateGBCE() {
		List<Trade> allTrades = tradeService.getAllTrades();
		if (allTrades == null || allTrades.size() == 0) {
			displayResult("No trades");
		} else {
			displayResult("GBCE: " + stockService.calculateGBCE(allTrades));
		}
	}

	private static void displayResult(String result) {
		System.out.println(result);

	}

	/*
	 * Stock Symbol
	 * 
	 * Type Last Dividend
	 * 
	 * Fixed Dividend
	 * 
	 * Par Value TEA Common 0 100 POP Common 8 100 ALE Common 23 60 GIN Preferred 8
	 * 2% 100 JOE Common 13 250
	 * 
	 */
	private static void initStocks() {
		stockService.addStock(new Stock("TEA", StockType.COMMON, 0, 0, 100));
		stockService.addStock(new Stock("POP", StockType.COMMON, 8, 0, 100));
		stockService.addStock(new Stock("ALE", StockType.COMMON, 23, 0, 60));
		stockService.addStock(new Stock("GIN", StockType.PREFERRED, 8, 2, 100));
		stockService.addStock(new Stock("JOE", StockType.COMMON, 13, 0, 250));
	}

}
