package slimechunkmap.nbt.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

@SuppressWarnings("unused")
public enum Compression {
    GZIP(1, GZIPInputStream::new, GZIPOutputStream::new),
    DEFLATE(2, InflaterInputStream::new, DeflaterOutputStream::new);

    private static final Compression[] BY_ID;

    static {
        int maxId = Arrays.stream(values()).mapToInt(Compression::getId).max().orElse(0);
        BY_ID = new Compression[maxId+1];
        for (Compression compression : values()) {
            BY_ID[compression.getId()] = compression;
        }

    }

    private final byte id;
    private final IOFunction<InputStream, InflaterInputStream> inputStreamFactory;
    private final IOFunction<OutputStream, DeflaterOutputStream> outputStreamFactory;

    Compression(int id, IOFunction<InputStream, InflaterInputStream> inputStreamFactory, IOFunction<OutputStream, DeflaterOutputStream> outputStreamFactory) {
        this.id = (byte) id;
        this.inputStreamFactory = inputStreamFactory;
        this.outputStreamFactory = outputStreamFactory;
    }

    public static Compression byId(byte id) {
        Compression compression = BY_ID[id];
        if (compression == null) {
            throw new ArrayIndexOutOfBoundsException(id);
        }
        return compression;
    }

    public byte getId() {
        return id;
    }

    public InflaterInputStream newInputStream(InputStream in) throws IOException {
        return inputStreamFactory.apply(in);
    }

    public DeflaterOutputStream newOutputStream(OutputStream out) throws IOException {
        return outputStreamFactory.apply(out);
    }
}
