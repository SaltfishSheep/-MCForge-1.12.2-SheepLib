package saltsheep.lib.common;

public interface Function {

    public static interface Void extends Function {
        public void invoke(Object[] args);
    }

    public static interface Return extends Function {
        public Object invoke(Object[] args);
    }

}
