package battletoads.planning.location;

public class DSLKey {
    private double m_a;
    private double m_b;

    public DSLKey( double a, double b ) {
        m_a = a;
        m_b = b;
    }

    public double get_a() {
        return ( m_a );
    }

    public double get_b() {
        return ( m_b );
    }

    public void set_a( int a ) {
        m_a = a;
    }

    public void set_b( int b ) {
        m_b = b;
    }
}
