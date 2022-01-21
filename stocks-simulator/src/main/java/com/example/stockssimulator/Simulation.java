package com.example.stockssimulator;

import com.example.stockssimulator.datahandling.Data;
import com.example.stockssimulator.investors.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

/**
 * Contains all the functionalities regards the Simulation.
 * Keeps the MainScreen clean and separated from "the backend".
 */
public class Simulation {
    private MainScreen mainScreen;
    private boolean running = false;
    private boolean finished = false;
    // private boolean paused = false; // todo - consider pause option
    private final ArrayList<Investor> investors;
    private final float[] default_investors_proportions = {0.2f, 0.5f, 0.2f, 0.1f}; // todo - adjust (see javadoc below)
    private int default_investor_number = 0;
    private int time;
    private String time_string; // todo
    private int end_time;
    private int time_rate = 1;
    private final Data data;

    /**
     * Creates a new instance of Simulation.
     * Default proportions for types of Investors is as follows: (0.2 Cautious, 0.5 Normal, 0.2 Risky, 0.1 Crazy) todo - adjust (see final float[] above)
     * @param stocks_no number of stocks to be included in the simulation
     * @param investors_no (optional) number of investors to be included in the simulation
     *                     - unspecified - both investors number and number of Investors types deduced based on number of stocks
     *                     - single int - investors number given, but number of Investors types deduced based on default proportions
     *                     - four ints given, number of particular Investors types given (Cautious, Normal, Risky, Crazy)
     */
    public Simulation(MainScreen mainScreen, int stocks_no, int... investors_no) throws IllegalArgumentException {
        // check the inputs
        if (mainScreen == null) {
            throw new Error("mainScreen=null");
        }
        if (stocks_no < 1) { // todo - add upper limit based on csv
            throw new IllegalArgumentException("stocks_no<1");
        }
        try {
            // todo - pass relative / user-defined path
            this.data = new Data(this, "stocks-simulator/src/main/resources/data/",
                    "stocks-simulator/src/main/resources/");
        } catch (IOException e) {
            // todo - add best handling
            e.printStackTrace();
            throw new Error("IOException - didn't initialize Data");
        } catch (IllegalArgumentException iae) {
            // todo - add best handling
            throw new Error("IllegalArgumentException - didn't initialize Data");
        }
        boolean overflow = this.data.addStocks(stocks_no);
        if (overflow) {
            // todo - consider doing something here
        }
        stocks_no = this.data.getStocks().size();
        this.time = this.data.getStart_time();
        this.time_string = Data.convertTimeIntToString(this.time);
        this.end_time = this.data.getEnd_time();

        int[] investors_numbers = new int[4];
        switch (investors_no.length) {
            case 0 -> {
                // deduce both number and types
                int investors_total = (int) Math.ceil((float) stocks_no / 5); // todo - adjust mapping from stocks_no
                int investors_left = investors_total;
                // distribute according to default (smaller counts first, then assign rest to Normal)
                investors_numbers[0] = Math.round((float) investors_total * default_investors_proportions[0]);
                investors_left -= investors_numbers[0];
                investors_numbers[2] = Math.round((float) investors_total * default_investors_proportions[2]);
                investors_left -= investors_numbers[2];
                investors_numbers[3] = Math.round((float) investors_total * default_investors_proportions[3]);
                investors_left -= investors_numbers[3];
                if (investors_left < 1) {
                    throw new IllegalArgumentException("investors_left<1");
                }
                investors_numbers[1] = investors_left;
            }
            case 1 -> {
                // deduce only types
                if (investors_no[0] < 1) {
                    throw new IllegalArgumentException("investors_no<1");
                }
                // investors_total = investors_no[0]
                int investors_left = investors_no[0];
                // distribute according to default (smaller counts first, then assign rest to Normal)
                investors_numbers[0] = Math.round((float) investors_no[0] * default_investors_proportions[0]);
                investors_left -= investors_numbers[0];
                investors_numbers[2] = Math.round((float) investors_no[0] * default_investors_proportions[2]);
                investors_left -= investors_numbers[2];
                investors_numbers[3] = Math.round((float) investors_no[0] * default_investors_proportions[3]);
                investors_left -= investors_numbers[3];
                if (investors_left < 1) {
                    throw new IllegalArgumentException("investors_left<1");
                }
                investors_numbers[1] = investors_left;
            }
            case 4 -> {
                // number of particular Investors given
                int investors_no_sum = Arrays.stream(investors_no).sum();
                if (investors_no_sum < 1) {
                    throw new IllegalArgumentException("investors_no_sum<1");
                }
                for (int i = 0; i < investors_no.length; i++) {
                    if (investors_no[i] < 0) {
                        throw new IllegalArgumentException("some_investors_no<0");
                    } else {
                        investors_numbers[i] = investors_no[i];
                    }
                }
            }
            default -> throw new IllegalArgumentException("investors_no.length-unsupported");
        }
        // create ArrayList of Investors, according to investors_numbers
        this.mainScreen = mainScreen;
        ArrayList<Investor> investors = new ArrayList<>();
        while (investors_numbers[0]-- > 0) {
            investors.add(new CautiousInvestor(mainScreen.getSimulation()));
        }
        while (investors_numbers[1]-- > 0) {
            investors.add(new NormalInvestor(mainScreen.getSimulation()));
        }
        while (investors_numbers[2]-- > 0) {
            investors.add(new RiskyInvestor(mainScreen.getSimulation()));
        }
        while (investors_numbers[3]-- > 0) {
            investors.add(new CrazyInvestor(mainScreen.getSimulation()));
        }
        this.investors = investors;
    }

    /**
     * Starts the simulation process
     */
    public void start() {
        // todo
    }

    public boolean isRunning() {
        return this.running;
    }

    public ArrayList<Investor> getInvestors() {
        return investors;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public int getTime_rate() {
        return time_rate;
    }

    public void setTime_rate(int time_rate) {
        if (time_rate < 1) {
            throw new Error("lowest time_rate is 1min/1min");
        }
        this.time_rate = time_rate;
    }

    public Data getData() {
        return data;
    }

    public int getTime() {
        return time;
    }

    public int getDefault_investor_number() {
        return default_investor_number;
    }

    public void add1ToDefaultInvestorNumber() {
        default_investor_number++;
    }

    public String getTime_string() {
        return time_string;
    }
}
