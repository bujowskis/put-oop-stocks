package com.example.stockssimulator.investors;

import com.example.stockssimulator.Simulation;

public class CrazyInvestor extends Investor {
    private static int number = 0;
    /**
     * Creates a CrazyInvestor. This Investor:
     * - sets the gain interval between 2.0 and 3.0%
     * - sets the loss interval between 1.5 and 2.0%
     * - sets 20-35 minutes as max waiting time before selling stock regardless of the above
     *  @param simulation reference to the simulation instance
     */
    public CrazyInvestor(Simulation simulation) {
        // todo - adjust values, update javadoc if changed
        super(simulation, "Crazy-" + number, 30, 20, 20, 15, 35, 20);
        number++;
    }
}
