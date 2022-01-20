package com.example.stockssimulator.investors;

import com.example.stockssimulator.MainScreen;
import com.example.stockssimulator.StockBought;

import java.util.ArrayList;

/** todo - format
 * Specifies what's common for all types of investors.
 * - risks are given in per mills and specify the ranges of risks that are going to be randomized when the Investor
 * buys a given stock
 * - wait times are given in minutes and specify how long the Investor is willing to wait before selling a stock regardless
 * of whether risk_gain or risk_lower is reached, or even if they faced loss or gained profit
 *
 * It works in the following way, let's say:
 * - risk_gain interval is [5, 15], and randomized risk_gain = 10
 * - risk_loss interval is [2, 10], and randomized risk_loss = 5
 * - wait_time interval is [8, 30], and randomized wait_time = 16
 *
 * Then the Investor is going to sell the stock if:
 * - at some point, they gained at least 1% on that stock
 * - at some point, they lost at least 0.5% on that stock
 * - they waited for 16 minutes, and they neither reached desired gain nor maximum accepted loss
 */
public abstract class Investor {
    private MainScreen mainScreen;
    private int risk_gain_upper;
    private int risk_gain_lower;
    private int risk_loss_upper;
    private int risk_loss_lower;
    private int wait_time_upper;
    private int wait_time_lower;
    private ArrayList<StockBought> stockBoughtAL = new ArrayList<StockBought>();

    /** todo - format
     * Creates an Investor;
     * - risk gains are specified in per mills and must be greater than 0
     * - wait times are specified in minutes and must be greater than 0
     * - upper bounds must be greater or equal lower bounds
     *
     * @param mainScreen reference to the simulation instance
     * @param risk_gain_upper upper bound for the randomized risk gain, at which the given stock will be sold
     * @param risk_gain_lower lower --||--
     * @param risk_loss_upper upper bound for the randomized risk loss, at which the given stock will be sold
     * @param risk_loss_lower lower --||--
     * @param wait_time_upper upper bound for the wait time, at which the given stock will be sold
     * @param wait_time_lower lower --||--
     */
    public Investor(MainScreen mainScreen, int risk_gain_upper, int risk_gain_lower, int risk_loss_upper,
                    int risk_loss_lower, int wait_time_upper, int wait_time_lower) {
        if (mainScreen == null) {
            throw new Error("No reference to simulation");
        }
        if (risk_gain_upper <= 0 || risk_gain_lower <= 0 || risk_loss_upper <= 0 || risk_loss_lower <= 0 ||
                wait_time_upper <= 0 || wait_time_lower <= 0) {
            throw new IllegalArgumentException("<=0");
        }
        if (risk_gain_upper < risk_gain_lower || risk_loss_upper < risk_loss_lower ||
                wait_time_upper < wait_time_lower) {
            throw new IllegalArgumentException("upper<lower");
        }
        this.risk_gain_upper = risk_gain_upper;
        this.risk_gain_lower = risk_gain_lower;
        this.risk_loss_upper = risk_loss_upper;
        this.risk_loss_lower = risk_loss_lower;
        this.wait_time_upper = wait_time_upper;
        this.wait_time_lower = wait_time_lower;
    }

    /**
     * Runs the investor; i.e. starts its trading process
     */
    public void run() {
        if (mainScreen.getSimulation() == null || !mainScreen.getSimulation().isRunning()) {
            throw new Error("Cannot run investor, when simulation is not started or uninitialized");
        }
        // todo
    }

    /**
     * Buys a randomly chosen stock
     */
    private void buyRandomStock() {
        // todo
    }

    /**
     * Checks the owned StocksBought
     */
    private void checkStocksBought() {
        // todo
    }

    public ArrayList<StockBought> getStockBoughtAL() {
        return stockBoughtAL;
    }
}
