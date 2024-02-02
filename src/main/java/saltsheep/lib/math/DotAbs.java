package saltsheep.lib.math;

public class DotAbs extends Dot {

    //*You can ignore the exception,it will never appear.
    public DotAbs(double x, double y, double z) {
        super(x, y, z, CoordinateSystemAbs.INSTANCE);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double xAbs() {
        return this.x;
    }

    public double yAbs() {
        return this.y;
    }

    public double zAbs() {
        return this.z;
    }

    public DotAbs getAbs() {
        return this;
    }

    public boolean isAbs() {
        return true;
    }

    public Dot copy() {
        return new DotAbs(this.x, this.y, this.z);
    }

}
