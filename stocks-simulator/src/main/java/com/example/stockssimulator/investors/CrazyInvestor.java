package com.example.stockssimulator.investors;

import com.example.stockssimulator.MainScreen;

public class CrazyInvestor extends Investor {
    /**
     * Creates a CrazyInvestor. This Investor:
     * - sets the gain interval between 2.0 and 3.0%
     * - sets the loss interval between 1.5 and 2.0%
     * - sets 20-35 minutes as max waiting time before selling stock regardless of the above
     *  @param mainScreen reference to the simulation instance
     */
    public CrazyInvestor(MainScreen mainScreen) {
        // todo - adjust values, update javadoc if changed
        super(mainScreen, 30, 20, 20, 15, 35, 20);
    }
}
