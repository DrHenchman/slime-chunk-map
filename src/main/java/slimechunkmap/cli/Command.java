package slimechunkmap.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public interface Command {

    int execute(InputStream input, PrintStream output, String... args) throws IOException;

}
