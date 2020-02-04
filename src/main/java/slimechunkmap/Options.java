package slimechunkmap;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;

public class Options {

    public long seed;
    public int scale = 2;
    public int mapIdOffset;
    public int startX = 0;
    public int startZ = 0;
    public int mapNumberStart = 1;
    public int mapNumberEnd = 9;
    public File output = new File(".");

    public static Options parse(String... args) {
        OptionIterator it = new OptionIterator(Arrays.asList(args).iterator());
        Options options = new Options();
        boolean seedSpecified = false;
        boolean mapIdOffsetSpecified = false;
        while (it.hasMoreOptions()) {
            String option = it.nextOption();
            switch (option) {
                case "--seed":
                    options.seed = it.nextLong();
                    seedSpecified = true;
                    break;
                case "--scale":
                    options.scale = it.nextInteger();
                    break;
                case "--start":
                    options.startX = it.nextInteger();
                    options.startZ = it.nextInteger();
                    break;
                case "--map-id-offset":
                    options.mapIdOffset = it.nextInteger();
                    mapIdOffsetSpecified = true;
                    break;
                case "--range":
                    options.mapNumberStart = it.nextInteger();
                    options.mapNumberEnd = it.nextInteger();
                    break;
                case "--ring":
                    int ring = it.nextInteger();
                    int width = ring * 2 - 1;
                    if (width == 1) {
                        options.mapNumberStart = 1;
                        options.mapNumberEnd = 1;
                    } else {
                        int widthMinus1 = width - 1;
                        int widthMinus2 = width - 2;
                        options.mapNumberStart = widthMinus2 * widthMinus2 + 1;
                        options.mapNumberEnd = options.mapNumberStart + widthMinus1 * 4 - 1;
                    }
                    break;
                case "--output":
                    options.output = it.nextDirectory();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported option: " + option);
            }
        }
        if (!seedSpecified) {
            throw new IllegalArgumentException("--seed must be specified");
        }
        if (!mapIdOffsetSpecified) {
            options.mapIdOffset = options.mapNumberStart - 1;
        }
        return options;
    }

    public static void help(PrintStream out) {
        out.println("Usage:");
        out.println("\tjava -jar slime-chunk-map.jar --seed SEED [options]");
        out.println();
        out.println("Options:");
        out.println("\t--seed SEED");
        out.println("\t\tThe seed for the world to generate the maps for");
        out.println("\t--scale SCALE");
        out.println("\t\tThe scale for the maps. Default: 2");
        out.println("\t--start X Z");
        out.println("\t\tThe coordinates to start the map generation centered on. Default: 0 0");
        out.println("\t--map-id-offset MAP_ID");
        out.println("\t\tThe map id to start generating maps from. Defaults to map number minus 1");
        out.println("\t--range START END");
        out.println("\t\tThe range of map numbers to generate, inclusively. Default 1 9");
        out.println("\t--ring NUMBER");
        out.println("\t\tInstead of specifying a range of maps, generate the ring around existing maps");
    }

    static class OptionIterator {

        private final Iterator<String> it;
        private String option;

        OptionIterator(Iterator<String> it) {
            this.it = it;
        }

        boolean hasMoreOptions() {
            return it.hasNext();
        }

        String nextOption() {
            option = it.next();
            if (!option.startsWith("--")) {
                throw new IllegalArgumentException("Expected option, got: " + option);
            }
            return option;
        }

        int nextInteger() {
            String value = nextValue();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Expected number for " + option + ", got: " + value);
            }
        }

        long nextLong() {
            String value = nextValue();
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Expected number for " + option + ", got: " + value);
            }
        }

        File nextDirectory() {
            File directory = new File(nextValue());
            if (!directory.isDirectory()) {
                throw new IllegalArgumentException("Expected directory for " + option + ", got: " + directory);
            }
            return directory;
        }

        String nextValue() {
            if (!it.hasNext()) {
                throw new IllegalArgumentException("Missing value for option " + option);
            }
            return it.next();
        }

    }

}
