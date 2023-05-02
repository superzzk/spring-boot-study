package com.zzk.spring.core.customannotation;

import java.lang.annotation.*;

/**
 * @author kun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface DataAccess {
    Class<?> entity();
}
