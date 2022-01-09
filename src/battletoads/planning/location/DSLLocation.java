package battletoads.planning.location;

import java.util.Set;
import java.util.HashSet;

import battlecode.common.MapLocation;

/**
 * Location class to store information about a MapLocation
 */
public class DSLLocation {
    // Map Location
    private MapLocation location;

    // Passability
    private double passability;

    // Distance weight
    private double distance;

    // A* and D* lite values
    private double m_g;
    private double m_rhs;

    // Set of connections/neighbors
    private Set<MapLocation> connections;

    /**
     * Constructor to initialize a location
     */
    public DSLLocation( MapLocation location, double passability, double distance, double g, double rhs ) {
        this.location = location;
        this.passability = passability;
        this.distance = distance;

        m_g = g;
        m_rhs = rhs;
        connections = new HashSet<MapLocation>();
    }

    /**
     * Get MapLocation object
     *
     * @return MapLocation
     */
    public MapLocation getLocation() {
        return ( location );
    }

    /**
     * Get passability value of location
     *
     * @return passability double [0.1, 1.0]
     */
    public double getPassability() {
        return ( passability );
    }

    /**
     * Get distance to destination from this location
     *
     * @return distance to destination from location
     */
    public double getDistance() {
        return ( distance );
    }

    /**
     * Get g value
     *
     * @return g value from A*
     */
    public double g() {
        return ( m_g );
    }

    /**
     * Get rhs value
     *
     * @return rhs value from D* lite
     */
    public double rhs() {
        return ( m_rhs );
    }

    /**
     * Add location to connections set
     *
     * @param Location to add to set
     */
    public void addConnection( MapLocation loc ) {
        connections.add( loc );
    }

    /**
     * Get set of connections
     *
     * @return set of neighbors
     */
    public Set<MapLocation> getConnections() {
        return ( connections );
    }
}
