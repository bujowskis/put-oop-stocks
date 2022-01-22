package com.example.stockssimulator.investors;

import com.example.stockssimulator.Simulation;

public class NormalInvestor extends Investor {
    private static int number = 0;
    /**
     * Creates a NormalInvestor. This Investor:
     * - sets the gain interval between 0.5 and 1.5%
     * - sets the loss interval between 0.4 and 0.8%
     * - sets 15-30 minutes as max waiting time before selling stock regardless of the above
     *  @param simulation reference to the simulation instance
     */
    public NormalInvestor(Simulation simulation) {
        // todo - adjust values, update javadoc if changed
        super(simulation, "Normal-" + number, 0.0f, 15, 5, 8, 4, 30, 15);
        number++;
    }
}
