package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtFloat implements Nbt {

    static final byte ID = 5;

    private float value;

    public NbtFloat(float value) {
        this.value = value;
    }

    NbtFloat() {
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    public float value() {
        return value;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readFloat();
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeFloat(value);
    }

    @Override
    public NbtFloat clone() {
        return new NbtFloat(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtFloat other = (NbtFloat) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

}
