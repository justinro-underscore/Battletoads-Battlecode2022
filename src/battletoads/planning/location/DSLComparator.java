package battletoads.planning.location;

import java.util.Comparator;

import battletoads.planning.location.DSLEntry;
import battletoads.planning.location.DSLKey;

public class DSLComparator implements Comparator<DSLEntry> {
    public int compare( DSLEntry e1, DSLEntry e2 ) {
        DSLKey e1Key = e1.get_key();
        DSLKey e2Key = e2.get_key();

        double e1Cost = e1Key.get_a();
        double e2Cost = e2Key.get_a();

        if ( e1Cost == e2Cost ) {
            e1Cost = e1Key.get_b();
            e2Cost = e2Key.get_b();
        }

        return ( (int) ( e1Cost - e2Cost ) );
    }
}
