package battletoads.runners.droids;

import battlecode.common.*;
import battletoads.utils.RobotUtils;
import battletoads.utils.Utils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

import static battletoads.utils.LogLevel.ERROR;
import static battletoads.utils.Logging.*;
import static battletoads.utils.Utils.*;

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

    //RUSHER => Move to an enemy archon and attempt to choke it
    //HUNTER => Just stand and shoot currently
    //DEFENDER => Just stand and shoot currently
    public enum SoldierType {RUSHER, HUNTER, DEFENDER}

    private static SoldierType type = SoldierType.RUSHER;

    private static Team myTeam;

    private static MapLocation chokeLocation = null;

    private static MapLocation targetArchon = null;
    private static MapLocation scoutLocation = null;

    private static MapLocation[] possibleEnemyArchonDirections = new MapLocation[3];
    private static int startingDirectionIdx = 0;


    /**
     * Run a single turn for a Soldier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     * @param rc us
     * @param whatShouldIDoDaddy what should I do????
     */
    public static void run(RobotController rc, SoldierType whatShouldIDoDaddy) throws GameActionException {
        myTeam = rc.getTeam();
        if (whatShouldIDoDaddy != null) {
            type = whatShouldIDoDaddy;
        }


        if (type == SoldierType.RUSHER) {
            rush(rc);
        } else if (type == SoldierType.HUNTER) {
            hunt(rc);
        } else if (type == SoldierType.DEFENDER) {
            fallBackShoot(rc, null);
        }
    }

    /**
     * Main goals, get next to an archon. Thats it.
     * @param rc
     */
    private static void rush(RobotController rc) {
        //Walk to enemy base
        //Stand around enemy base
        //Shoot units first, then archone
        //Maybe shoot towers above all?
        //Find out if you can shoot lead
        //Are we standing near the base?
        //If we aren't move towards it and return
        //If we are then shoot em up

        if (targetArchon == null) {
            findAnArchon(rc, true);
        }

        //We still don't have one, probably means we are
        //At the begginning of the game. Need to guess a
        //direction to walk. This attempts a move and
        //a shoot action so we return afterwards
        if (scoutLocation == null && targetArchon == null) {
            scoutALocation(rc);
            return;
        }

        RobotInfo[] robots = rc.senseNearbyRobots();
        for (RobotInfo info : robots) {
            if (info.getTeam() != myTeam && info.getType() == RobotType.ARCHON) {
                if (rc.getLocation().isAdjacentTo(info.getLocation())) {
                    //If we are next to the archon, attack it
                    try {
                        if (rc.isActionReady()) {
                            rc.attack(info.getLocation());
                        }
                        reportEnemyArchon(rc, info);
                        //We've attacked and are blocking, we can return
                        return;
                    } catch (GameActionException e) {
                        error("Can't attack here");
                        return;
                    }
                } else {
                    //We need to attempt to choke it
                    chokeArchon(rc, info);
                }
            }
        }

        //Nothing worth shooting, and if we got here we aren't by an archon, move towards it
        moveToArchon(rc);
        fallBackShoot(rc, null);
    }

    //Will find the first enemy and walk around it and stuff
    private static void hunt(RobotController rc) {
        findAnArchon(rc, false);
        moveToArchon(rc);
        fallBackShoot(rc, null);
    }

    private static void moveToArchon(RobotController rc) {
        if (targetArchon != null) {
            try {
                RobotUtils.moveTo(rc, targetArchon);
            } catch (GameActionException e) {
                error("Ruh roh can't move");
            }
        } else {
            try {
                RobotUtils.moveTo(rc, scoutLocation);
            } catch (GameActionException e) {
                error("Ruh roh can't move");
            }
        }
    }

    /**
     * Attempt to move next to the enemy archon. Shoots something
     * otherwise as to not waste a move
     * @param rc us
     * @param enemyArchon them
     */
    private static void chokeArchon(RobotController rc, RobotInfo enemyArchon) {
        //Find a location next to the archon
        //Move to it
        //Stand in it
        List<MapLocation> locations = new ArrayList<MapLocation>();
        try {
            locations = Arrays.asList(rc.getAllLocationsWithinRadiusSquared(enemyArchon.getLocation(), 1));
        } catch (GameActionException e) {
            error("ruh roh can't choke");
        }

        if (locations.isEmpty()) {
            //No more places to choke, time to hunt I guess
            type = SoldierType.HUNTER;
            hunt(rc);
        }

        if (chokeLocation != null) {
            try {
                rc.senseRobotAtLocation(chokeLocation);
            } catch (GameActionException e) {
                error("Sensing for previous choke location failed");
                return;
            }
        }

        chokeLocation = locations.get(0);

        try {
            RobotUtils.moveTo(rc, chokeLocation);
        } catch (GameActionException e) {
            error("Unable to move to choke location");
            fallBackShoot(rc, null);
        }
    }



    /**
     * Fallback method that should be used when we failed to move
     * but we still want to do some kind of d a m a g e
     *
     * @param rc               our self
     * @param targetOfInterest nullable, optional target we prefer to shoot at
     */
    private static void fallBackShoot(RobotController rc, MapLocation targetOfInterest) {
        if (targetOfInterest != null) {
            if (rc.canAttack(targetOfInterest)) {
                try {
                    rc.attack(targetOfInterest);
                    return;
                } catch (GameActionException e) {
                    error("Fallback attack failed");
                }
            }
        }

        RobotInfo[] locations = rc.senseNearbyRobots();

        RobotInfo target = null;
        for (RobotInfo info : locations) {
            if (info.getTeam() != rc.getTeam()) {
                /*
                 * Shoot watchtowers first, then archons, then anything else
                 */
                if (target == null) {
                    target = info;
                } else if (info.getType() == RobotType.WATCHTOWER) {
                    target = info;
                } else if (info.getType() == RobotType.ARCHON && target.getType() != RobotType.WATCHTOWER) {
                    target = info;
                } else {
                    target = info;
                }
            }
        }

        if (target != null) {
            if (rc.canAttack(target.getLocation())) {
                try {
                    rc.attack(target.getLocation());
                    return;
                } catch (GameActionException e) {
                    error("Fallback attack failed");
                }
            }
        }
    }

    /**
     * All this needs to do is attempt to set targetArchon
     * The loop unraveling is totally intentional for byte
     * code saving cost
     * @param rc us
     */
    private static void findAnArchon(RobotController rc, boolean careIfChoked) {
        try {
            int locationInt = rc.readSharedArray(ARR_IDX_SOLDIER_1);
            if (locationInt != 0) {
                MapLocation location = decodeFromInt(locationInt, careIfChoked);
                if (location != null) {
                    targetArchon = location;
                    return;
                }
            }
            locationInt = rc.readSharedArray(ARR_IDX_SOLDIER_2);
            if(locationInt != 0) {
                MapLocation location = decodeFromInt(locationInt,careIfChoked);
                if (location != null) {
                    targetArchon = location;
                    return;
                }
            }
            locationInt = rc.readSharedArray(ARR_IDX_SOLDIER_3);
            if(locationInt != 0) {
                MapLocation location = decodeFromInt(locationInt, careIfChoked);
                if (location != null) {
                    targetArchon = location;
                    return;
                }
            }
            locationInt = rc.readSharedArray(ARR_IDX_SOLDIER_4);
            if(locationInt != 0) {
                MapLocation location = decodeFromInt(locationInt, careIfChoked);
                if (location != null) {
                    targetArchon = location;
                }
            }
        } catch (GameActionException e) {
            error("CANT READ A DUMBO ARRAY BLAH BLAH BLAH");
        }
    }

    /**
     * Bit masking is for suckas
     * @param locationInt encoded thing
     * @return A valid map location
     */
    private static MapLocation decodeFromInt(Integer locationInt, boolean careIfChoked) {
        String locationString = locationInt.toString();
        String xCord = locationString.substring(0, 1);
        String yCord = locationString.substring(2, 3);
        String isChoked = locationString.substring(4);

        if (isChoked.equals("1") && careIfChoked) {
            return null;
        }
        try {
            return new MapLocation(Integer.getInteger(xCord), Integer.getInteger(yCord));
        } catch(NullPointerException e) {
            return null;
        }
    }

    private static void scoutALocation(RobotController rc) {
        if (possibleEnemyArchonDirections[0] == null) {
            MapLocation currLocation = rc.getLocation();

            // Radial symmetry
            MapLocation enemyArchonLocation1 = new MapLocation(rc.getMapWidth() - currLocation.x - 1, rc.getMapHeight() - currLocation.y - 1);
            possibleEnemyArchonDirections[0] = enemyArchonLocation1;

            // Vertical symmetry
            MapLocation enemyArchonLocation2 = new MapLocation(currLocation.x, rc.getMapHeight() - currLocation.y - 1);
            possibleEnemyArchonDirections[1] = enemyArchonLocation2;

            // Horizontal symmetry
            MapLocation enemyArchonLocation3 = new MapLocation(rc.getMapWidth() - currLocation.x - 1, currLocation.y);
            possibleEnemyArchonDirections[2] = enemyArchonLocation3;
        }

        Random random = new Random();
        scoutLocation = possibleEnemyArchonDirections[random.nextInt(3)];

        try {
            RobotUtils.moveTo(rc, scoutLocation);
        } catch (GameActionException e) {
            error("Dammit");
        }

        fallBackShoot(rc, null);
    }

    //Assumes rc can see every square around the enemy archon
    private static void reportEnemyArchon(RobotController rc, RobotInfo enemyArchon) {
        String location = "";
        //XXYYC
        //C = Boolean if archon is choked
        location = location + enemyArchon.getLocation().x + enemyArchon.getLocation().y + "0";
        int locationInt = Integer.parseInt(location);

        try {
            if (rc.readSharedArray(ARR_IDX_SOLDIER_1) == locationInt
                    || rc.readSharedArray(ARR_IDX_SOLDIER_2) == locationInt
                    || rc.readSharedArray(ARR_IDX_SOLDIER_3) == locationInt
                    || rc.readSharedArray(ARR_IDX_SOLDIER_4) == locationInt) {
                return;
            }

            if (rc.readSharedArray(ARR_IDX_SOLDIER_1) == 0) {
                rc.writeSharedArray(ARR_IDX_SOLDIER_1, locationInt);
            } else if (rc.readSharedArray(ARR_IDX_SOLDIER_2) == 0) {
                rc.writeSharedArray(ARR_IDX_SOLDIER_2, locationInt);
            } else if (rc.readSharedArray(ARR_IDX_SOLDIER_3) == 0) {
                rc.writeSharedArray(ARR_IDX_SOLDIER_3, locationInt);
            } else if (rc.readSharedArray(ARR_IDX_SOLDIER_4) == 0) {
                rc.writeSharedArray(ARR_IDX_SOLDIER_4, locationInt);
            }

        } catch (GameActionException e) {
            error("WAAAWAAA");
        }
    }
}
