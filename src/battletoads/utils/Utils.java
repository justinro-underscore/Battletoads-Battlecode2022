package battletoads.utils;

import battlecode.common.*;

import java.util.*;

public class Utils {

    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    public static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    public static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };

    public static final Set<Direction> CARDINAL_DIRECTIONS = new HashSet<Direction>(Arrays.asList(
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST
    ));

    public static Direction getDirectionFromVector(MapLocation ml1, MapLocation ml2) {
        if (ml2.y > ml1.y) {
            if (ml2.x < ml1.x) {
                return Direction.NORTHWEST;
            }
            else if (ml2.x > ml1.x) {
                return Direction.NORTHEAST;
            }
            return Direction.NORTH;
        }
        else if (ml2.y < ml1.y) {
            if (ml2.x < ml1.x) {
                return Direction.SOUTHWEST;
            }
            else if (ml2.x > ml1.x) {
                return Direction.SOUTHEAST;
            }
            return Direction.SOUTH;
        }
        else {
            if (ml2.x < ml1.x) {
                return Direction.WEST;
            }
            else if (ml2.x > ml1.x) {
                return Direction.EAST;
            }
            return Direction.CENTER;
        }
    }

    //TODO ints 0-3 are going to store information on the archons location.
    //TODO internally they'll be used as strings, but use valuesOf() for storage;
    //TODO for example, say the enemy archon is at location (45, 21), the
    //TODO integer stored will be 4521
}
