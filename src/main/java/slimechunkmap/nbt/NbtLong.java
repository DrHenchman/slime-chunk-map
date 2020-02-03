package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtLong implements Nbt {

    static final byte ID = 4;

    private long value;

    public NbtLong(long value) {
        this.value = value;
    }

    NbtLong() {
    }

    public long value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readLong();
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeLong(value);
    }

    @Override
    public NbtLong clone() {
        return new NbtLong(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtLong other = (NbtLong) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

}
