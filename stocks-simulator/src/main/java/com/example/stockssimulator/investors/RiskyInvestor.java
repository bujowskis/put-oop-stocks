package com.example.stockssimulator.investors;

import com.example.stockssimulator.MainScreen;

public class RiskyInvestor extends Investor {
    /**
     * Creates a RiskyInvestor. This Investor:
     * - sets the gain interval between 0.8 and 2.0%
     * - sets the loss interval between 0.6 and 1.0%
     * - sets 20-35 minutes as max waiting time before selling stock regardless of the above
     *  @param mainScreen reference to the simulation instance
     */
    public RiskyInvestor(MainScreen mainScreen) {
        // todo - adjust values, update javadoc if changed
        super(mainScreen, 20, 8, 10, 6, 35, 20);
    }
}
