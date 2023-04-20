// O. Bittel
// 22.09.2022

package de.htwg.alda.dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the de.htwg.alda.dictionary.Dictionary interface as AVL tree.
 * <p>
 * The entries are ordered using their natural ordering on the keys,
 * or by a Comparator provided at set creation time, depending on which constructor is used.
 * <p>
 * An iterator for this dictionary is implemented by using the parent node reference.
 *
 * @param <K> Key.
 * @param <V> Value.
 */
public class BinaryTreeDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private Node<K, V> root = null;
    private int size = 0;
    private int modCount = 0;
    private V oldValue = null;

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }
        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        modCount++;
        return oldValue;
    }

    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            size++;
            p = new Node<>(key, value);
            oldValue = null;
        } else if (key.compareTo(p.key) < 0)
            p.left = insertR(key, value, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = insertR(key, value, p.right);
        else { // Schlüssel bereits vorhanden:
            oldValue = p.value;
            p.value = value;
        }

        p = balance(p);
        return p;
    }

    @Override
    public V search(K key) {
        return searchR(key, root).value;
    }

    private Node<K, V> searchR(K key, Node<K, V> p) {
        if (p == null) throw new NoSuchElementException();
        else if (key.compareTo(p.key) < 0) {
            return searchR(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            return searchR(key, p.right);
        } else {
            return p;
        }
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

    /**
     * Pretty prints the tree
     */
    public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);
        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private int getHeight(Node<K, V> node) {
        return node == null ? -1 : node.height;
    }

    private int getBalance(Node<K, V> p) {
        return p == null ? 0 : getHeight(p.right) - getHeight(p.left);
    }

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null) return null;

        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0) {
                p = rotateRight(p);
            } else {
                p = rotateLeftRight(p);
            }
        } else if (getBalance(p) == 2) {
            if (getBalance(p.right) >= 0) {
                p = rotateLeft(p);
            } else {
                p = rotateLeftRight(p);
            }
        }
        return p;
    }

    private Node<K, V> rotateRight(Node<K, V> p) {
        Node<K, V> q = p.left;
        p.left = q.right;
        q.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeft(Node<K, V> p) {
        Node<K, V> q = p.right;
        p.right = q.left;
        q.left = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node<K, V> p) {
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }

    private Node<K, V> rotateRightLeft(Node<K, V> p) {
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }

    static private class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }
    }

}
