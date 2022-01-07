package battletoads.planning;

import java.util.PriorityQueue;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import battletoads.planning.util.*;

/**
 * Path planning using a simple, short sighted, greedy search
 */
public class GreedyPlanner {

    // Number of adjacent tiles
    private final int NUM_ADJACENT = 8;

    // Min heap to get best next tile
    private PriorityQueue<Location> locations;

    // Robot controller
    private RobotController robotController;

    /**
     * Constructor to initialize planner with surrounding tiles
     */
    public GreedyPlanner( RobotController robotController ) {
        // Set robot controller
        this.robotController = robotController;

        // Initialize priority queue with surrounding tiles
        locations = new PriorityQueue<Location>( NUM_ADJACENT, new LocationComparator() );
    }

    /**
     * Try and get best next direction to get to final destination
     *
     * @return Best direction to move to reach goal
     * @throws GameActionException
     */
    public Direction GreedyPlan( Direction direction ) throws GameActionException {
        MapLocation newLocation = robotController.getLocation().add( direction );
        Direction nextDirection = GreedyPlan( newLocation, 1, true );

        return ( nextDirection );
    }

    /**
     * Try and get best next direction to get to final destination
     *
     * @return Best direction to move to reach goal
     * @throws GameActionException
     */
    public Direction GreedyPlan( MapLocation destination ) throws GameActionException {
        return ( GreedyPlan( destination, 1, true ) );
    }

    /**
     * Try and get best next direction to get to final destination
     *
     * @return Best direction to move to reach goal
     * @throws GameActionException
     */
    // TODO: add option to increase sensing radius
    public Direction GreedyPlan( MapLocation destination, int sensingRadius, boolean forceMove ) throws GameActionException {
        // Default to center direction (this only happens if we cannot move in any direction)
        Direction nextDirection = Direction.CENTER;

        if ( locations.isEmpty() ) {
            // Add adjacent robot tiles to queue
            for ( Direction direction : Directions.directions ) {
                MapLocation adjLoc = robotController.adjacentLocation( direction );
                if ( robotController.onTheMap( adjLoc ) ) {
                    double passability = robotController.senseRubble( adjLoc );
                    double distance = adjLoc.distanceSquaredTo( destination );
                    locations.add( new Location( adjLoc, passability, distance ) );
                }
            }
        }

        // Loop through adjacent locations to find best option
        while ( false == locations.isEmpty() ) {
            Location location = locations.poll();
            Direction direction = robotController.getLocation().directionTo( location.getLocation() );

            // Check if we can move to this location
            if ( robotController.canMove( direction ) ) {
                nextDirection = direction;
                locations.clear();
                break;
            }

            // Quit after checking best location if we do not want to force a movement
            if ( !forceMove ) {
                break;
            }
        }

        return ( nextDirection );
    }
}