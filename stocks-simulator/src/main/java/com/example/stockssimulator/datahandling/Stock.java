package com.example.stockssimulator.datahandling;

import java.util.HashMap;
import java.util.List;

/**
 * Holds all the necessary info about a particular stock
 */
public class Stock {
    private final String ticker;
    private final String company_name;
    private final String sector_name;
    private final String industry_name;
    private final String country_name;
    private HashMap<String, OHLCV> data_ohlcv = null; // todo - is there a way to make this final, but uninitialized until Data.addStocks() ?
    private boolean data_set = false; // workaround for "final, but uninitialized for now"

    /**
     * Creates instance of Stock WITHOUT ITS OHLCV DATA;
     * The data is added later on, once the Stock is added to the Simulation in Data.addStocks()
     * @param ticker ticker of the Stock
     * @param company_name full name of the company
     * @param sector_name name of the sector company belongs to
     * @param industry_name name of the industry company belongs to
     * @param country_name name of the country where the company is registered
     */
    public Stock(String ticker, String company_name, String sector_name, String industry_name, String country_name) {
        this.ticker = ticker;
        this.company_name = company_name;
        this.sector_name = sector_name;
        this.industry_name = industry_name;
        this.country_name = country_name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getSector_name() {
        return sector_name;
    }

    public String getIndustry_name() {
        return industry_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public HashMap<String, OHLCV> getData_ohlcv() throws NullPointerException {
        if (data_ohlcv == null) {
            throw new NullPointerException();
        }
        return data_ohlcv;
    }

    /**
     * DISCLAIMER - USE WITH CAUTION
     * The data must not be changed after set once. If tried, throws Error.
     * @param data_ohlcv data to be assigned
     */
    public void setData_ohlcv(HashMap<String, OHLCV> data_ohlcv) {
        if (data_set) {
            throw new Error("tried to set data_ohlcv again");
        }
        this.data_ohlcv = data_ohlcv;
        data_set = true;
    }
}
