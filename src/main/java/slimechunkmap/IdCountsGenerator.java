package slimechunkmap;

import slimechunkmap.nbt.NbtInt;
import slimechunkmap.nbt.NbtMap;
import slimechunkmap.nbt.io.NbtOutputStream;

import java.io.*;
import java.util.Collections;

public class IdCountsGenerator {

    public static int generate(int nextMapId, File directory) throws IOException {
        NbtMap nbt = new NbtMap(Collections.emptyMap());
        nbt.put("map", new NbtInt(nextMapId));
        try (OutputStream output = openFile(directory)) {
            try (NbtOutputStream nbtOutput = NbtOutputStream.builder().build(new BufferedOutputStream(output))) {
                nbtOutput.writeNbt(nbt);
                nbtOutput.flush();
            }
        }
        return 0;
    }

    private static OutputStream openFile(File directory) throws FileNotFoundException {
        if (!directory.isDirectory()) {
            throw new FileNotFoundException("'" + directory + "' is not a directory");
        }

        return new FileOutputStream(new File(directory, "idcounts.dat"));
    }

}
