package com.storedobject.helper;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamJava9 {
	public static <T> Stream<T> iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next) {
		Objects.requireNonNull(next);
		Objects.requireNonNull(hasNext);
		Spliterator<T> spliterator = new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE,
				Spliterator.ORDERED | Spliterator.IMMUTABLE) {
			T prev;
			boolean started, finished;

			@Override
			public boolean tryAdvance(Consumer<? super T> action) {
				Objects.requireNonNull(action);
				if (finished)
					return false;
				T t;
				if (started)
					t = next.apply(prev);
				else {
					t = seed;
					started = true;
				}
				if (!hasNext.test(t)) {
					prev = null;
					finished = true;
					return false;
				}
				action.accept(prev = t);
				return true;
			}

			@Override
			public void forEachRemaining(Consumer<? super T> action) {
				Objects.requireNonNull(action);
				if (finished)
					return;
				finished = true;
				T t = started ? next.apply(prev) : seed;
				prev = null;
				while (hasNext.test(t)) {
					action.accept(t);
					t = next.apply(t);
				}
			}
		};
		return StreamSupport.stream(spliterator, false);
	}

}
