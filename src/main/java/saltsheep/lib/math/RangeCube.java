package saltsheep.lib.math;

public class RangeCube extends Range {

    public final DotAbs posMin;
    public final DotAbs posMax;

    public RangeCube(double x1, double y1, double z1, double x2, double y2, double z2) {
        this(new DotAbs(x1, y1, z1), new DotAbs(x2, y2, z2));
    }

    public RangeCube(Dot pos1, Dot pos2) {
        this.posMin = new DotAbs(Math.min(pos1.xAbs(), pos2.xAbs()), Math.min(pos1.yAbs(), pos2.yAbs()), Math.min(pos1.zAbs(), pos2.zAbs()));
        this.posMax = new DotAbs(Math.max(pos1.xAbs(), pos2.xAbs()), Math.max(pos1.yAbs(), pos2.yAbs()), Math.max(pos1.zAbs(), pos2.zAbs()));
    }

    @Override
    public boolean isInRange(Dot dot) {
        DotAbs asAbs = dot.getAbs();
        return asAbs.xAbs() >= posMin.xAbs() && asAbs.xAbs() <= posMax.xAbs() && asAbs.yAbs() >= posMin.yAbs() && asAbs.yAbs() <= posMax.yAbs() && asAbs.zAbs() >= posMin.zAbs() && asAbs.zAbs() <= posMax.zAbs();
    }

}
