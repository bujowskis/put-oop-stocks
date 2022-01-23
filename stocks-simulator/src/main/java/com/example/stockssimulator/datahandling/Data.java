package com.example.stockssimulator.datahandling;

import com.example.stockssimulator.Simulation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// todo - add functions fetching data needed for plotting and such
/**
 * Contains the data relevant for the simulation (i.e. Stocks info), and provides functionalities how to obtain it.
 */
public class Data {
	private final Simulation simulation;
	private HashMap<String, Stock> stocks;
	private ArrayList<Stock> stocks_al;
	private ArrayList<Stock> stocks_available; // all available stocks (given as tickers) to add to stocks
	private String stocks_path; 			// todo - add getting relative path OR prompt user to specify where data is
	private String stocks_available_path; 	// todo - as above
	private int next_available_idx; // remembers index of the next stock available for adding
	private final int start_time = convertTimeStringToInt("9:30"); 	// todo - add relative check instead of hardcoded
	private final int end_time = convertTimeStringToInt("15:59"); 	// todo - add relative check instead of hardcoded
	// checking the data
	private int duration = 390; // todo - not hardcoded?

	/**
	 * Creates Data used by the Simulation
	 * @param simulation reference to the Simulation this Data belongs to
	 * @param stocks_path path to the csv files containing OHLCV's for stocks, named "(ticker).csv"
	 * @param stocks_available_path path to the csv file named "Company.csv", storing metainfo about all available stocks
	 */
	public Data(Simulation simulation, String stocks_path, String stocks_available_path) throws IOException, IllegalArgumentException {
		if (simulation == null) {
			throw new IllegalArgumentException("Data must belong to Simulation");
		}
		this.simulation = simulation;
		this.stocks_path = stocks_path;
		this.stocks_available_path = stocks_available_path;
		// read the available stocks
		this.stocks_available = readAvailableStocks();
		if (stocks_available.isEmpty()) {
			// todo - add the best way of handling this
			throw new IllegalArgumentException("no stocks available");
		}
		this.next_available_idx = 0;
		this.stocks = new HashMap<>();
		this.stocks_al = new ArrayList<>();
	}

	/**
	 * Reads the available stocks, which can be then used in the Simulation.
	 * The stocks are shuffled, such that when different seed provided, they should be different for each Instance of
	 * Simulation
	 * @return ArrayList with tickers of all the available stocks
	 */
	private ArrayList<Stock> readAvailableStocks() throws IllegalArgumentException, IOException {
		List<String> list_of_lines = new ArrayList<>();

		// read the csv by rows into List of Strings
		FileReader fr;
		try {
			fr = new FileReader(stocks_available_path + "Company.csv");
		} catch (FileNotFoundException e) {
			// todo - prompt user to enter path to data
			//		BUT try to make it relative (then it's programming Error)
			e.printStackTrace();
			throw new IllegalArgumentException("Company.csv not found");
		}
		try (BufferedReader bf = new BufferedReader(fr)) {
			// check first line for proper formatting
			String line = bf.readLine();
			if (!Objects.equals(line, "Ticker,Company,Sector,Industry,Country,Market Cap,P/E,Volume")) {
				throw new IllegalArgumentException("Company.csv wrong format");
			}
			line = bf.readLine();
			while (line != null) {
				list_of_lines.add(line);
				line = bf.readLine();
			}
		} catch (IOException io) {
			io.printStackTrace();
			throw new IOException("Company.csv IOException");
		}

		// create the ArrayList
		ArrayList<Stock> available_stocks = new ArrayList<>();
		for (String line : list_of_lines) {
			String[] values = line.split(",");
			available_stocks.add(new Stock(values[0], values[1], values[2], values[3], values[4]));
		}
		// shuffle it to randomize Stocks chosen for Simulation
		Collections.shuffle(available_stocks);
		return available_stocks;
	}

	/**
	 * Adds given amounts of randomly selected Stocks into the Data (and thus, into the Simulation).
	 * If total count of added stocks exceeds the count of available stocks, it doesn't add any more Stocks.
	 * @return int containing number of stocks added; -1 if no more stocks can be added
	 */
	public int addStocks(int count) {
		if (next_available_idx >= stocks_available.size()) {
			return -1;
		}
		int added = 0;
		for (int i = 0; i < count; i++) {
			if (next_available_idx >= stocks_available.size()) {
				break;
			}
			Stock next_stock = stocks_available.get(next_available_idx);
			HashMap<String, OHLCV> ohlcv_data;
			try {
				ohlcv_data = readStockCsv(next_stock.getTicker());
			} catch (IOException io) {
				// todo - add best way of handling this
				io.printStackTrace();
				throw new Error("IOException in addStocks");
			} catch (IllegalArgumentException iae) {
				if (iae.getMessage().equals("!=lines_count")) {
					this.next_available_idx++;
					continue;
				} else {
					// todo - add best way of handling this
					iae.printStackTrace();
					throw new Error("IllegalArgumentException in addStocks");
				}
			}
			next_stock.setData_ohlcv(ohlcv_data);
			stocks.put(next_stock.getTicker(), next_stock);
			stocks_al.add(next_stock);
			this.next_available_idx++;
			added++;
		}
		return added;
	}

