package com.example.stockssimulator.investors;

import com.example.stockssimulator.MainScreen;

public class NormalInvestor extends Investor {
    /**
     * Creates a NormalInvestor. This Investor:
     * - sets the gain interval between 0.5 and 1.5%
     * - sets the loss interval between 0.4 and 0.8%
     * - sets 15-30 minutes as max waiting time before selling stock regardless of the above
     *  @param mainScreen reference to the simulation instance
     */
    public NormalInvestor(MainScreen mainScreen) {
        // todo - adjust values, update javadoc if changed
        super(mainScreen, 15, 5, 8, 4, 30, 15);
    }
}
