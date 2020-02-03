package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public interface Nbt extends Cloneable {

    byte getTypeId();

    void readNbt(DataInput input) throws IOException;

    void writeNbt(DataOutput output) throws IOException;

    Nbt clone();

    default NbtMap asMap() {
        return (NbtMap) this;
    }

    default NbtList asList() {
        return (NbtList) this;
    }

    static Nbt read(DataInput input) throws IOException {
        byte type = input.readByte();
        if (type == NbtEnd.ID) {
            return NbtEnd.INSTANCE;
        } else {
            input.readUTF(); // ignore response
            Nbt nbt = createForType(type);
            nbt.readNbt(input);
            return nbt;
        }
    }

    static void write(DataOutput output, Nbt nbt) throws IOException {
        output.writeByte(nbt.getTypeId());
        output.writeUTF("");
        nbt.writeNbt(output);
    }

    static Nbt createForType(byte type) {
        switch (type) {
            case NbtEnd.ID:
                return NbtEnd.INSTANCE;
            case NbtByte.ID:
                return new NbtByte();
            case NbtShort.ID:
                return new NbtShort();
            case NbtInt.ID:
                return new NbtInt();
            case NbtLong.ID:
                return new NbtLong();
            case NbtFloat.ID:
                return new NbtFloat();
            case NbtDouble.ID:
                return new NbtDouble();
            case NbtByteArray.ID:
                return new NbtByteArray();
            case NbtString.ID:
                return new NbtString();
            case NbtList.ID:
                return new NbtList();
            case NbtMap.ID:
                return new NbtMap();
            case NbtIntArray.ID:
                return new NbtIntArray();
            case NbtLongArray.ID:
                return new NbtLongArray();
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

}
