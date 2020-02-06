package slimechunkmap;

import slimechunkmap.nbt.NbtByte;
import slimechunkmap.nbt.NbtInt;
import slimechunkmap.nbt.NbtMap;
import slimechunkmap.nbt.io.Compression;
import slimechunkmap.nbt.io.NbtOutputStream;

import java.io.*;
import java.util.Collections;

public class MapFileGenerator {

    public static int generate(int mapId, int centerX, int centerZ, int scale, File directory) throws IOException {
        //data={unlimitedTracking=1, trackingPosition=1, scale=2, dimension=0, banners=[]},DataVersion=1501
        NbtMap root = new NbtMap(Collections.emptyMap());
        NbtMap mapData = new NbtMap(Collections.emptyMap());
        mapData.put("xCenter", new NbtInt(centerX));
        mapData.put("zCenter", new NbtInt(centerZ));
        mapData.put("dimension", new NbtByte((byte) 0));
        mapData.put("scale", new NbtByte((byte) scale));
        mapData.put("unlimitedTracking", new NbtByte((byte) 1));
        mapData.put("trackingPosition", new NbtByte((byte) 1));
        root.put("data", mapData);
        root.put("DataVersion", new NbtInt(1501));
        try (OutputStream output = openFile(directory, mapId)) {
            try (NbtOutputStream nbtOutput = NbtOutputStream.builder().compression(Compression.GZIP).build(new BufferedOutputStream(output))) {
                nbtOutput.writeNbt(root);
                nbtOutput.flush();
            }
        }
        return 0;
    }

    private static OutputStream openFile(File directory, int mapId) throws FileNotFoundException {
        if (!directory.isDirectory()) {
            throw new FileNotFoundException("'" + directory + "' is not a directory");
        }

        return new FileOutputStream(new File(directory, "map_" + mapId + ".dat"));
    }

}
