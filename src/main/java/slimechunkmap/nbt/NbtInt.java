package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@SuppressWarnings("unused")
public class NbtInt implements Nbt {

    static final byte ID = 3;

    private int value;

    public NbtInt(int value) {
        this.value = value;
    }

    NbtInt() {
    }

    public int value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readInt();

    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeInt(value);
    }

    @Override
    public NbtInt clone() {
        return new NbtInt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtInt other = (NbtInt) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
