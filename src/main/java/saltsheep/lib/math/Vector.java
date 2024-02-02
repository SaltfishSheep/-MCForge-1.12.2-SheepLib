package saltsheep.lib.math;

import saltsheep.lib.common.NumberHelper;

public class Vector {

    protected final double xToAbs;
    protected final double yToAbs;
    protected final double zToAbs;

    public static Vector xAbs = new Vector(1, 0, 0);
    public static Vector yAbs = new Vector(0, 1, 0);
    public static Vector zAbs = new Vector(0, 0, 1);

    public Vector(double xToAbs, double yToAbs, double zToAbs) {
        this.xToAbs = xToAbs;
        this.yToAbs = yToAbs;
        this.zToAbs = zToAbs;
    }

    public Vector(Dot from, Dot to) {
        this(to.xAbs() - from.xAbs(), to.yAbs() - from.yAbs(), to.zAbs() - from.zAbs());
    }

    public double xToAbs() {
        return this.xToAbs;
    }

    public double yToAbs() {
        return this.yToAbs;
    }

    public double zToAbs() {
        return this.zToAbs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector) {
            return NumberHelper.isEqual(this.xToAbs, ((Vector) obj).xToAbs) && NumberHelper.isEqual(this.yToAbs, ((Vector) obj).yToAbs) && NumberHelper.isEqual(this.zToAbs, ((Vector) obj).zToAbs);
        } else
            return false;
    }

}
