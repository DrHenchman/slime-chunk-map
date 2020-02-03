package slimechunkmap.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CommandSlimeChunkMap implements Command {
    @Override
    public int execute(InputStream input, PrintStream output, String... args) throws IOException {
        long seed = Long.parseLong(args[0]);
        int startX = Integer.parseInt(args[1]);
        int startZ = Integer.parseInt(args[2]);
        int scale = Integer.parseInt(args[3]);
        int diameter = ((int) Math.pow(2, scale)) * 128;
        int radius = diameter / 2;
        int centerX = calculateMapCenter(startX, scale);
        int centerZ = calculateMapCenter(startZ, scale);
        List<String> parts = new ArrayList<>();
        for (int z = centerZ - radius + 8; z < centerZ + radius; z += 16) {
            for (int x = centerX - radius + 8; x < centerX + radius; x += 16) {
                if (isInSlimeChunk(seed, x, z)) {
                    parts.add("{rot:180.0d,type:4b,id:\"" + x + "_" + z + "\",x:" + x + ",z:" + z + "}");
                }
            }
        }
        output.println(parts.stream().collect(Collectors.joining(",", "Decorations:[", "]")));
        return 0;
    }

    private static boolean isInSlimeChunk(long seed, int blockX, int blockZ) {
        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;
        Random rnd = new Random(seed +
                (long) (chunkX * chunkX * 0x4c1906) +
                (long) (chunkX * 0x5ac0db) +
                (long) (chunkZ * chunkZ) * 0x4307a7L +
                (long) (chunkZ * 0x5f24f) ^ 0x3ad8025f);
        return rnd.nextInt(10) == 0;
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
