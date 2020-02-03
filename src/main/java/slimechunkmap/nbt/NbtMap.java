package slimechunkmap.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NbtMap extends AbstractMap<String, Nbt> implements Nbt, Map<String, Nbt> {

    static final byte ID = 10;

    private Map<String, Nbt> values;

    public NbtMap(Map<String, Nbt> values) {
        this.values = new LinkedHashMap<>(values);
    }

    NbtMap() {
        values = new LinkedHashMap<>();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Nbt> m) {
        super.putAll(m);
    }

    @Override
    public Set<Entry<String, Nbt>> entrySet() {
        return values.entrySet();
    }

    @Override
    public Nbt put(String key, Nbt value) {
        return values.put(key, value);
    }

    @Override
    public byte getTypeId() {
        return ID;
    }

    @Override
    public void readNbt(DataInput input) throws IOException {
        Map<String, Nbt> values = new LinkedHashMap<>();
        byte subTypeId;
        while ((subTypeId = input.readByte()) != NbtEnd.ID) {
            String key = input.readUTF();
            Nbt value = Nbt.createForType(subTypeId);
            value.readNbt(input);
            values.put(key, value);
        }
        this.values = values;
    }

    @Override
    public void writeNbt(DataOutput output) throws IOException {
        for (Entry<String, Nbt> entry : values.entrySet()) {
            output.writeByte(entry.getValue().getTypeId());
            output.writeUTF(entry.getKey());
            entry.getValue().writeNbt(output);
        }
        output.writeByte(NbtEnd.ID);
    }

    @Override
    public NbtMap clone() {
        return new NbtMap(values);
    }
}
