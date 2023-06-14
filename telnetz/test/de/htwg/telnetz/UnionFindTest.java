package de.htwg.telnetz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {
    @Test
    void union() {
        final UnionFind uf = new UnionFind(11);
        assertEquals(11, uf.size());

        uf.union(0, 6);

        assertEquals(6, uf.find(0));
        assertEquals(10, uf.size());
    }

    @Test
    void advancedUnion() {
        final UnionFind uf = new UnionFind(12);
        uf.union(2, 0);
        uf.union(1, 0);

        uf.union(7, 4);
        uf.union(9, 4);
        uf.union(11, 6);
        uf.union(10, 6);
        uf.union(4, 6);

        uf.union(8, 5);

        assertEquals(4, uf.size());

        assertEquals(6, uf.find(9));
        assertEquals(6, uf.find(11));
        assertEquals(3, uf.find(3));
        assertEquals(0, uf.find(2));

        uf.union(0, 6);

        assertEquals(6, uf.find(2));
    }
}
