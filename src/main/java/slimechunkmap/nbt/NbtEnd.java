package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NbtEnd implements Nbt {

    public static final NbtEnd INSTANCE = new NbtEnd();

    static final byte ID = 0;

    private NbtEnd() {
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
    }

    @Override
    public NbtEnd clone() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "END";
    }

}
