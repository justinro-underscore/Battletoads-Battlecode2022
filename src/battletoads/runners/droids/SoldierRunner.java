package battletoads.runners.droids;

import battlecode.common.*;
import battletoads.utils.Utils;

import static battletoads.utils.LogLevel.ERROR;
import static battletoads.utils.Logging.log;

/**
 * Fun class that is going to be rushing the enemy archons
 *
 * Integers 0-3 contain the location of enemy archons. In general
 * this unit should move towards the closest un-choked enemy archon. TODO it may be better to focus on one archon at a time?
 * If there are no more un-choked archons the soldier should either
 * hunt for enemy units around said choked archones orrrrrr play a little
 * defense around our archons. We could decide this by % of type or something
 * of units built after a given round :shrug:
 *
 * @author Noah Brabec
 */
public class SoldierRunner {

    private enum SoldierType {CHOKER, HUNTER, DEFENDER}

    private static SoldierType type = SoldierType.CHOKER;

    private static Team myTeam;


    /**
     * Run a single turn for a Soldier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    public static void run(RobotController rc) throws GameActionException {
        myTeam = rc.getTeam();

        if (type == SoldierType.CHOKER) {
            rush(rc);
        } else {
            hunt(rc);
        }
    }

    private static void rush(RobotController rc) {
        //Walk to enemy base
        //Stand around enemy base
        //Shoot units first, then archone
        //Maybe shoot towers above all?
        //Find out if you can shoot lead


        //Are we standing near the base?
        //If we aren't move towards it and return

        //If we are then shoot em up


        RobotInfo[] robots = rc.senseNearbyRobots();
        for (RobotInfo info : robots) {
            if (info.getTeam() != myTeam && info.getType() == RobotType.ARCHON) {
                if (rc.getLocation().isAdjacentTo(info.getLocation())) {
                    try {
                        if (rc.isActionReady()) {
                            rc.attack(info.getLocation());
                        }
                        //We've attacked and are blocking, we can return
                        return;
                    } catch (GameActionException e) {
                        log(ERROR, "Can't attack here");
                    }
                }
            }
        }

        //Nothing worth shooting, and if we got here we aren't by archon, move towards it
        moveToArchon(rc);
    }

    private static void hunt(RobotController rc) {

    }

    private static void moveToArchon(RobotController rc) {
        try {
            rc.move(Direction.EAST);
        } catch (GameActionException e) {
            log(ERROR, "Ruh roh can't move");
        }
    }
}
