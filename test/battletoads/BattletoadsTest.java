package battletoads;

import static org.junit.Assert.*;
import org.junit.Test;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battletoads.utils.Utils;

public class BattletoadsTest {

	@Test
	public void testGetDirectionFromVector() {
        MapLocation ml1 = new MapLocation(5, 5);
        MapLocation ml2 = new MapLocation(5, 5);
        assertEquals(Direction.CENTER, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(5, 10);
        assertEquals(Direction.NORTH, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(10, 10);
        assertEquals(Direction.NORTHEAST, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(10, 5);
        assertEquals(Direction.EAST, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(10, 0);
        assertEquals(Direction.SOUTHEAST, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(5, 0);
        assertEquals(Direction.SOUTH, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(0, 0);
        assertEquals(Direction.SOUTHWEST, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(0, 5);
        assertEquals(Direction.WEST, Utils.getDirectionFromVector(ml1, ml2));

        ml2 = new MapLocation(0, 10);
        assertEquals(Direction.NORTHWEST, Utils.getDirectionFromVector(ml1, ml2));
	}
  
}
