package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
public class NbtLongArray implements Nbt {

    static final byte ID = 12;

    private long[] value;

    public NbtLongArray(long[] value) {
        this.value = value;
    }

    public NbtLongArray() {
        value = new long[0];
    }

    public long[] value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        long[] value = new long[input.readInt()];
        for (int i = 0; i < value.length; i++) {
            value[i] = input.readLong();
        }
        this.value = value;
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for (long v : value) {
            output.writeLong(v);
        }
    }

    @Override
    public NbtLongArray clone() {
        return new NbtLongArray(Arrays.copyOf(value, value.length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtLongArray that = (NbtLongArray) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }

}
