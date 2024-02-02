package saltsheep.lib.reflect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class ClassChecker {

    private static Map<Class<?>, Class<?>> corrects = new HashMap<Class<?>, Class<?>>();

    static {
        corrects.put(int.class, Integer.class);
        corrects.put(long.class, Long.class);
        corrects.put(float.class, Float.class);
        corrects.put(double.class, Double.class);
        corrects.put(short.class, Short.class);
        corrects.put(byte.class, Byte.class);
        corrects.put(boolean.class, Boolean.class);
        corrects.put(char.class, Character.class);
    }

    public static Class<?> correct(Class<?> c) {
        if (c.isPrimitive())
            return corrects.get(c);
        return c;
    }

    public static Class<?>[] corrects(Class<?>[] c) {
        List<Class<?>> list = Lists.newLinkedList();
        for (Class<?> each : c)
            list.add(correct(each));
        return list.toArray(new Class<?>[c.length]);
    }

    public static boolean canPass(Class<?> require, Class<?> input) {
        return correct(require).isAssignableFrom(correct(input));
    }

    public static boolean canPass(Class<?>[] requires, Class<?>[] inputs) {
        if (requires.length != inputs.length)
            return false;
        for (int i = 0; i < requires.length; i++)
            if (!canPass(requires[i], inputs[i]))
                return false;
        return true;
    }

}
