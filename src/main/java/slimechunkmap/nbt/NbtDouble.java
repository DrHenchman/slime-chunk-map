package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtDouble implements Nbt {

    static final byte ID = 6;

    private double value;

    public NbtDouble(double value) {
        this.value = value;
    }

    NbtDouble() {
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readDouble();
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeDouble(value);
    }

    @Override
    public NbtDouble clone() {
        return new NbtDouble(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtDouble other = (NbtDouble) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

}
