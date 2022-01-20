package com.example.stockssimulator.datahandling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class containing the data relevant for the simulation, and providing functionalities how to obtain it.
 */
public class Data {
	private HashMap<String, HashMap<String, List<OHLCV>>> stocks_data;
	private String start_time = null; // todo - add checking
	private int duration = -1;
	private String end_time = null; /// todo - add checking
	private final String path = ""; // todo - add relative path

	public Data() {
		// todo - initialize String ArrayList with all available stocks Tickers to randomly choose from
		//  (maybe by shuffling the indices, and returning next one for each Stock?)
	}

	/**
	 * Adds given amounts of randomly selected Stocks into
	 */
	public void addRandomStocks(int count) {

	}

	/**
	 * Reads csv of stock given by a ticker
	 * @param ticker ticker of the specified stock
	 * @return HashMap mapping the timestamp to a List of corresponding OHLCV's
	 */
	public HashMap<String, List<OHLCV>> readStockCsv(String ticker) {
		List<String> list_of_lines = new ArrayList<>();

		// read the csv by rows into List of Strings
		FileReader fr;
		try {
			fr = new FileReader(path + ticker + ".csv");
		} catch (FileNotFoundException e) {
			// todo - prompt user to enter path to data
			//		BUT try to make it relative (then it's programming Error)
			e.printStackTrace();
			throw new Error("Couldn't find the csv associated with " + ticker + " in specified path " + path);
		}
		try (BufferedReader bf = new BufferedReader(fr)) {
			// check first line for proper formatting
			String line = bf.readLine();
			if (!Objects.equals(line, "Time,Open,High,Close,Low,Volume")) {
				throw new Error("Wrongly formatted csv passed");
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
				throw new Error("Not the same count of rows across all used csv's");
			}
		} catch (IOException io) {
			io.printStackTrace();
			throw new Error("IOException while reading csv");
		}

		// create Hashmap<OHLCV>
		HashMap<String, List<OHLCV>> hashMap = new HashMap<>();
		for (String line : list_of_lines) {
			// todo - could add checking if the times are ordered by 1, start and end at the same times, etc.
			String[] values = line.split(",");
			OHLCV ohlcv = new OHLCV(values[1], values[2], values[3], values[4], values[5]);
			values[0] = values[0].split(" ")[1];
			hashMap.put(values[0], ohlcv);
		}
		return hashMap;
	}
}
