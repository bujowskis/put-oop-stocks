package com.example.stockssimulator.datahandling;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Holds the following values:
 *      Open, High, Low, Close, Volume -> OHLCV, for short
 * of a particular stock, for a given timestamp.
 *
 * Accessing the right row is implemented in DataParser
 */
public class OHLCV implements List<OHLCV> { // todo - better way than implementing List?
    private final Float open;
    private final Float high;
    private final Float low;
    private final Float close;
    private final Float volume;

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

    public Float getOpen() {
        return open;
    }

    public Float getHigh() {
        return high;
    }

    public Float getLow() {
        return low;
    }

    public Float getClose() {
        return close;
    }

    public Float getVolume() {
        return volume;
    }

    // the price of implements List<OHLCV>
    @Override
    public boolean add(OHLCV e) {
        return false;
    }

    @Override
    public void add(int arg0, OHLCV arg1) {}

    @Override
    public boolean addAll(Collection<? extends OHLCV> c) {
        return false;
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends OHLCV> arg1) {
        return false;
    }

    @Override
    public void clear() {}

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public OHLCV get(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<OHLCV> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<OHLCV> listIterator() {
        return null;
    }

    @Override
    public ListIterator<OHLCV> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public OHLCV remove(int index) {
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public OHLCV set(int arg0, OHLCV arg1) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<OHLCV> subList(int arg0, int arg1) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
