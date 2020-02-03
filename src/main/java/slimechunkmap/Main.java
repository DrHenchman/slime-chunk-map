package slimechunkmap;

import slimechunkmap.cli.Command;
import slimechunkmap.cli.CommandCreateMapData;
import slimechunkmap.cli.CommandSlimeChunkMap;

import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {
        String commandName = args[0];
        Command command;
        switch (commandName) {
            case "createMap":
                command = new CommandCreateMapData();
                break;
            case "slimeMap":
                command = new CommandSlimeChunkMap();
                break;
            default:
                System.err.println("Unsupported command: " + commandName);
                System.exit(1);
                return;
        }
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        System.exit(command.execute(System.in, System.out, subArgs));
    }

}
