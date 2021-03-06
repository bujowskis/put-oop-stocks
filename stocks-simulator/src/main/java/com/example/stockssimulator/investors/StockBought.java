package com.example.stockssimulator.investors;

import com.example.stockssimulator.datahandling.Stock;

/**
 * A single order of some specific stock, that an Investor owns
 *
 * The Investor buys stocks as StockBought objects, specifying the
 */
public class StockBought {
    // surely final
    private final Stock stock;
    private final float initial_value;
    private final int initial_time;
    // (at least for now) left non-final, may be altered during simulation
    private float gain_bound;
    private float loss_bound;
    private int wait_time;
    private final float volume;

    public StockBought(Stock stock, float initial_value, int initial_time, float gain_bound, float loss_bound,
                       int wait_time, float volume) {
        if (stock == null) {
            throw new Error("stock=null");
        }
        this.stock = stock;
        this.initial_value = initial_value; // todo - could be accessed from stock directly
        this.initial_time = initial_time;
        this.gain_bound = gain_bound;
        this.loss_bound = loss_bound;
        this.wait_time = wait_time;
        this.volume = volume;
    }

    /**
     * Checks if the upper bound value specified for this StockBought was reached;
     * i.e. if this StockBought already made the gain threshold specified by the Investor
     *
     * @param current_val current value of the stock todo - make StockBought access it itself
     * @return true if upper bound reached, false otherwise
     */
    public boolean checkUpper(float current_val) {
        return current_val - initial_value >= initial_value * gain_bound;
    }

    /**
     * Checks if the lower bound value specified for this StockBought was reached;
     * i.e. if this StockBought already faced the loss threshold specified by the Investor
     *
     * @param current_val current value of the stock todo - make StockBought access it itself
     * @return true if upper bound reached, false otherwise
     */
    public boolean checkLower(float current_val) {
        return current_val - initial_value <= - initial_value * loss_bound;
    }

    /**
     * Checks if the wait time specified for this StockBought was reached;
     * i.e. if the Investor kept th StockBought was owned by the Investor for at least as long as they
     *
     * @param current_time current time of the simulation todo - make StockBought access it itself
     * @return true if waiting time passed,
     */
    public boolean checkWaitTime(int current_time) {
        return current_time - initial_time >= wait_time;
    }

    public float getInitial_value() {
        return initial_value;
    }

    public int getInitial_time() {
        return initial_time;
    }

    public float getGain_bound() {
        return gain_bound;
    }

    public void setGain_bound(float gain_bound) {
        this.gain_bound = gain_bound;
    }

    public float getLoss_bound() {
        return loss_bound;
    }

    public void setLoss_bound(float loss_bound) {
        this.loss_bound = loss_bound;
    }

    public int getWait_time() {
        return wait_time;
    }

    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }

    public float getVolume() {
        return volume;
    }

    public Stock getStock() {
        return stock;
    }
}
