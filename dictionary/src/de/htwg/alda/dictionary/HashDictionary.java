package de.htwg.alda.dictionary;

import java.math.BigInteger;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class HashDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {
    private static final int LOAD_FACTOR = 2;

    private LinkedList<Entry<K, V>>[] data;
    private int size;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public HashDictionary(int capacity) {
        this.data = new LinkedList[capacity];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V insert(K key, V value) {
        int index = hash(key);
        LinkedList<Entry<K, V>> list = data[index];

        if (list == null) list = data[index] = new LinkedList<>();

        for (var e : list) {
            if (e.getKey().equals(key)) {
                return e.setValue(value);
            }
        }

        if (size >= data.length * LOAD_FACTOR) {
            var newData = new LinkedList[BigInteger.valueOf(data.length * 2L).nextProbablePrime().intValue()];
            copyTo(newData);
            data = newData;
        }

        list.push(new Entry<>(key, value));
        size++;
        modCount++;

        return null;
    }

    private void copyTo(LinkedList<Entry<K, V>>[] dest) {
        for (var e : this) {
            insert(e, dest);
        }
    }

    private void insert(Entry<K, V> entry, LinkedList<Entry<K, V>>[] dest) {
        int index = hash(entry.getKey(), dest.length);
        LinkedList<Entry<K, V>> list = dest[index];
        if (list == null) list = dest[index] = new LinkedList<>();
        list.push(entry);
    }


    @Override
    public V search(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> list = data[index];
        if (list == null || list.isEmpty()) return null;

        for (Entry<K, V> e : list) {
            if (e.getKey().compareTo(key) == 0) return e.getValue();
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> list = data[index];
        if (list == null || list.isEmpty()) return null;

        Entry<K, V> removed = list.pop((entry) -> entry.getKey().equals(key));
        if (removed == null) return null;
        else {
            size--;
            modCount++;
            return removed.getValue();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int index = 0;
            private Iterator<Entry<K, V>> linkedListIt;

            private void nextIterator() {
                if (linkedListIt == null || !linkedListIt.hasNext()) {
                    linkedListIt = null;
                    while (index < data.length) {
                        if (data[index] != null) {
                            linkedListIt = data[index].iterator();
                            index++;
                            break;
                        }
                        index++;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                nextIterator();
                return linkedListIt != null && linkedListIt.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                if (modCount != expectedModCount) throw new ConcurrentModificationException();
                if (!hasNext()) throw new NoSuchElementException();
                return linkedListIt.next();
            }
        };
    }

    private int hash(K key) {
        int index = key.hashCode();
        if (index < 0) index = -index;
        index = index % data.length;

        return index;
    }

    private static <K> int hash(K key, int m) {
        int index = key.hashCode();
        if (index < 0) index = -index;
        index = index % m;

        return index;
    }

    private static class LinkedList<T> implements Iterable<T> {
        private Node<T> first;
        private Node<T> last;
        private int size;
        private int modCount = 0;

        public LinkedList() {
            clear();
        }

        public void clear() {
            this.first = null;
            this.last = null;
            size = 0;
            modCount++;
        }

        public void push(T value) {
            if (this.isEmpty()) {
                this.last = this.first = new Node<>(null, null, value);
            } else {
                this.last.next = new Node<>(this.last, null, value);
                this.last = this.last.next;
            }

            size++;
            modCount++;
        }

        public T pop(Predicate<T> condition) {
            if (isEmpty()) return null;
            Node<T> current = first;
            do {
                if (condition.test(current.value)) return removeNode(current);
                current = current.next;
            } while(current.hasNext());

            return null;
        }

        private T removeNode(Node<T> node) {
            if (node.prev != null) {
                node.prev.next = node.next;
            }

            if (node.next != null) {
                node.next.prev = node.prev;
            }

            node.next = node.prev = null;
            size--;
            modCount++;
            return node.value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append('(');
            for (T val : this) {
                builder.append(val);
                builder.append(", ");
            }
            builder.append(")");

            return builder.toString();
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<>() {
                private final int expectedModCount = modCount;
                private Node<T> current = first;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    if (modCount != expectedModCount) throw new ConcurrentModificationException();
                    if (!hasNext()) throw new NoSuchElementException();

                    var val = current.value;
                    current = current.next;
                    return val;
                }
            };
        }

        static class Node<U> {
            public Node<U> prev;
            public Node<U> next;
            public U value;

            public Node(Node<U> prev, Node<U> next, U value) {
                this.prev = prev;
                this.next = next;
                this.value = value;
            }

            public boolean hasNext() {
                return next != null;
            }
        }
    }
}
