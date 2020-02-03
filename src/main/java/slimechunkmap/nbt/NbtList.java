package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class NbtList extends AbstractList<Nbt> implements Nbt, List<Nbt> {

    static final byte ID = 9;

    private byte subTypeId;
    private List<Nbt> values;

    public NbtList(byte subTypeId, List<Nbt> values) {
        this.subTypeId = subTypeId;
        this.values = new ArrayList<>(values);
    }

    public NbtList() {
        subTypeId = NbtEnd.ID;
        values = new ArrayList<>();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Nbt get(int index) {
        return values.get(index);
    }

    @Override
    public Nbt set(int index, Nbt element) {
        if (subTypeId != element.getTypeId()) {
            throw new IllegalArgumentException("Excepted subtype " + subTypeId + " got " + element.getTypeId());
        }
        return values.set(index, element);
    }

    @Override
    public void add(int index, Nbt element) {
        if (isEmpty() ? element == NbtEnd.INSTANCE : subTypeId != element.getTypeId()) {
            throw new IllegalArgumentException("Excepted subtype " + subTypeId + " got " + element.getTypeId());
        }
        subTypeId = element.getTypeId();
        values.add(index, element);
    }

    @Override
    public Nbt remove(int index) {
        Nbt subNbt = values.remove(index);
        if (isEmpty()) {
            subTypeId = NbtEnd.ID;
        }
        return subNbt;
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    public byte getSubTypeId() {
        return subTypeId;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        byte subTypeId = input.readByte();
        int size = input.readInt();
        if (subTypeId == NbtEnd.ID && size > 0) {
            throw new IOException("Invalid subtype");
        }
        List<Nbt> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Nbt subNbt = Nbt.createForType(subTypeId);
            subNbt.readNbt(input);
            values.add(subNbt);
        }
        this.subTypeId = subTypeId;
        this.values = values;
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        output.writeByte(subTypeId);
        output.writeInt(values.size());
        for (Nbt subNbt : values) {
            subNbt.writeNbt(output);
        }
    }

    @Override
    public NbtList clone() {
        return new NbtList(subTypeId, values);
    }

}
