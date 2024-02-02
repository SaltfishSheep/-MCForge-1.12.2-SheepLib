package saltsheep.lib.reflect;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.List;

public class SheepMethodSpecial extends SheepMethod {

    private final Class<?> target;

    public SheepMethodSpecial(List<Method> ms, Class<?> target) {
        super(ms);
        this.target = target;
    }

    public SheepMethodSpecial(Method[] ms, Class<?> target) {
        super(ms);
        this.target = target;
    }

    @Override
    public Object run(Method method, Class<?> returnClass, Class<?>[] requires, Object owner, Object... params) {
        MethodType type = requires.length > 0 ? MethodType.methodType(returnClass, requires) : MethodType.methodType(returnClass);
        try {
            if (requires.length > 0)
                return lookup.findSpecial(target, method.getName(), type, target).bindTo(owner).invokeWithArguments(params);
            else
                return lookup.findSpecial(target, method.getName(), type, target).bindTo(owner).invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

}
