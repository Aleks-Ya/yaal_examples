package java8.stream.collector;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class SingleElementCollector<T> implements Collector<T, Set<T>, T> {

    @Override
    public Supplier<Set<T>> supplier() {
        return HashSet::new;
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        return (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        };
    }

    @Override
    public Function<Set<T>, T> finisher() {
        return (set) -> {
            if (set.isEmpty()) {
                throw new NoSuchElementException();
            }
            if (set.size() > 1) {
                throw new IllegalStateException("Expect single element, but found " + set.size());
            }
            return set.iterator().next();
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        return Set::add;
    }

}
