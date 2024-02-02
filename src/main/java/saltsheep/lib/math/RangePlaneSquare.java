package saltsheep.lib.math;

public class RangePlaneSquare extends Range {

    public final double xMin;
    public final double xMax;
    public final double yMin;
    public final double yMax;

    public RangePlaneSquare(double x1, double y1, double x2, double y2) {
        this.xMin = Math.min(x1, x2);
        this.xMax = Math.max(x1, x2);
        this.yMin = Math.min(y1, y2);
        this.yMax = Math.max(y1, y2);
    }

    //*Default as XZ
    @Override
    public boolean isInRange(Dot dot) {
        return this.isInRange(dot, Type.XZ);
    }

    public boolean isInRange(Dot dot, Type type) {
        switch (type) {
            case XY:
                return this.isBetween(dot.xAbs(), dot.yAbs());
            case XZ:
                return this.isBetween(dot.xAbs(), dot.zAbs());
            case YZ:
                return this.isBetween(dot.yAbs(), dot.zAbs());
            default:
                return false;
        }
    }

    public boolean isBetween(double x, double y) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
    }

    public static enum Type {
        XY, XZ, YZ;
    }

}
