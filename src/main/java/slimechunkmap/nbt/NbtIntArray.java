package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NbtIntArray implements Nbt {

    static final byte ID = 11;

    private int[] value;

    public NbtIntArray(int[] value) {
        this.value = value;
    }

    NbtIntArray() {
        value = new int[0];
    }

    public int[] value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        int[] value = new int[input.readInt()];
        for (int i = 0; i < value.length; i++) {
            value[i] = input.readInt();
        }
        this.value = value;
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for (int v : value) {
            output.writeInt(v);
        }
    }

    @Override
    public NbtIntArray clone() {
        return new NbtIntArray(Arrays.copyOf(value, value.length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtIntArray other = (NbtIntArray) o;
        return Arrays.equals(value, other.value);
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
