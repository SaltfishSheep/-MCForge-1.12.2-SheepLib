package saltsheep.lib.math;

public abstract class Range {

    public static final Range NOMATCH = new Range() {
        public boolean isInRange(Dot dot) {
            return false;
        }
    };

    public abstract boolean isInRange(Dot dot);

}
