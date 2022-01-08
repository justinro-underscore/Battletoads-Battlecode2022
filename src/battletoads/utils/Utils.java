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

    /**
     * Returns a random Direction.
     *
     * @return a random Direction
     */
    public static Direction getRandomDirection() {
        return directions[(int) ( Math.random() * directions.length )];
    }

    /**
     * This defines all of the points inside a circle based on the radius squared
     * More can be added from the circlepoints.py script in python directory
     */
    public static final Map<Integer, int[][]> CIRCLE_POINTS = new HashMap<Integer, int[][]>(){{
        put(20, new int[][]{{0,0},{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1},{0,2},{0,-2},{2,0},{-2,0},{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1},{2,2},{2,-2},{-2,2},{-2,-2},{0,3},{0,-3},{3,0},{-3,0},{1,3},{1,-3},{-1,3},{-1,-3},{3,1},{3,-1},{-3,1},{-3,-1},{2,3},{2,-3},{-2,3},{-2,-3},{3,2},{3,-2},{-3,2},{-3,-2},{0,4},{0,-4},{4,0},{-4,0},{1,4},{1,-4},{-1,4},{-1,-4},{4,1},{4,-1},{-4,1},{-4,-1},{3,3},{3,-3},{-3,3},{-3,-3},{2,4},{2,-4},{-2,4},{-2,-4},{4,2},{4,-2},{-4,2},{-4,-2}});
        put(34, new int[][]{{0,0},{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1},{0,2},{0,-2},{2,0},{-2,0},{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1},{2,2},{2,-2},{-2,2},{-2,-2},{0,3},{0,-3},{3,0},{-3,0},{1,3},{1,-3},{-1,3},{-1,-3},{3,1},{3,-1},{-3,1},{-3,-1},{2,3},{2,-3},{-2,3},{-2,-3},{3,2},{3,-2},{-3,2},{-3,-2},{0,4},{0,-4},{4,0},{-4,0},{1,4},{1,-4},{-1,4},{-1,-4},{4,1},{4,-1},{-4,1},{-4,-1},{3,3},{3,-3},{-3,3},{-3,-3},{2,4},{2,-4},{-2,4},{-2,-4},{4,2},{4,-2},{-4,2},{-4,-2},{0,5},{0,-5},{3,4},{3,-4},{-3,4},{-3,-4},{4,3},{4,-3},{-4,3},{-4,-3},{5,0},{-5,0},{1,5},{1,-5},{-1,5},{-1,-5},{5,1},{5,-1},{-5,1},{-5,-1},{2,5},{2,-5},{-2,5},{-2,-5},{5,2},{5,-2},{-5,2},{-5,-2},{4,4},{4,-4},{-4,4},{-4,-4},{3,5},{3,-5},{-3,5},{-3,-5},{5,3},{5,-3},{-5,3},{-5,-3}});
        put(53, new int[][]{{0,0},{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1},{0,2},{0,-2},{2,0},{-2,0},{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1},{2,2},{2,-2},{-2,2},{-2,-2},{0,3},{0,-3},{3,0},{-3,0},{1,3},{1,-3},{-1,3},{-1,-3},{3,1},{3,-1},{-3,1},{-3,-1},{2,3},{2,-3},{-2,3},{-2,-3},{3,2},{3,-2},{-3,2},{-3,-2},{0,4},{0,-4},{4,0},{-4,0},{1,4},{1,-4},{-1,4},{-1,-4},{4,1},{4,-1},{-4,1},{-4,-1},{3,3},{3,-3},{-3,3},{-3,-3},{2,4},{2,-4},{-2,4},{-2,-4},{4,2},{4,-2},{-4,2},{-4,-2},{0,5},{0,-5},{3,4},{3,-4},{-3,4},{-3,-4},{4,3},{4,-3},{-4,3},{-4,-3},{5,0},{-5,0},{1,5},{1,-5},{-1,5},{-1,-5},{5,1},{5,-1},{-5,1},{-5,-1},{2,5},{2,-5},{-2,5},{-2,-5},{5,2},{5,-2},{-5,2},{-5,-2},{4,4},{4,-4},{-4,4},{-4,-4},{3,5},{3,-5},{-3,5},{-3,-5},{5,3},{5,-3},{-5,3},{-5,-3},{0,6},{0,-6},{6,0},{-6,0},{1,6},{1,-6},{-1,6},{-1,-6},{6,1},{6,-1},{-6,1},{-6,-1},{2,6},{2,-6},{-2,6},{-2,-6},{6,2},{6,-2},{-6,2},{-6,-2},{4,5},{4,-5},{-4,5},{-4,-5},{5,4},{5,-4},{-5,4},{-5,-4},{3,6},{3,-6},{-3,6},{-3,-6},{6,3},{6,-3},{-6,3},{-6,-3},{0,7},{0,-7},{7,0},{-7,0},{1,7},{1,-7},{-1,7},{-1,-7},{5,5},{5,-5},{-5,5},{-5,-5},{7,1},{7,-1},{-7,1},{-7,-1},{4,6},{4,-6},{-4,6},{-4,-6},{6,4},{6,-4},{-6,4},{-6,-4},{2,7},{2,-7},{-2,7},{-2,-7},{7,2},{7,-2},{-7,2},{-7,-2}});
    }};

    //TODO ints 0-3 are going to store information on the archons location.
    //TODO internally they'll be used as strings, but use valuesOf() for storage;
    //TODO for example, say the enemy archon is at location (45, 21), the
    //TODO integer stored will be 4521
}
