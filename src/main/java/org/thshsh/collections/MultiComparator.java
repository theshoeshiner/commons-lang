package org.thshsh.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @deprecated use java Comparator.then
 */
@Deprecated
public class MultiComparator<T> implements Comparator<T> {
    private final List<Comparator<T>> comparators;

    public MultiComparator(List<Comparator<T>> comparators) {
        this.comparators = comparators;
    }

    public MultiComparator(Comparator<T>... comparators) {
        this(Arrays.asList(comparators));
    }

    public int compare(T o1, T o2) {
        for (Comparator<T> c : comparators) {
            int result = c.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

}
