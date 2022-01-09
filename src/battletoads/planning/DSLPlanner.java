package battletoads.planning;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import battletoads.utils.Utils;
import battletoads.planning.location.DSLKey;
import battletoads.planning.location.DSLEntry;
import battletoads.planning.location.DSLComparator;
import battletoads.planning.location.DSLLocation;

/**
 * D* Lite planning
 */
public class DSLPlanner {

    // Min heap to get next inconsistant key pair
    private PriorityQueue<DSLEntry> inconsistencies;

    // Set of locations to search
    private Set<DSLLocation> locations;

    // Robot controller
    private RobotController robotController;

    // Edge change cost
    private int k_m;

    // Goal and start
    MapLocation s_goal;
    MapLocation s_start;

    /**
     * Constructor to initialize planner with surrounding tiles
     */
    public DSLPlanner( RobotController robotController ) {
        // Set robot controller
        this.robotController = robotController;
    }

    // TODO: there should be two version of this
    // 1. Ellipse between start and goal (Greedy) (find mid point and get circle from there with radius to start or end)
    // 2. Entire sensing locations (Expensive)
    private void generateMap() throws GameActionException {
        int visionRadiusSquared = robotController.getType().visionRadiusSquared;
        MapLocation[] sensedLocations = robotController.getAllLocationsWithinRadiusSquared( s_start, visionRadiusSquared );

        for ( MapLocation location : sensedLocations  ) {
            double passability = robotController.senseRubble( location );
            double distance = location.distanceSquaredTo( s_start );

            double g = Double.POSITIVE_INFINITY;
            double rhs = Double.POSITIVE_INFINITY;

            if ( location == s_goal ) {
                rhs = 0;
            }

            DSLLocation s = new DSLLocation( location, passability, distance, g, rhs );

            for ( Direction direction : Utils.directions ) {
                MapLocation adjLoc = robotController.adjacentLocation( direction );
                if ( robotController.canSenseLocation( adjLoc ) ) {
                    s.addConnection( adjLoc );
                }
            }

            locations.add( s );
        }

        //DSLKey key = new DSLKey( h( s_start, s_goal ), 0 );
        //DSLEntry entry = new DSLEntry( s_goal, key );
    }

    private void initialize() throws GameActionException {
        // Initialize priority queue to store DSLEntries
        inconsistencies = new PriorityQueue<DSLEntry>( 10, new DSLComparator() );

        // Initialize set of locations to search
        locations = new HashSet<DSLLocation>();

        k_m = 0;
        generateMap();
    }

    private DSLLocation getLocation( MapLocation targetLocation ) {
        for ( DSLLocation location : locations ) {
            if ( location.getLocation() == targetLocation ) {
                return ( location );
            }
        }

        return ( null );
    }

    private MapLocation project( MapLocation location ) {
        return location;
    }

    private MapLocation[] succ( int node ) {
        return null;
    }

    private MapLocation[] pred( int node ) {
        return null;
    }

    private DSLKey calculateKey( DSLLocation node ) {
        double a = Math.min( node.g(), node.rhs() ) + h( s_start, node.getLocation() ) + k_m;
        double b = Math.min( node.g(), node.rhs() );
        DSLKey key = new DSLKey( a, b );

        return ( key );
    }

    private int g( int node ) {
        MapLocation[] successors = succ( node );

        return 0;
    }

    private int rhs( int node ) {
        // if ( node == start_id ) {
        //     return ( 0 );
        // }

        MapLocation[] predecessors = pred( node );

        return 0;
    }

    private double h( MapLocation l1, MapLocation l2 ) {
        return ( l1.distanceSquaredTo( l2 ) );
    }

    private void updateNode( DSLLocation node ) {
        if ( node.g() == node.rhs() && locations.contains( node ) ) {
            locations.remove( node );
        } else if ( node.g() != node.rhs() ) {
            DSLKey key = calculateKey( node );
            DSLEntry entry = new DSLEntry( node, key );

            if ( locations.contains( node ) ) {
                locations.remove( node );
            }

            locations.add( node );
        }
    }

    private void computeShortestPath() {

    }

    public Direction plan( MapLocation destination ) throws GameActionException {
        return ( plan( destination, true ) );
    }

    public Direction plan( MapLocation destination, boolean forceMove ) throws GameActionException {
        // TODO: if destination == s_goal (do not do initialization again just grab next in set) or maybe just add a bool or replan or not?
        // Set goal and start
        s_goal = destination;
        s_start = robotController.getLocation();

        // Project goal onto start radius
        s_goal = project( s_goal );

        // Init stuff
        initialize();

        // Update costs
        for ( DSLLocation location : locations ) {
           // TODO: update costs

        }


        // Find best path to goal
        computeShortestPath();

        // TODO: get best location that is succ from start
        // TODO: get direction to loc and return that
        return null;
    }
}
