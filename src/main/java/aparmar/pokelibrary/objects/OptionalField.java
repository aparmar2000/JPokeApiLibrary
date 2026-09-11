package aparmar.pokelibrary.objects;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that this field is optional - 
 * i.e. if it is not a {@code Collection} or array type it might be {@code null}, and if it is a {@code Collection} or array type it may be empty. 
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
public @interface OptionalField {

}
