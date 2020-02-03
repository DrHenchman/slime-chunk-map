package slimechunkmap.cli;

import slimechunkmap.nbt.NbtByte;
import slimechunkmap.nbt.NbtInt;
import slimechunkmap.nbt.NbtMap;
import slimechunkmap.nbt.io.Compression;
import slimechunkmap.nbt.io.NbtOutputStream;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;

public class CommandCreateMapData implements Command {
    @Override
    public int execute(InputStream stdIn, PrintStream stdOut, String... args) throws IOException {
        //data={unlimitedTracking=1, trackingPosition=1, scale=2, dimension=0, banners=[]},DataVersion=1501
        int xCenter = Integer.parseInt(args[1]);
        int zCenter = Integer.parseInt(args[2]);
        int scale = Integer.parseInt(args[3]);
        NbtMap root = new NbtMap(Collections.emptyMap());
        NbtMap mapData = new NbtMap(Collections.emptyMap());
        mapData.put("xCenter", new NbtInt(calculateMapCenter(xCenter, scale)));
        mapData.put("zCenter", new NbtInt(calculateMapCenter(zCenter, scale)));
        mapData.put("dimension", new NbtByte((byte) 0));
        mapData.put("scale", new NbtByte((byte) scale));
        mapData.put("unlimitedTracking", new NbtByte((byte) 1));
        mapData.put("trackingPosition", new NbtByte((byte) 1));
        root.put("data", mapData);
        root.put("DataVersion", new NbtInt(1501));
        try (OutputStream output = openFile(args[0], stdOut)) {
            try (NbtOutputStream nbtOutput = new NbtOutputStream(Compression.GZIP, new BufferedOutputStream(output))) {
                nbtOutput.writeNbt(root);
                nbtOutput.flush();
            }
        }
        return 0;
    }

    private OutputStream openFile(String fileName, OutputStream stdOut) throws FileNotFoundException {
        if ("-".equals(fileName)) {
            return stdOut;
        } else {
            return new FileOutputStream(fileName);
        }
    }

    private static int calculateMapCenter(double pos, int mapScale) {
        int i = 128 * (1 << mapScale);
        int j = floor((pos + 64.0D) / (double) i);
        return j * i + i / 2 - 64;
    }

    private static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }
}
