package saltsheep.lib.math;

import javax.annotation.Nonnull;

public class Dot {

    @Nonnull
    protected final double x;
    @Nonnull
    protected final double y;
    @Nonnull
    protected final double z;
    @Nonnull
    protected final CoordinateSystem systemIn;

    public Dot(double x, double y, double z, CoordinateSystem systemIn) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.systemIn = systemIn;
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
        return systemIn.getDotAbs(this).x;
    }

    public double yAbs() {
        return systemIn.getDotAbs(this).y;
    }

    public double zAbs() {
        return systemIn.getDotAbs(this).z;
    }

    public DotAbs getAbs() {
        return systemIn.getDotAbs(this);
    }

    public boolean isAbs() {
        return systemIn instanceof CoordinateSystemAbs;
    }

    public Dot copy() {
        return new Dot(this.x, this.y, this.z, this.systemIn);
    }

}
