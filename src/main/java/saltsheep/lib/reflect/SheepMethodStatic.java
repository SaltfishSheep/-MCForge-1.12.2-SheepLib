package saltsheep.lib.reflect;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.List;

public class SheepMethodStatic extends SheepMethod {

    private final Class<?> target;

    public SheepMethodStatic(List<Method> ms, Class<?> target) {
        super(ms);
        this.target = target;
    }

    public SheepMethodStatic(Method[] ms, Class<?> target) {
        super(ms);
        this.target = target;
    }

    @Override
    public Object run(Method method, Class<?> returnClass, Class<?>[] requires, Object owner, Object... params) {
        MethodType type = requires.length > 0 ? MethodType.methodType(returnClass, requires) : MethodType.methodType(returnClass);
        try {
            if (requires.length > 0)
                return lookup.findStatic(target, method.getName(), type).invokeWithArguments(params);
            else
                return lookup.findStatic(target, method.getName(), type).invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
