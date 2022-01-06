package battletoads.utils;

import battlecode.common.*;

/**
 * Utility functions that are used to manipulate a RobotController
 */
public class RobotUtils {

    // Used to define how the moveTo function ended
    public static enum MoveToResult {
        /* The following means we did move */
        TARGET_REACHED, // Success! We successfully made it to the target
        MOVED, // We moved closer to the target

        /* The following means we didn't move */
        TARGET_BLOCKED, // If the target location is blocked and the robot is next to the target
        COOLDOWN_REACHED, // If the robot cannot move any longer
        PATH_BLOCKED, // If there is a robot in the way TODO Varun make our algorithm better so that this doesn't end the turn
    }

    /**
     * Returns whether or not a move was successful based on the move result
     * Anytime an enum is added to MoveToResult, it should be added here
     * @param res The result to test
     * @return True if move was successful
     */
    public static boolean moveToSuccessful(MoveToResult res) {
        switch (res) {
            case TARGET_REACHED:
            case MOVED:
                return true;
            case TARGET_BLOCKED:
            case COOLDOWN_REACHED:
            case PATH_BLOCKED:
        }
        return false;
    }

    /**
     * Moves a given robot to a given location
     * TODO Check bytecode usage
     * @param rc Robot to move
     * @param loc The location to move to
     * @return True if the robot has reached the destination
     */
    public static boolean moveTo(RobotController rc, MapLocation loc) throws GameActionException {
        // TODO Varun make this better (this should work the same as moveToVerbose)
        Direction dir = rc.getLocation().directionTo(loc);
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
        return rc.getLocation().equals(loc);
    }

    /**
     * Moves a given robot to a given location
     * This returns a more explicit answer to how a move was made
     * TODO Check bytecode usage
     * @param rc Robot to move
     * @param loc The location to move to
     * @return The result of the attempted move
     */
    public static MoveToResult moveToVerbose(RobotController rc, MapLocation loc) throws GameActionException {
        // TODO Varun make this better (this should work the same as moveTo)
        if (!rc.isMovementReady()) {
            return MoveToResult.COOLDOWN_REACHED;
        }

        Direction dir = rc.getLocation().directionTo(loc);
        MapLocation nextLocation = rc.getLocation().add(dir);
        if (rc.isLocationOccupied(nextLocation)) {
            if (nextLocation.equals(loc)) {
                return MoveToResult.TARGET_BLOCKED;
            }
            // TODO We should change our algorithm so that this does not end the move
            return MoveToResult.PATH_BLOCKED;
        }

        // Move the robot, IDEALLY shouldn't throw an exception
        if (nextLocation.equals(loc)) {
            return MoveToResult.TARGET_REACHED;
        }
        return MoveToResult.MOVED;
    }
}
