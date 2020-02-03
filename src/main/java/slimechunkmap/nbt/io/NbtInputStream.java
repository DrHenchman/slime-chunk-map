package slimechunkmap.nbt.io;

import slimechunkmap.nbt.Nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NbtInputStream extends DataInputStream {

    public NbtInputStream(Compression compression, InputStream in) throws IOException {
        super(compression.newInputStream(in));
    }

    public Nbt readNbt() throws IOException {
        return Nbt.read(this);
    }

}
