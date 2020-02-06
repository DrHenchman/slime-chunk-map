package slimechunkmap.nbt.io;

import slimechunkmap.nbt.Nbt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class NbtOutputStream extends DataOutputStream {

    private NbtOutputStream(Compression compression, OutputStream out) throws IOException {
        super(compression == null ? out : compression.newOutputStream(out));
    }

    public void writeNbt(Nbt nbt) throws IOException {
        Nbt.write(this, nbt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Compression compression;

        public Builder() {
        }

        public NbtOutputStream build(OutputStream out) throws IOException {
            return new NbtOutputStream(compression, out);
        }

        public Builder compression(Compression compression) {
            this.compression = compression;
            return this;
        }

    }

}
