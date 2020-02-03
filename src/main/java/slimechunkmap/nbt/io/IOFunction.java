package slimechunkmap.nbt.io;

import java.io.IOException;

@SuppressWarnings("unused")
@FunctionalInterface
interface IOFunction<I, O> {
    O apply(I input) throws IOException;
}
