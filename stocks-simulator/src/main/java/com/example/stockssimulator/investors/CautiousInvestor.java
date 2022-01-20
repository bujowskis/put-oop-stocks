package com.example.stockssimulator.investors;

import com.example.stockssimulator.Simulation;

public class CautiousInvestor extends Investor {
    /**
     * Creates a CautiousInvestor. This Investor:
     * - sets the gain interval between 0.3 and 0.7%
     * - sets the loss interval between 0.2 and 0.4%
     * - sets 10-20 minutes as max waiting time before selling stock regardless of the above
     *  @param simulation reference to the simulation instance
     */
    public CautiousInvestor(Simulation simulation) {
        // todo - adjust values, update javadoc if changed
        super(simulation, 7, 3, 4, 2, 20, 10);
    }
}
