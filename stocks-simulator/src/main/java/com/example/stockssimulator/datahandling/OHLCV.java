package com.example.stockssimulator.datahandling;

/**
 * Holds the following values:
 *      Open, High, Low, Close, Volume -> OHLCV, for short
 * of a particular stock, for a given timestamp.
 *
 * Accessing the right row is implemented in DataParser
 */
public class OHLCV {
    private final float open;
    private final float high;
    private final float low;
    private final float close;
    private final float volume;

    /**
     * Creates OHLCV instance by converting and storing values provided as Strings
     */
    public OHLCV(String open, String high, String low, String close, String volume) {
        this.open = Float.parseFloat(open);
        this.high = Float.parseFloat(high);
        this.low = Float.parseFloat(low);
        this.close = Float.parseFloat(close);
        this.volume = Float.parseFloat(volume);
    }

    public float getOpen() {
        return open;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getClose() {
        return close;
    }

    public float getVolume() {
        return volume;
    }

}
