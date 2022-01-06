package battletoads.runners.droids;

import battlecode.common.*;
import battletoads.utils.Logging;
import battletoads.utils.RobotUtils;
import battletoads.utils.Utils;

public class MinerRunner {

    // Store sense points here, cuts down on bytecode
    private static final int[][] SENSE_POINTS = Utils.CIRCLE_POINTS.get(RobotType.MINER.visionRadiusSquared);

    private static enum MinerState {
        SEARCHING,
        MOVING,
        MINING
    }

    private static MinerState currState = MinerState.SEARCHING;
    private static MapLocation mineLoc;

    /**
     * Run a single turn for a Miner.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    public static void run(RobotController rc) throws GameActionException {
        boolean done = false;
        while (!done) {
            switch (currState) {
                case SEARCHING: done |= searchAction(rc); break;
                case MOVING: done |= moveAction(rc); break;
                case MINING: done |= mineAction(rc); break;
            }
        }
    }

    /**
     * Runs the search action
     * Finds the next location to move to based on where there is stuff to mine
     * @return True if the robot's move should be done
     */
    private static boolean searchAction(RobotController rc) throws GameActionException {
        mineLoc = findNextMiningSpot(rc);
        currState = MinerState.MOVING;
        return true; // Always return true because the finding algorithm is very bytecode intensive
    }

    /**
     * Runs the move action
     * Navigates to the lead location if it exists
     * Otherwise, walks randomly to try to find a new location
     * @return True if the robot's move should be done
     */
    private static boolean moveAction(RobotController rc) throws GameActionException {
        if (mineLoc != null) {
            // TODO should navigate to the square adjacent to the mining location, since it can still mine from there
            // TODO should verify that no one is currently mining the lead it is going for
            if (RobotUtils.moveTo(rc, mineLoc)) {
                currState = MinerState.MINING;
                return false;
            }
        }
        else {
            // TODO This could be updated so that it's not walking in a random direction but instead navigating somewhere more advantageous
            Direction dir = Utils.directions[Utils.rng.nextInt(Utils.directions.length)];
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
            currState = MinerState.SEARCHING;
            return false; // Causes the robot to search on the same turn
        }
        return true;
    }

    /**
     * Runs the mine action
     * Mines the desired location
     * @return True if the robot's move should be done
     */
    private static boolean mineAction(RobotController rc) throws GameActionException {
        // TODO Add ability to mine gold
        // TODO Add ability to mine around the lead
        while (rc.canMineLead(mineLoc)) {
            rc.mineLead(mineLoc);
        }

        // When we are done, go back to searching for the next lead
        if (rc.senseLead(mineLoc) == 0) {
            currState = MinerState.SEARCHING;
            return false;
        }
        return true;
    }

    /**
     * Finds the closest location for mining
     * @return
     */
    private static MapLocation findNextMiningSpot(RobotController rc) {
        // TODO This algorithm is pretty bytecode intensive, could work on that a bit
        MapLocation currLoc = rc.getLocation();
        // Look through all points around current location that the robot can sense
        for (int[] point : SENSE_POINTS) {
            MapLocation loc = new MapLocation(currLoc.x + point[0], currLoc.y + point[1]);

            try {
                // Verify the map location is valid
                if (rc.onTheMap(loc)) {
                    // If there is lead here
                    if (rc.senseLead(loc) > 0) {
                        // Verify there is no miner currently mining this
                        RobotInfo localRobot = rc.senseRobotAtLocation(loc); // TODO should be changed to a radius of 2 around the loc when miners are navigating to next to the location
                        if (localRobot == null || localRobot.getType() != RobotType.MINER) {
                            return loc;
                        }
                    }
                }
            }
            // This should never throw an exception
            catch (GameActionException ex) {
                Logging.error(String.format("Robot [%d] threw error while finding next lead at {%d, %d}: %s", rc.getID(), loc.x, loc.y, ex.getMessage()));
            }

        }
        return null;
    }
}
