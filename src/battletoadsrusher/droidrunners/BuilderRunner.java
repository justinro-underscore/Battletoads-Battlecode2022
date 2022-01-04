package battletoadsrusher.droidrunners;

import java.util.Arrays;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battletoadsrusher.Utils;

public class BuilderRunner {

  private static MapLocation enemyArchonLocation;

  public static void run(RobotController rc) throws GameActionException {
    // This means the builder was just spawned
    if (enemyArchonLocation == null) {
      // TODO: Does not take into account if next to multiple archons
      RobotInfo[] nearbyRobots = rc.senseNearbyRobots(rc.getLocation(), 2, rc.getTeam());
      RobotInfo archon = Arrays.stream(nearbyRobots).filter(ri -> ri.getType() == RobotType.ARCHON).findFirst().get();
      MapLocation archonLocation = archon.getLocation();
      Direction enemyArchonDirection = Utils.getDirectionFromVector(archonLocation, rc.getLocation());
      // Radial symmetry
      if (!Utils.CARDINAL_DIRECTIONS.contains(enemyArchonDirection)) {
        enemyArchonLocation = new MapLocation(rc.getMapWidth() - archonLocation.x - 1, rc.getMapHeight() - archonLocation.y - 1);
      }
      // Vertical symmetry
      else if (enemyArchonDirection == Direction.NORTH || enemyArchonDirection == Direction.SOUTH) {
        enemyArchonLocation = new MapLocation(archonLocation.x, rc.getMapHeight() - archonLocation.y - 1);
      }
      // Horizontal symmetry
      else {
        enemyArchonLocation = new MapLocation(rc.getMapWidth() - archonLocation.x - 1, archonLocation.y);
      }
    }

    Direction dir = Utils.getDirectionFromVector(rc.getLocation(), enemyArchonLocation);
    if (rc.canMove(dir)) {
      rc.move(dir);
    }
  }
}
