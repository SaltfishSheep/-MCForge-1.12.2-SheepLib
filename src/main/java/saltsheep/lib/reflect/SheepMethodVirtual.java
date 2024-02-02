package saltsheep.lib.reflect;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.List;

public class SheepMethodVirtual extends SheepMethod {

    public SheepMethodVirtual(List<Method> ms) {
        super(ms);
    }

    public SheepMethodVirtual(Method[] ms) {
        super(ms);
    }

    @Override
    protected Object run(Method method, Class<?> returnClass, Class<?>[] requires, Object owner, Object... params) {
		/*
		method.setAccessible(true);
		try {
			return method.invoke(owner, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
		*/
        MethodType type = requires.length > 0 ? MethodType.methodType(returnClass, requires) : MethodType.methodType(returnClass);
        try {
            if (requires.length > 0)
                return lookup.findVirtual(owner.getClass(), method.getName(), type).bindTo(owner).invokeWithArguments(params);
            else
                return lookup.findVirtual(owner.getClass(), method.getName(), type).bindTo(owner).invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/*public SheepMethodSpecial toSpecial(Class<?> target) {
		return new SheepMethodSpecial(this.methods,target);
	}
	
	public SheepMethodStatic toStatic(Class<?> target) {
		List<Method> staticMethods = Lists.newLinkedList();
		for(Method method:this.methods)
			if(Modifier.isStatic(method.getModifiers())&&method.getDeclaringClass()==target)
				staticMethods.add(method);
		return new SheepMethodStatic(staticMethods,target);
	}*/

}
