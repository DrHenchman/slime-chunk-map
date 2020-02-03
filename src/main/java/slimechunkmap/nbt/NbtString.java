package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class NbtString implements Nbt {

    static final byte ID = 8;

    private String value;

    public NbtString(String value) {
        this.value = value;
    }

    public NbtString() {
        value = "";
    }

    public String value() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        value = input.readUTF();
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeUTF(value);
    }

    @Override
    public NbtString clone() {
        return new NbtString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NbtString other = (NbtString) o;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
