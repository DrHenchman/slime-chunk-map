package slimechunkmap.nbt.io;

import slimechunkmap.nbt.Nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class NbtInputStream extends DataInputStream {

    private NbtInputStream(Compression compression, InputStream in) throws IOException {
        super(compression == null ? in : compression.newInputStream(in));
    }

    public Nbt readNbt() throws IOException {
        return Nbt.read(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Compression compression;

        public Builder() {
        }

        public NbtInputStream build(InputStream in) throws IOException {
            return new NbtInputStream(compression, in);
        }

        public Builder compression(Compression compression) {
            this.compression = compression;
            return this;
        }

    }

}
