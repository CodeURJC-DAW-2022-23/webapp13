package es.codeurjc.backend.utilities;

import org.springframework.data.util.Pair;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class OptTwo<T> {

    private final Pair<Optional<T>, Optional<T>> m;

    private OptTwo(Pair<Optional<T>, Optional<T>> m) {
        this.m = m;
    }

    public static <T> OptTwo<T> of(Optional<T> l, Optional<T> r) {
        return new OptTwo<>(Pair.of(l, r));
    }

    public static <T> OptTwo<T> empty() {
        return new OptTwo<>(Pair.of(Optional.empty(), Optional.empty()));
    }

    public boolean isEmpty() {
        return m.getFirst().isEmpty() && m.getSecond().isEmpty();
    }

    public Optional<T> getOptLeft() {
        return m.getFirst();
    }

    public Optional<T> getOptRight() {
        return m.getSecond();
    }

    public T getLeft() {
        return this.getOptLeft().get();
    }

    public T getRight() {
        return this.getOptRight().get();
    }

    public boolean isLeft() {
        return m.getFirst().isPresent();
    }

    public boolean isRight() {
        return m.getSecond().isPresent();
    }

    public <U> OptTwo<U> map(Function<T, U> f) {
        return (OptTwo<U>) OptTwo.of(
            this.getOptLeft().map(f),
            this.getOptRight().map(f)
        );
    }

    public void forEach(Consumer<T> f) {
        if (this.isLeft()) f.accept(this.getLeft());
        if (this.isRight()) f.accept(this.getRight());
    }

    public <U> OptPair<T, T> getOptPair() {
        return OptPair.of(this.getOptLeft(), this.getOptRight());
    }
}
