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
	// checking the data
	private String start_time = null; // todo - add checking
	private int duration = -1;
	private String end_time = null; /// todo - add checking
	private final String path = ""; // todo - add getting relative path OR prompt user to specify where data is

	public Data() {
		// todo - initialize String ArrayList with all available stocks Tickers to randomly choose from
		//  (maybe by shuffling the indices, and returning next one for each Stock?)
	}

	/**
	 * Adds given amounts of randomly selected Stocks into the Data (and thus, into the Simulation)
	 */
	public void addRandomStocks(int count) {
		// todo
	}

	/**
	 * Reads csv of stock given by a ticker
	 * @param ticker ticker of the specified stock
	 * @return HashMap mapping the timestamp to a List of corresponding OHLCV's
	 */
	public HashMap<String, List<OHLCV>> readStockCsv(String ticker) throws IllegalArgumentException, IOException {
		List<String> list_of_lines = new ArrayList<>();

		// read the csv by rows into List of Strings
		FileReader fr;
		try {
			fr = new FileReader(path + ticker + ".csv");
		} catch (FileNotFoundException e) {
			// todo - prompt user to enter path to data
			//		BUT try to make it relative (then it's programming Error)
			e.printStackTrace();
			throw new IllegalArgumentException("Couldn't find the csv associated with " + ticker + " in specified path " + path);
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
				throw new IllegalArgumentException("Not the same count of rows across all used csv's");
			}
		} catch (IOException io) {
			io.printStackTrace();
			throw new IOException("IOException while reading csv");
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
		return hh + ":" + mm_str;
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
}
