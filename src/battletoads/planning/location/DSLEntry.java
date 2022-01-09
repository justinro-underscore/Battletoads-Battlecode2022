package battletoads.planning.location;

import battletoads.planning.location.DSLLocation;
import battletoads.planning.location.DSLKey;

public class DSLEntry {
    private DSLLocation m_id;
    private DSLKey m_key;

    public DSLEntry( DSLLocation id, DSLKey key ) {
        m_id = id;
        m_key = key;
    }

    public DSLLocation get_id() {
        return ( m_id );
    }

    public DSLKey get_key() {
        return ( m_key );
    }

    public void set_key( DSLKey key ) {
        m_key = key;
    }

    public void update_key( int a, int b ) {
        m_key.set_a( a );
        m_key.set_b( b );
    }
}
