package slimechunkmap;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        Options options = parseOptions(args);
        int originX = calculateMapCenter(options.startX, options.scale);
        int originZ = calculateMapCenter(options.startZ, options.scale);
        for (int mapNum = options.mapNumberStart, mapId = options.mapIdOffset; mapNum <= options.mapNumberEnd; mapNum++, mapId++) {
            int centerX = calculateCenterXFromOrigin(mapNum, options.scale, originX);
            int centerZ = calculateCenterZFromOrigin(mapNum, options.scale, originZ);
            System.out.println("Generating dat file for map #" + mapNum + " with map ID " + mapId +
                    " at coordinates " + centerX + ", " + centerZ);
            checkCode(MapFileGenerator.generate(mapId, centerX, centerZ, options.scale, options.output));
            System.out.println("Generating loot table for map #" + mapNum + " with map ID " + mapId +
                    " at coordinates " + centerX + ", " + centerZ);
            checkCode(LootTableGenerator.generate(options.seed, mapNum, mapId, centerX, centerZ, options.scale, options.output));
        }
        int nextMapId = options.mapIdOffset + (options.mapNumberEnd - options.mapNumberStart + 1) + options.reserve;
        System.out.println("Reserving until map ID " + nextMapId);
        IdCountsGenerator.generate(nextMapId, options.output);
        System.out.println("Files generated into " + options.output);
    }

    private static Options parseOptions(String... args) {
        Options options = null;
        try {
            options = Options.parse(args);
        } catch (RuntimeException e) {
            Options.help(System.err);
            System.exit(1);
        }
        return options;
    }

    private static void checkCode(int exitCode) {
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    @SuppressWarnings("Duplicates")
    static int calculateCenterXFromOrigin(int mapNum, int scale, int originX) {
        if (mapNum == 1) {
            return originX;
        }
        int ring = calculateRing(mapNum);
        int width = ring * 2 - 1;
        int innerArea = (width - 2) * (width - 2);
        int remainder = mapNum - innerArea - 1;
        int halfWidth = ring - 1;
        int offset = -halfWidth;
        if (remainder >= width) {
            remainder -= width;
            offset = halfWidth;
            if (remainder >= width) {
                remainder -= width;
                if (remainder >= width) {
                    offset = -halfWidth;
                } else {
                    offset -= remainder;
                }
            }
        } else {
            offset += remainder;
        }
        int diameter = ((int) Math.pow(2, scale)) * 128;
        return offset * diameter + originX;
    }

    @SuppressWarnings("Duplicates")
    static int calculateCenterZFromOrigin(int mapNum, int scale, int originZ) {
        if (mapNum == 1) {
            return originZ;
        }
        int ring = calculateRing(mapNum);
        int width = ring * 2 - 1;
        int innerArea = (width - 2) * (width - 2);
        int remainder = mapNum - innerArea - 1;
        int halfWidth = ring - 1;
        int offset = -halfWidth;
        if (remainder >= width) {
            remainder -= width;
            if (remainder >= width) {
                remainder -= width;
                offset = halfWidth;
                if (remainder >= width) {
                    remainder -= width;
                    offset -= remainder;
                }
            } else {
                offset += remainder;
            }
        }
        int diameter = ((int) Math.pow(2, scale)) * 128;
        return offset * diameter + originZ;
    }

    static int calculateRing(int mapNum) {
        int ring = 1;
        while ((ring * 2 - 1) * (ring * 2 - 1) < mapNum) {
            ring += 1;
        }
        return ring;
    }

    static int calculateMapCenter(double pos, int mapScale) {
        int i = 128 * (1 << mapScale);
        int j = floor((pos + 64.0D) / (double) i);
        return j * i + i / 2 - 64;
    }

    private static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

}
