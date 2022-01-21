package com.example.stockssimulator.datahandling;

import java.util.HashMap;
import java.util.List;

/**
 * Holds all the necessary info about a particular stock
 */
public class Stock {
    private String ticker;
    private String company_name;
    private String sector_name;
    private String industry_name;
    private String country_name;
    // todo - consider adding the rest, but seems unnecessary
    private HashMap<String, List<OHLCV>> data_ohlcv;

    public Stock(String ticker, String company_name, String sector_name, String industry_name, String country_name,
                 HashMap<String, List<OHLCV>> data_ohlcv) {
        // todo
    }
}
