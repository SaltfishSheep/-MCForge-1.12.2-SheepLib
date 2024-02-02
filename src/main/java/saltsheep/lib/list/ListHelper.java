package saltsheep.lib.list;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class ListHelper {

    public static <T> List<T> getListInner(Object[] outObjs, GetInnerObject method, Class<T> returnType) {
        List<Object> outList = Lists.newArrayList(outObjs);
        return getListInner(outList, method, returnType);
    }

    public static <T> List<T> getListInner(Iterable<?> outObjs, GetInnerObject method, Class<T> returnType) {
        List<Object> outList = Lists.newLinkedList(outObjs);
        return getListInner(outList, method, returnType);
    }

    public static <T> List<T> getListInner(Iterator<?> outObjs, GetInnerObject method, Class<T> returnType) {
        List<Object> outList = Lists.newArrayList(outObjs);
        return getListInner(outList, method, returnType);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getListInner(List<Object> outObjs, GetInnerObject method, Class<T> returnType) {
        List<T> innerList = Lists.newLinkedList();
        for (Object eachOut : outObjs)
            innerList.add((T) method.get(eachOut));
        return innerList;
    }

    public static interface GetInnerObject {
        public Object get(Object obj);
    }

}
