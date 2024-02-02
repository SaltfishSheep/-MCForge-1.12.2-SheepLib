package saltsheep.lib.announce;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//*一组相互干涉的类里，对外开放的部分
@Retention(RUNTIME)
@Target(TYPE)
public @interface GroupPublic {

}
