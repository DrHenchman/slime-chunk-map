# Slime Chunk Maps

`slime-chunk-map` is a utility for generating in-game maps in Minecraft with markers
indicating the location of slime chunks. As slime chunks (chunks in the world in which
slimes spawn) are dependent on the seed, this is a viable alternative for multiplayer server
where administrators don't want to give out the world seed but still want to make it reasonably
easy for players to find slime chunks.

Slime chunk maps are generated in successive rings like as follows:

    10 11 12 13 14
    25 02 03 04 15
    24 09 01 05 16
    23 08 07 06 17
    22 21 20 19 18

This allows for administrators to pick and choose which maps they make available as well
as progressively reveal slime chunks surrounding an origin, as required. The maps themselves
are made up of two parts, the .dat data files which will need to uploaded into the world save
files, and .json files which are loot table definitions for referencing the maps. Note, the
loot tables are the part of the maps which hold the actual makers, so both are needed for the
solution to work. It is up to administrators to decide how to make use of the loot tables, for
example you might make them a random item which spawns in dungeons by overriding the vanilla
loot tables for dungeon chests.

# Requirements

* Java 1.8+

# Build from source

Currently, there is no distribution so you'll need to build fromm source using Gradle. Luckily
there is a Gradle wrapper file checked into the repository. Build using the following command:

    ./gradlew build

Or on windows, use the `gradlew.bat` file.

# User Guide

Once built, you can run slime-chunk-map as below (substituting in your world seed):

    java -jar build/libs/slime-chunk-map.jar --seed 123

By default, the first 9 slime chunk maps will be generated. But there are many configuration options
available for generating different map numbers and are different map ID offsets. use `--help` to see
the available options:

    java -jar build/libs/slime-chunk-map.jar --help

    Usage:
        java -jar slime-chunk-map.jar --seed SEED [options]
    
    Options:
        --seed SEED
            The seed for the world to generate the maps for
        --output directory
            The directory to output the files to. Default: current directory
        --scale SCALE
            The scale for the maps. Default: 2
        --start X Z
            The coordinates to start the map generation centered on. Default: 0 0
        --map-id-offset MAP_ID
            The map id to start generating maps from. Defaults to map number minus 1
        --range START END
            The range of map numbers to generate, inclusively. Default 1 9
        --ring NUMBER
            Instead of specifying a range of maps, generate the ring around existing maps
        --reserve RESERVE
            How many additional maps to reserve map IDs for

Finally, it is worth mentioning the `idcounts.dat` file which is also generated. In order for Minecraft
to recognise the maps files and not override them, you need to update the map ID counter. If you are
generating a new world, when it is recommended to reserve some additional map IDs using the `--reserve`
option so that you can use those id numbers later on without overriding or modifying user created maps.

For example, perhaps at the beginning of a new world you only want to generate the first 25 slime chunk
maps (the first 3 rings), but want to reserve some map IDs to be used later on in a world. You could
use the following command to reserve 75 additional map IDs:
    
    java -jar build/libs/slime-chunk-map.jar --seed 123 --range 1 25 --reserve 75

Then later, you can safely generate the next ring of slime chunk maps (map # 26 to 49) without worrying
about overriding user generated maps:

    java -jar build/libs/slime-chunk-map.jar --seed 123 --ring 4

# License

slime-chunk-map
Copyright (C) 2020  DrHenchman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
