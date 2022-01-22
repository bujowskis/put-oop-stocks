package com.example.stockssimulator.investors;

import com.example.stockssimulator.Simulation;
import com.example.stockssimulator.datahandling.Data;
import com.example.stockssimulator.datahandling.OHLCV;
import com.example.stockssimulator.datahandling.Stock;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.Random;

/**
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
    private final Simulation simulation;
    private final String nickname;
    private float money;
    private final float initial_money;
    private final int risk_gain_upper;
    private final int risk_gain_lower;
    private final int gain_additional;
    private final int risk_loss_upper;
    private final int risk_loss_lower;
    private final int loss_additional;
    private final int wait_time_upper;
    private final int wait_time_lower;
    private final int wait_time_additional;
    // skipping unnecessary stock checking
    private int last_checked_time = -1;
    private static final Random random = new Random();
    private TradeOnThread tradeOnThread;

    private ArrayList<StockBought> stockBoughtAL = new ArrayList<>();

    /**
     * Creates an Investor;
     * - risk gains are specified in per mills and must be greater than 0
     * - wait times are specified in minutes and must be greater than 0
     * - upper bounds must be greater or equal lower bounds
     *
     * @param simulation reference to the simulation instance
     * @param nickname nickname to differentiate the Investor
     * @param money initial amount of money; if 0, 10,000 is assigned
     * @param risk_gain_upper upper bound for the randomized risk gain, at which the given stock will be sold
     * @param risk_gain_lower lower --||--
     * @param risk_loss_upper upper bound for the randomized risk loss, at which the given stock will be sold
     * @param risk_loss_lower lower --||--
     * @param wait_time_upper upper bound for the wait time, at which the given stock will be sold
     * @param wait_time_lower lower --||--
     */
    public Investor(Simulation simulation, String nickname, float money, int risk_gain_upper, int risk_gain_lower,
                    int risk_loss_upper, int risk_loss_lower, int wait_time_upper, int wait_time_lower) {
        if (simulation == null) {
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
        if (money < 0) {
            throw new IllegalArgumentException("money<0");
        }
        if (nickname == null) {
            this.nickname = "default-" + simulation.getDefault_investor_number();
            simulation.add1ToDefaultInvestorNumber();
        } else {
            this.nickname = nickname;
        }
        this.simulation = simulation;
        if (money == 0) {
            this.money = 10000f;
        } else {
            this.money = money;
        }
        this.initial_money = this.money;
        this.risk_gain_upper = risk_gain_upper;
        this.risk_gain_lower = risk_gain_lower;
        this.gain_additional = risk_gain_upper - risk_gain_lower + 1;
        this.risk_loss_upper = risk_loss_upper;
        this.risk_loss_lower = risk_loss_lower;
        this.loss_additional = risk_loss_upper - risk_loss_lower + 1;
        this.wait_time_upper = wait_time_upper;
        this.wait_time_lower = wait_time_lower;
        this.wait_time_additional = wait_time_upper - wait_time_lower + 1;
    }

    /**
     * Runs the investor; i.e. starts its trading process on a separate thread
     */
    public void startTrading() {
        if (simulation == null || !simulation.isRunning()) {
            throw new Error("Cannot startTrading investor, when simulation is not started or uninitialized");
        }
        tradeOnThread = new TradeOnThread();
        tradeOnThread.start();
    }

    /**
     * Handles the trading process on a separate thread
     */
    public class TradeOnThread extends Thread {
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                if (simulation.isFinished()) {
                    // sell all stocks, wrap things up
                    for (StockBought stock_bought : stockBoughtAL) {
                        sellStock(stock_bought);
                    }
                } else {
                    // continue with new time_rate
                    run();
                }
            }
            while (true) {
                checkStocksBought();
                tryBuyRandomStock();
                System.out.println(nickname + ":\n" + money + "\n" + stockBoughtAL.toString());
                try {
                    sleep((long) 60000 / simulation.getTime_rate() * (1 + random.nextInt(5)));
                } catch (InterruptedException e) {
                    if (simulation.isFinished()) {
                        // sell all stocks, wrap things up
                        // fixme - could be optimized
                        while (!stockBoughtAL.isEmpty()) {
                            sellStock(stockBoughtAL.get(stockBoughtAL.size() - 1));
                        }
                        System.out.println(nickname + " - money at end: " + money);
                        this.stop(); // todo - better way to do that
                    } else {
                        // continue with new time_rate
                        run();
                    }
                }
                System.out.println("\t(loop completed)");
            }
        }
    }

    /**
     * Tries to buy a randomly chosen stock
     * It's possible more than one stock will be bought, depending on how much money Investor has
     */
    private void tryBuyRandomStock() {
        Data data = this.simulation.getData();
        Stock stock_chosen = data.getStocks_al().get(random.nextInt(data.getStocks_al().size()));
        // todo - adjust the behavior
        if (money < 200) {
            // hold on with buying - leave some money to survive loss
            return;
        } else if (money < 500) {
            // near the last ditch attempt - try to desperately make profit
            buyStock(stock_chosen, money * (0.4f + (random.nextFloat() / 2)));
        } else if (money < 2000) {
            // still got some money to come by, but play it safer
            buyStock(stock_chosen, money * (0.2f + 2 * (random.nextFloat() / 5)));
        } else if (money < 5000) {
            // "normal" amount of money, "normal" buying
            buyStock(stock_chosen, money * (0.2f + 2 * (random.nextFloat() / 5)));
            if (random.nextBoolean()) { // 1/2 chance
                tryBuyRandomStock();
            }
        } else { // money > 5000
            // quite lots of money, try to invest more
            buyStock(stock_chosen, money * (0.2f + 2 * (random.nextFloat() / 5)));
            if (random.nextInt(4) != 3) { // 3/4 chance
                tryBuyRandomStock();
            }
        }
    }

    /**
     * Buys the given stock using given amount of money
     */
    private void buyStock(Stock stock, float amount) {
        if (money - amount < 0) {
            throw new Error("tried to buy with insufficient money");
        }
        float current_price = stock.getCloseAtTime(simulation.getTime_string());
        float volume = amount / stock.getCloseAtTime(simulation.getTime_string());
        money -= amount;
        stockBoughtAL.add(new StockBought(stock, current_price, simulation.getTime(), randomizeGain(), randomizeLoss(),
                randomizeWaitTime(), volume));
    }

    /**
     * Sells the given stock
     */
    private void sellStock(StockBought stock) {
        System.out.println("\t(sold stock " + stock.getStock().getTicker());
        boolean removed = this.stockBoughtAL.remove(stock);
        if (!removed) {
            // could be handled, but I try to be strict and make sure everything works as intended
            throw new Error("Tried to sell stock, which doesn't belong to this Investor");
        }
        this.money += stock.getVolume() * this.simulation.getData().getCloseAtTimestamp(
                stock.getStock().getTicker(), this.simulation.getTime_string());
    }

    /**
     * Checks the owned StocksBought
     */
    private void checkStocksBought() {
        // check only if time changed
        if (this.simulation.getTime() == this.last_checked_time) {
            return;
        }
        ArrayList<StockBought> to_sell = new ArrayList<>();
        for (StockBought stock_bought : this.stockBoughtAL) {
            OHLCV ohlcv = this.simulation.getData().getOhlcvAtTimestamp(stock_bought.getStock().getTicker(),
                    this.simulation.getTime_string());
            if (stock_bought.checkUpper(ohlcv.getClose())
                || stock_bought.checkLower(ohlcv.getClose())
                || stock_bought.checkWaitTime(this.simulation.getTime())) {
                to_sell.add(stock_bought);
            }
        }
        for (StockBought stockBought : to_sell) {
            sellStock(stockBought);
        }
        this.last_checked_time = this.simulation.getTime();
    }

    private float randomizeGain() {
        return (float) (risk_gain_lower + random.nextInt(gain_additional)) / 1000;
    }

    private float randomizeLoss() {
        return (float) (risk_loss_lower + random.nextInt(loss_additional)) / 1000;
    }

    private int randomizeWaitTime() {
        return wait_time_lower + random.nextInt(wait_time_additional);
    }

    public ArrayList<StockBought> getStockBoughtAL() {
        return stockBoughtAL;
    }

    public String getNickname() {
        return nickname;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public TradeOnThread getTradeOnThread() {
        return tradeOnThread;
    }
}
