package de.htwg.alda.dictionary;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.math.BigInteger;

public class HashDictionary<K, V> implements Dictionary<K, V> {
    private BigInteger size = BigInteger.valueOf(23L);

    @Override
    public V insert(K key, V value) {
//        size = size.add(size).nextProbablePrime();
        return null;
    }

    @Override
    public V search(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Entry<K, V>> action) {
        Dictionary.super.forEach(action);
    }

    @Override
    public Spliterator<Entry<K, V>> spliterator() {
        return Dictionary.super.spliterator();
    }
}
