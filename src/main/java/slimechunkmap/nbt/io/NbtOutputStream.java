package slimechunkmap.nbt.io;

import slimechunkmap.nbt.Nbt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class NbtOutputStream extends DataOutputStream {

    public NbtOutputStream(Compression compression, OutputStream out) throws IOException {
        super(compression.newOutputStream(out));
    }

    public void writeNbt(Nbt nbt) throws IOException {
        Nbt.write(this, nbt);
    }

}
