package saltsheep.lib.math;

public class CoordinateSystemAbs extends CoordinateSystem {

    public static final CoordinateSystemAbs INSTANCE = new CoordinateSystemAbs();

    private CoordinateSystemAbs() {
        super(Vector.xAbs, Vector.yAbs, Vector.zAbs);
    }

}
