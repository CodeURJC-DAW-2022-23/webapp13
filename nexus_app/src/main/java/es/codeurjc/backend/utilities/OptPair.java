package es.codeurjc.backend.utilities;

import io.vavr.control.Try;
import org.springframework.data.util.Pair;

import java.util.Optional;

public class OptPair<T, U> {

    private final Pair<Optional<T>, Optional<U>> m;

    private OptPair(Pair<Optional<T>, Optional<U>> m) {
        this.m = m;
    }

    public static <T, U> OptPair<T, U> of(Optional<T> l, Optional<U> r) {
        return new OptPair<>(Pair.of(l, r));
    }

    public static <T, U> OptPair<T, U> empty() {
        return new OptPair(Pair.of(Optional.empty(), Optional.empty()));
    }

    public boolean isEmpty() {
        return m.getFirst().isEmpty() && m.getSecond().isEmpty();
    }

    public Optional<T> getOptLeft() {
        return m.getFirst();
    }

    public Optional<U> getOptRight() {
        return m.getSecond();
    }

    public T getLeft() {
        return this.getOptLeft().get();
    }

    public U getRight() {
        return this.getOptRight().get();
    }

    public boolean isLeft() {
        return m.getFirst().isPresent();
    }

    public boolean isRight() {
        return m.getSecond().isPresent();
    }

    public <X> OptTwo<X> getOptTwo() {
        Optional<X> l = Try
            .of(() -> this.getOptLeft().map(x -> (X) x))
            .getOrElse(Optional.empty());

        Optional<X> r = Try
            .of(() -> this.getOptLeft().map(x -> (X) x))
            .getOrElse(Optional.empty());

        return OptTwo.of(l, r);
    }
}
