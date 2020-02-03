package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtShort implements Nbt {

    static final byte ID = 2;

    private short value;

    public NbtShort(short value) {
        this.value = value;
    }

    NbtShort() {
    }

    public short value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readShort();
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeShort(value);
    }

    @Override
    public NbtShort clone() {
        return new NbtShort(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtShort other = (NbtShort) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(value);
    }

    @Override
    public String toString() {
        return Short.toString(value);
    }

}
