package battletoads.planning;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import battletoads.planning.ASPlanner;

public class ASPlannerTest {

    private static ASPlanner planner;

    private static MapLocation start;

    private static int radiusSquared;

    @BeforeClass
    public static void setUp() {
        planner = new ASPlanner( null );
        start = new MapLocation( 10, 10 );

        radiusSquared = 25;
    }

	@Test
	public void testProjectQuadOne() {
        MapLocation goal = new MapLocation( 15, 21 );

        MapLocation expected = new MapLocation( 12, 14 );
        MapLocation projected = planner.project( start, goal, radiusSquared );

        assertEquals( expected.x, projected.x );
        assertEquals( expected.y, projected.y );
	}

	@Test
	public void testProjectQuadTwo() {
        MapLocation goal = new MapLocation( 15, 4 );

        MapLocation expected = new MapLocation( 13, 6 );
        MapLocation projected = planner.project( start, goal, radiusSquared );

        assertEquals( expected.x, projected.x );
        assertEquals( expected.y, projected.y );
	}

	@Test
	public void testProjectQuadThree() {
        MapLocation goal = new MapLocation( 3, 8 );

        MapLocation expected = new MapLocation( 5, 8 );
        MapLocation projected = planner.project( start, goal, radiusSquared );

        assertEquals( expected.x, projected.x );
        assertEquals( expected.y, projected.y );
	}

	@Test
	public void testProjectQuadFour() {
        MapLocation goal = new MapLocation( 3, 15 );

        MapLocation expected = new MapLocation( 5, 12 );
        MapLocation projected = planner.project( start, goal, radiusSquared );

        assertEquals( expected.x, projected.x );
        assertEquals( expected.y, projected.y );
	}

    @Test
	public void testProjectInside() {
        MapLocation goal = new MapLocation( 12, 12 );

        MapLocation expected = new MapLocation( 13, 13 );
        MapLocation projected = planner.project( start, goal, radiusSquared );

        assertEquals( expected.x, projected.x );
        assertEquals( expected.y, projected.y );
	}
}
