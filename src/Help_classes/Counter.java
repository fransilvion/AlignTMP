package Help_classes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fransilvion on 15.09.2016.
 */
public class Counter<T> {

    final Map<T, Integer> counts = new HashMap<>();

    public void add(T t) {
        counts.merge(t, 1, Integer::sum);
    }

    public int count(T t) {
        return counts.getOrDefault(t, 0);
    }
}
