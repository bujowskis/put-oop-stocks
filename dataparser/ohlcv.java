package filereader;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ohlcv implements List<ohlcv> 
{
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Float volume;

    public ohlcv(String open, String high, String low, String close, String volume) {
        this.open = Float.parseFloat(open);
        this.high = Float.parseFloat(high);
        this.low = Float.parseFloat(low);
        this.close = Float.parseFloat(close);
        this.volume = Float.parseFloat(volume);
    }

    public Float getOpen() {
        return open;
    }
    public void setOpen(Float open) {
        this.open = open;
    }
    public Float getHigh() {
        return high;
    }
    public void setHigh(Float high) {
        this.high = high;
    }
    public Float getLow() {
        return low;
    }
    public void setLow(Float low) {
        this.low = low;
    }
    public Float getClose() {
        return close;
    }
    public void setClose(Float close) {
        this.close = close;
    }
    public Float getVolume() {
        return volume;
    }
    public void setVolume(Float volume) {
        this.volume = volume;
    }

    @Override
    public boolean add(ohlcv e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void add(int arg0, ohlcv arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean addAll(Collection<? extends ohlcv> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends ohlcv> arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ohlcv get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<ohlcv> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator<ohlcv> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<ohlcv> listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ohlcv remove(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ohlcv set(int arg0, ohlcv arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<ohlcv> subList(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }    
}
