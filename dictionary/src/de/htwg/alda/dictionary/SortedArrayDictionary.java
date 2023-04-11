package de.htwg.alda.dictionary;

import java.util.*;
import java.util.function.Consumer;

public class SortedArrayDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {
    private static final int INITIAL_CAP = 16;

    private int size;
    private Entry<K, V>[] data;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        this.size = 0;
        this.data = new Entry[INITIAL_CAP];
        this.modCount++;
    }

    @Override
    public V insert(K key, V value) {
        int i = searchIndex(key);

        // has entry
        if (i >= 0 && data[i].getKey().compareTo(key) == 0) {
            return data[i].setValue(value);
        }

        if (data.length == size) {
            data = Arrays.copyOf(data, size * 2);
        }

        int j;
        for (j = size - 1; j >= 0 && key.compareTo(data[j].getKey()) < 0; j--) {
            data[j + 1] = data[j];
        }

        data[j+1] = new Entry<>(key, value);
        size++;
        this.modCount++;
        return null;
    }

    private int searchIndex(K key) {
        int li = 0;
        int re = size - 1;

        while (re >= li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].getKey()) == 0) {
                return m;
            } else if (key.compareTo(data[m].getKey()) < 0) {
                re = m - 1;
            } else {
                li = m + 1;
            }
        }

        return -1;
    }

    @Override
    public V search(K key) {
        int i = searchIndex(key);
        return i < 0 ? null : data[i].getValue();
    }

    @Override
    public V remove(K key) {
        int i = searchIndex(key);
        if (i < 0) {
            return null;
        }

        V val = data[i].getValue();
        for (int j = i; j < size; j++) {
            data[j] = data[j+1];
        }
        data[--size] = null;
        this.modCount++;
        return val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int i = 0;
            private final int mc = modCount;

            @Override
            public boolean hasNext() {
                if (mc != modCount) throw new ConcurrentModificationException();
                return i < size;
            }

            @Override
            public Entry<K, V> next() {
                if (mc != modCount) throw new ConcurrentModificationException();
                if (i >= size) throw new NoSuchElementException();
                return data[i++];
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Entry<K, V>> action) {
        for (var e : this) {
            action.accept(e);
        }
    }

    @Override
    public Spliterator<Entry<K, V>> spliterator() {
        return Dictionary.super.spliterator();
    }
}
