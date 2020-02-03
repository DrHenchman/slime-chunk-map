package slimechunkmap.nbt.io;

import java.io.IOException;

@FunctionalInterface
interface IOFunction<I, O> {
    O apply(I input) throws IOException;
}
