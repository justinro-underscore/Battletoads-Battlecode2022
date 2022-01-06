package battletoads.utils;

import battlecode.common.*;

/**
 * Utility functions that are used to manipulate a RobotController
 */
public class RobotUtils {

    /**
     * Moves a given robot to a given location
     * @param rc Robot to move
     * @param loc The location to move to
     * @return True if the robot has reached the destination
     */
    public static boolean moveTo(RobotController rc, MapLocation loc) throws GameActionException {
        // TODO Varun make this better
        // TODO Add different return results to show different reasons the robot stopped moving
        Direction dir = rc.getLocation().directionTo(loc);
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
        return rc.getLocation().equals(loc);
    }
}
