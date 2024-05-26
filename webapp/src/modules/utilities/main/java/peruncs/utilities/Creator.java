package peruncs.utilities;


import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interface for builders, to be able to accept a builder in addition to an instance.
 * <p>
 * This interface is similar to {@link java.util.function.Supplier} as it provides an instance, only for classes that act
 * as instance builders (fluent API builder pattern), where method {@link java.util.function.Supplier#get()} would be
 * misleading.
 *
 * @param <B> Type of the builder
 * @param <T> Type of the built instance
 */
@FunctionalInterface
public interface Creator<B extends Creator<B, T>, T> extends Supplier<T> {
    /**
     * Build the instance from this builder.
     *
     * @return instance of the built type
     */
    T create();

    /**
     * Update the builder in a fluent API way.
     *
     * @param consumer consumer of the builder instance
     * @return updated builder instance
     */
    default B update(Consumer<B> consumer) {
        consumer.accept(identity());
        return identity();
    }

    /**
     * Instance of this builder as the correct type.
     *
     * @return this instance typed to correct type
     */
    @SuppressWarnings("unchecked")
    default B identity() {
        return (B) this;
    }

    @Override
    default T get() {
        return create();
    }
}
