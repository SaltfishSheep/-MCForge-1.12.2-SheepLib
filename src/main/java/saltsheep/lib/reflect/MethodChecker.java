package saltsheep.lib.reflect;

import java.lang.reflect.Method;

public class MethodChecker {

    /**
     * Hum,I think it has no useful.
     */
    public static boolean isSame(Method ma, Method mb) {
        if (ma == mb)
            return true;
        return isOverride(ma, mb);
    }

    /**
     * @return true if ma's return type and params type same as mb.
     */
    public static boolean isOverride(Method ma, Method mb) {
        if (ma.getReturnType() != mb.getReturnType())
            return false;
        Class<?>[] upc = ClassChecker.corrects(ma.getParameterTypes());
        Class<?>[] downc = ClassChecker.corrects(mb.getParameterTypes());
        if (upc.length != downc.length)
            return false;
        for (int i = 0; i < upc.length; i++)
            if (upc[i] != downc[i])
                return false;
        return true;
    }

    /**
     * @return true if ma's name same as mb.
     */
    public static boolean isOverload(Method ma, Method mb) {
        return ma.getName().equals(mb.getName());
    }

    /**
     * @return true if rude's return same as accurate, and all params of accurate is the son type of the rude one, use for method sort.
     */
    public static boolean isAccurate(Method rude, Method accurate) {
        if (rude.getReturnType() != accurate.getReturnType())
            return false;
        Class<?>[] rudec = ClassChecker.corrects(rude.getParameterTypes());
        Class<?>[] accuc = ClassChecker.corrects(accurate.getParameterTypes());
        if (rudec.length != accuc.length)
            return false;
        for (int i = 0; i < rudec.length; i++)
            if (!rudec[i].isAssignableFrom(accuc[i]))
                return false;
        return true;
    }

}
