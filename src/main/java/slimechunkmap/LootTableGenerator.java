package slimechunkmap;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LootTableGenerator {

    public static int generate(long seed, int mapNum, int mapId, int centerX, int centerZ, int scale, File directory) throws IOException {
        int diameter = ((int) Math.pow(2, scale)) * 128;
        int radius = diameter / 2;
        List<String> parts = new ArrayList<>();
        for (int z = centerZ - radius + 8; z < centerZ + radius; z += 16) {
            for (int x = centerX - radius + 8; x < centerX + radius; x += 16) {
                if (isInSlimeChunk(seed, x, z)) {
                    parts.add("{rot:180.0d,type:4b,id:\"" + x + "_" + z + "\",x:" + x + ",z:" + z + "}");
                }
            }
        }
        try (OutputStream out = openFile(directory, mapNum)) {
            PrintStream output = new PrintStream(new BufferedOutputStream(out), false, StandardCharsets.UTF_8);

            output.println("{");
            output.println("  \"pools\": [");
            output.println("    {");
            output.println("      \"rolls\": 1,");
            output.println("      \"entries\": [");
            output.println("        {");
            output.println("          \"type\": \"item\",");
            output.println("          \"name\": \"minecraft:filled_map\",");
            output.println("          \"weight\": 1,");
            output.println("          \"functions\": [");
            output.println("            {");
            output.println("              \"function\": \"set_nbt\",");

            StringBuilder nbt = new StringBuilder();
            String name = "Slime Chunk Map #" + mapNum;
            String textComponent = quoteString(name);
            nbt.append("{");
            nbt.append("map:").append(mapId).append(",");
            nbt.append("display:{Name:").append(quoteString(textComponent)).append("},");
            nbt.append(parts.stream().collect(Collectors.joining(",", "Decorations:[", "]")));
            nbt.append("}");

            output.print("              \"tag\":");
            output.println(quoteString(nbt.toString()));

            output.println("            }");
            output.println("          ]");
            output.println("        }");
            output.println("      ]");
            output.println("    }");
            output.println("  ]");
            output.println("}");

            output.flush();
        }
        return 0;
    }

    static String quoteString(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
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

    private static OutputStream openFile(File directory, int mapNum) throws FileNotFoundException {
        if (!directory.isDirectory()) {
            throw new FileNotFoundException("'" + directory + "' is not a directory");
        }

        return new FileOutputStream(new File(directory, mapNum + ".json"));
    }

}
