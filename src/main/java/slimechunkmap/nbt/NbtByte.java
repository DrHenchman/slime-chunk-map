package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtByte implements Nbt {

    static final byte ID = 1;

    private byte value;

    public NbtByte(byte value) {
        this.value = value;
    }

    NbtByte() {
    }

    public byte value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readByte();

    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeByte(value);
    }

    @Override
    public NbtByte clone() {
        return new NbtByte(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtByte other = (NbtByte) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }

    @Override
    public String toString() {
        return Byte.toString(value);
    }

}
