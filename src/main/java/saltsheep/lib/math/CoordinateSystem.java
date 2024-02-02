package saltsheep.lib.math;

import javax.annotation.Nonnull;

public class CoordinateSystem {

    @Nonnull
    protected final Vector xTo;
    @Nonnull
    protected final Vector yTo;
    @Nonnull
    protected final Vector zTo;

    public CoordinateSystem(@Nonnull Vector xTo, @Nonnull Vector yTo, @Nonnull Vector zTo) {
        this.xTo = xTo;
        this.yTo = yTo;
        this.zTo = zTo;
    }

    public DotAbs getDotAbs(Dot dot) {
        return new DotAbs(dot.x() * (xTo.xToAbs() + yTo.xToAbs + zTo.xToAbs),
                dot.y() * (xTo.yToAbs() + yTo.yToAbs + zTo.yToAbs),
                dot.z() * (xTo.zToAbs() + yTo.zToAbs + zTo.zToAbs));
    }

    public Dot getDotUnder(Dot dot) {
        DotAbs asAbs = dot.getAbs();
        double x = asAbs.xAbs() / (xTo.xToAbs + yTo.xToAbs + zTo.xToAbs);
        double y = asAbs.yAbs() / (xTo.yToAbs + yTo.yToAbs + zTo.yToAbs);
        double z = asAbs.zAbs() / (xTo.zToAbs + yTo.zToAbs + zTo.zToAbs);
        return new Dot(x, y, z, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CoordinateSystem) {
            return this.xTo.equals(obj) && this.yTo.equals(obj) && this.zTo.equals(obj);
        } else
            return false;
    }

}
