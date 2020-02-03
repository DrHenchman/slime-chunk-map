package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("unused")
public class NbtByteArray implements Nbt {

    static final byte ID = 7;

    private byte[] value;

    public NbtByteArray(byte[] value) {
        this.value = value;
    }

    public NbtByteArray() {
        this.value = new byte[0];
    }

    public byte[] value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        byte[] value = new byte[input.readInt()];
        input.readFully(value);
        this.value = value;
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeInt(value.length);
        output.write(value);
    }

    @Override
    public NbtByteArray clone() {
        return new NbtByteArray(Arrays.copyOf(value, value.length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtByteArray other = (NbtByteArray) o;
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
