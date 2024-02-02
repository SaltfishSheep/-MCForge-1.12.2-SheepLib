package saltsheep.lib.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;

import saltsheep.lib.SheepLib;

public abstract class SheepMethod {

    public static MethodHandles.Lookup lookup;

    static {
        try {
            Field IMPL_LOOKUP = MethodHandles.lookup().getClass().getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP.setAccessible(true);
            lookup = (Lookup) IMPL_LOOKUP.get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    protected final List<Method> methods;

    public SheepMethod(List<Method> ms) {
        this.methods = Lists.newArrayList(ms);
    }

    public SheepMethod(Method[] ms) {
        this.methods = Lists.newArrayList(ms);
    }

    /**
     * Warning!It has bug at method have any param like (Object...)
     * for the param like Object... arg0, you must input Object[].
     */
    public final Object invoke(Object owner, Object... params) {
        if (this.methods.isEmpty()) {
            SheepLib.getLogger().warn("It is a disaster, you are trying to control a undefined method.");
            return null;
        }
        for (Method method : methods) {
            Class<?>[] requires = method.getParameterTypes();
            if (requires.length != params.length)
                continue;
            Class<?>[] inputs = new Class<?>[requires.length];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = params[i].getClass();
            if (!ClassChecker.canPass(requires, inputs))
                continue;
            //System.out.println(((Object[])params[1])[0]);
            Class<?> returnClass = method.getReturnType();
            if (method.isVarArgs()) {
                Object[] varArgs = (Object[]) params[params.length - 1];
                Object[] oldParams = params;
                params = new Object[oldParams.length + varArgs.length - 1];
                int pIndex = 0;
                for (int i = 0; i < oldParams.length - 1; i++) {
                    params[pIndex] = oldParams[i];
                    pIndex++;
                }
                for (int i = 0; i < varArgs.length; i++) {
                    params[pIndex] = varArgs[i];
                    pIndex++;
                }
            }
            return run(method, returnClass, requires, owner, params);
        }
        return null;
    }

    protected abstract Object run(Method method, Class<?> returnClass, Class<?>[] requires, Object owner, Object... params);

}
