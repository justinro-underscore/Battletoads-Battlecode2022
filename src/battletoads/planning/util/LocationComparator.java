package battletoads.planning.util;

import java.util.Comparator;

// TODO: update this to use better cost function (look at cool down calculation...)
public class LocationComparator implements Comparator<Location> {
    public int compare( Location l1, Location l2 ) {
        double l1Cost = l1.getDistance() + 0.5 / l1.getPassability();
        double l2Cost = l2.getDistance() + 0.5 / l2.getPassability();

        return ( (int) ( l1Cost - l2Cost ) );
    }
}
