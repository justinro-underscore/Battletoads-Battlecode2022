package battletoadsrusher.droidrunners;

import battlecode.common.*;
import battletoadsrusher.Utils;

public class ArchonRunner {

    private static Direction[] possibleEnemyArchonDirections = new Direction[3];
    private static int startingDirectionIdx = 0;

    /**
     * Run a single turn for an Archon.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    public static void run(RobotController rc) throws GameActionException {
        if (possibleEnemyArchonDirections[0] == null) {
            MapLocation currLocation = rc.getLocation();

            // Radial symmetry
            MapLocation enemyArchonLocation1 = new MapLocation(rc.getMapWidth() - currLocation.x - 1, rc.getMapHeight() - currLocation.y - 1);
            possibleEnemyArchonDirections[0] = Utils.getDirectionFromVector(currLocation, enemyArchonLocation1);

            // Vertical symmetry
            MapLocation enemyArchonLocation2 = new MapLocation(currLocation.x, rc.getMapHeight() - currLocation.y - 1);
            possibleEnemyArchonDirections[1] = Utils.getDirectionFromVector(currLocation, enemyArchonLocation2);

            // Horizontal symmetry
            MapLocation enemyArchonLocation3 = new MapLocation(rc.getMapWidth() - currLocation.x - 1, currLocation.y);
            possibleEnemyArchonDirections[2] = Utils.getDirectionFromVector(currLocation, enemyArchonLocation3);
        }

        for (int i = 0; i < 3; i++) {
            Direction dir = possibleEnemyArchonDirections[(i + startingDirectionIdx) % 3];
            if (rc.canBuildRobot(RobotType.BUILDER, dir)) {
                rc.buildRobot(RobotType.BUILDER, dir);
            }
        }
        startingDirectionIdx++;
    }
}
