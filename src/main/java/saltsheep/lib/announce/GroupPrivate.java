package saltsheep.lib.announce;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//*一组相互干涉的类里面，不对外公开
@Retention(RUNTIME)
@Target(TYPE)
public @interface GroupPrivate {

}