	/**
	 * Reads csv of stock given by a ticker
	 * @param ticker ticker of the specified stock
	 * @return HashMap mapping the timestamp to a List of corresponding OHLCV's
	 */
	private HashMap<String, OHLCV> readStockCsv(String ticker) throws IllegalArgumentException, IOException {
		List<String> list_of_lines = new ArrayList<>();

		// read the csv by rows into List of Strings
		FileReader fr;
		try {
			fr = new FileReader(stocks_path + ticker + ".csv");
		} catch (FileNotFoundException e) {
			// todo - prompt user to enter path to data
			//		BUT try to make it relative (then it's programming Error)
			e.printStackTrace();
			throw new IllegalArgumentException("Couldn't find the csv associated with " + ticker + " in specified path " + stocks_path);
		}
		try (BufferedReader bf = new BufferedReader(fr)) {
			// check first line for proper formatting
			String line = bf.readLine();
			if (!Objects.equals(line, "Time,Open,High,Close,Low,Volume")) {
				throw new IllegalArgumentException("Wrongly formatted csv passed");
			}
			int lines_count = 0;
			line = bf.readLine();
			while (line != null) {
				list_of_lines.add(line);
				lines_count++;
				line = bf.readLine();
			}
			if (this.duration == -1) {
				this.duration = lines_count;
			} else if (this.duration != lines_count) {
				throw new IllegalArgumentException("!=lines_count");
			}
		} catch (IOException io) {
			io.printStackTrace();
			throw new IOException("IOException while reading csv");
		}

		// create Hashmap<OHLCV>
		HashMap<String, OHLCV> hashMap = new HashMap<>();
		for (String line : list_of_lines) {
			// todo - add checking (step between each timestamp is 1 minute)
			// todo - add checking (all data starts at the same time)
			// todo - add checking (all data ends at the same time)
			String[] values = line.split(",");
			values[0] = values[0].split(" ")[1];
			hashMap.put(values[0], new OHLCV(values[1], values[2], values[3], values[4], values[5]));
		}
		return hashMap;
	}

	/**
	 * Gets the OHLCV object of a given Stock, at given timestamp, directly
	 * @param ticker ticker of the Stock
	 * @param timestamp timestamp as String in format "HH:MM"
	 * @return associated OHLCV
	 */
	public OHLCV getOhlcvAtTimestamp(String ticker, String timestamp) {
		return stocks.get(ticker).getData_ohlcv().get(timestamp);
	}

	/**
	 * Gets the close value of a given Stock, at a given timestamp, directly
	 * @param ticker ticker of the Stock
	 * @param timestamp timestamp as String in format "HH:MM"
	 * @return associated close value
	 */
	public float getCloseAtTimestamp(String ticker, String timestamp) {
		return stocks.get(ticker).getData_ohlcv().get(timestamp).getClose();
	}

	/**
	 * Converts time given as int to String in HH:MM format used as a key in the HashMap
	 * @return time as String in form HH:MM
	 */
	public static String convertTimeIntToString(int time) throws IllegalArgumentException {
		if (time < 0) {
			throw new IllegalArgumentException("negative time");
		}
		int mm = time % 60;
		String mm_str;
		if (mm <= 9) { // 0-9 minutes
			mm_str = "0" + mm;
		} else {
			mm_str = String.valueOf(mm);
		}
		int hh = (int) Math.floor((float) time / 60);
		if (!(hh <= 24)) {
			throw new IllegalArgumentException("more than 24 hours");
		}
		String hh_str;
		if (hh <= 9) {
			hh_str = "0" + hh;
		} else {
			hh_str = String.valueOf(hh);
		}
		return hh_str + ":" + mm_str;
	}

	/**
	 * Converts time given as String HH:MM to int
	 * @return time as int
	 */
	public static int convertTimeStringToInt(String time) throws IllegalArgumentException {
		String[] time_split = time.split(":");
		if (time_split.length != 2) {
			throw new IllegalArgumentException("Wrong time format");
		}
		int hh = Integer.parseInt(time_split[0]);
		int mm = Integer.parseInt(time_split[1]);
		// I know the following could be merged, but this keeps the code more understandable
		if (!(0 <= hh && hh <= 24) || !(0 <= mm && mm <= 59)) {
			throw new IllegalArgumentException(time + "doesn't contain proper time");
		}
		return hh * 60 + mm;
	}

	public HashMap<String, Stock> getStocks() {
		return stocks;
	}

	public int getDuration() {
		return duration;
	}

	public int getStart_time() {
		return start_time;
	}

	public int getEnd_time() {
		return end_time;
	}

	public ArrayList<Stock> getStocks_al() {
		return stocks_al;
	}
}
